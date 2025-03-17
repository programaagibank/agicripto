package com.cripto.dao;

import com.cripto.model.Carteira;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarteiraDAO {
    private final Connection conexao;

    public CarteiraDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void criarCarteira(Carteira carteira) {
        String sql = "INSERT INTO agicripto.Carteira (id_cliente, saldo_conta_corrente) values(?,?)";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, carteira.getId_cliente());
            ps.setDouble(2, carteira.gerarSaldoAleatoriamente());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a carteira", e);
        }
    }

    public void Pix(double valor, int idContaOrigem, int idContaDestino) {
        String verificarSaldoSQL = "SELECT saldo_conta_corrente FROM agicripto.Carteira WHERE id_cliente = ?";
        String debitarSQL = "UPDATE agicripto.Carteira SET saldo_conta_corrente = saldo_conta_corrente - ? WHERE id_cliente = ?";
        String creditarSQL = "UPDATE agicripto.Carteira SET saldo_conta_corrente = saldo_conta_corrente + ? WHERE id_cliente = ?";

        PreparedStatement verificarSaldoPS = null;
        PreparedStatement debitarPS = null;
        PreparedStatement creditarPS = null;

        try {
            conexao.setAutoCommit(false);

            verificarSaldoPS = conexao.prepareStatement(verificarSaldoSQL);
            verificarSaldoPS.setInt(1, idContaOrigem);
            var resultado = verificarSaldoPS.executeQuery();

            if (!resultado.next()) {
                throw new RuntimeException("Conta de origem não encontrada!");
            }

            double saldoAtual = resultado.getDouble("saldo_conta_corrente");
            if (saldoAtual < valor) {
                throw new RuntimeException("Saldo insuficiente para realizar a transferência!");
            }

            debitarPS = conexao.prepareStatement(debitarSQL);
            debitarPS.setDouble(1, valor);
            debitarPS.setInt(2, idContaOrigem);
            debitarPS.executeUpdate();


            creditarPS = conexao.prepareStatement(creditarSQL);
            creditarPS.setDouble(1, valor);
            creditarPS.setInt(2, idContaDestino);
            creditarPS.executeUpdate();

            conexao.commit();
            System.out.println("Transferência realizada com sucesso!");

        } catch (SQLException e) {
            try {
                conexao.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException("Erro ao realizar a transferência Pix", e);
        } finally {
            try {
                if (verificarSaldoPS != null) verificarSaldoPS.close();
                if (debitarPS != null) debitarPS.close();
                if (creditarPS != null) creditarPS.close();
                conexao.setAutoCommit(true);
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

}

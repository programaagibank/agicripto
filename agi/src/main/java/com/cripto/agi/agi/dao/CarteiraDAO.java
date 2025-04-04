package com.cripto.agi.agi.dao;

import com.cripto.agi.agi.model.Carteira;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public Carteira pegarCarteiraPeloClienteId(Integer id) {
        String sql = "SELECT id_carteira, id_cliente, saldo_conta_corrente FROM agicripto.Carteira WHERE id_cliente = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Carteira carteira = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                carteira = new Carteira(
                        rs.getInt("id_carteira"),
                        rs.getInt("id_cliente"),
                        rs.getDouble("saldo_conta_corrente")
                );
            } else {
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar recursos:" , e);
        }
        return carteira;
    }

    public boolean atualizarSaldo(Double valor, Integer idCarteira) {
        String sql = "UPDATE agicripto.Carteira SET saldo_conta_corrente = ? WHERE id_carteira = ?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setDouble(1, valor);
            ps.setInt(2, idCarteira);
            ps.execute();
            ps.close();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar saldo!", e);
        }
    }
}

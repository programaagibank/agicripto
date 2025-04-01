package com.cripto.agi.agi.dao;

import com.cripto.agi.agi.model.CarteiraCripto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarteiraCriptoDAO {
    private final Connection conexao;

    public CarteiraCriptoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean criarCarteiraCripto(CarteiraCripto carteiraCripto) {
        String sql = "INSERT INTO agicripto.Carteira_Cripto (id_cliente, saldo_brl," +
                "saldo_btc, saldo_eth, saldo_sol, saldo_agicoin) values (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, carteiraCripto.getIdCliente());
            ps.setDouble(2, carteiraCripto.getSaldoBRL());
            ps.setDouble(3, carteiraCripto.getSaldoBTC());
            ps.setDouble(4, carteiraCripto.getSaldoETH());
            ps.setDouble(5, carteiraCripto.getSaldoSOl());
            ps.setDouble(6, carteiraCripto.getSaldoAGICOIN());

            ps.execute();
            ps.close();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar carteira", e);
        }
    }

    public boolean comprarCriptomoedas(Integer opcao, Double valor, Integer idCarteira) {
        String sql;
        if (opcao == 1) {
            sql = "UPDATE agicripto.Carteira_Cripto SET saldo_btc = ? WHERE id_cliente = ?";
        } else if (opcao == 2) {
            sql = "UPDATE agicripto.Carteira_Cripto SET saldo_eth = ? WHERE id_cliente = ?";
        } else if (opcao == 3) {
            sql = "UPDATE agicripto.Carteira_Cripto SET saldo_sol = ? WHERE id_cliente = ?";
        } else {
            return false;
        }

        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setDouble(1, valor);
            ps.setInt(2, idCarteira);

            ps.execute();
            ps.close();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao comprar alguma criptmoeda!", e);
        }
    }

    public CarteiraCripto acharPeloIdCliente(Integer id) {
        String sql = "SELECT id_cliente, saldo_brl, saldo_btc, saldo_eth, saldo_sol, saldo_agicoin FROM agicripto.Carteira_Cripto WHERE id_cliente = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        CarteiraCripto carteiraCripto = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                carteiraCripto = new CarteiraCripto(
                        rs.getInt("id_cliente"),
                        rs.getDouble("saldo_brl"),
                        rs.getDouble("saldo_btc"),
                        rs.getDouble("saldo_eth"),
                        rs.getDouble("saldo_sol"),
                        rs.getDouble("saldo_agicoin")
                );
            } else {
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao procurar carteira pelo id do cliente");
        }
        return carteiraCripto;
    }

    public void atualizarSaldoBrl(Double saldoBRL, Integer id) {
        String sql = "UPDATE agicripto.Carteira_Cripto SET saldo_brl = ? WHERE id_cliente = ?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setDouble(1, saldoBRL);
            ps.setInt(2, id);

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar saldo", e);
        }
    }

    public void atualizarSaldoCripto(int idCliente, int opcaoCripto, double novoSaldo) {
        String sql = "";

        switch (opcaoCripto) {
            case 1 -> sql = "UPDATE agicripto.Carteira_Cripto SET saldo_btc = ? WHERE id_cliente = ?";
            case 2 -> sql = "UPDATE agicripto.Carteira_Cripto SET saldo_eth = ? WHERE id_cliente = ?";
            case 3 -> sql = "UPDATE agicripto.Carteira_Cripto SET saldo_sol = ? WHERE id_cliente = ?";
            case 4 -> sql = "UPDATE agicripto.Carteira_Cripto SET saldo_agicoin = ? WHERE id_cliente = ?";
            default -> throw new RuntimeException("Criptomoeda inválida!");
        }

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setDouble(1, novoSaldo);
            ps.setInt(2, idCliente);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar saldo da cripto: " + e.getMessage(), e);
        }
    }

    public void cashback(double valor, int id) {
        String sql = "UPDATE agicripto.Carteira_Cripto SET saldo_agicoin = saldo_agicoin + ? WHERE id_cliente = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setDouble(1, valor);
            ps.setInt(2, id);

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new RuntimeException("Nenhuma linha foi atualizada. O ID pode estar incorreto.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar saldo: " + e.getMessage(), e);
        }
    }


    public boolean excluirCarteiraCripto(Integer idCliente) {
        String sql = "DELETE FROM agicripto.Carteira_Cripto WHERE id_cliente = ?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idCliente);

            ps.execute();
            ps.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}



package com.cripto.dao;

import com.cripto.model.CarteiraCripto;

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

    public boolean comprarCriptomoedas(Integer opcao, Double valor, Integer idCliente) {
        String coluna;
        if (opcao == 1) {
            coluna = "saldo_btc";
        } else if (opcao == 2) {
            coluna = "saldo_eth";
        } else if (opcao == 3) {
            coluna = "saldo_sol";
        } else {
            return false;
        }

        String sql = "UPDATE agicripto.Carteira_Cripto SET " + coluna + " = " + coluna + " + ? WHERE id_cliente = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setDouble(1, valor);
            ps.setInt(2, idCliente);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao comprar criptomoeda!", e);
        }
    }


    public CarteiraCripto acharPeloIdCliente(Integer id) {
        String sql = "SELECT id_cliente, saldo_brl, saldo_btc, saldo_eth, saldo_sol, saldo_agicoin FROM agicripto.Carteira_Cripto WHERE id_cliente = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CarteiraCripto(
                            rs.getInt("id_cliente"),
                            rs.getDouble("saldo_brl"),
                            rs.getDouble("saldo_btc"),
                            rs.getDouble("saldo_eth"),
                            rs.getDouble("saldo_sol"),
                            rs.getDouble("saldo_agicoin")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao procurar carteira pelo id do cliente", e);
        }
        return null;
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
}

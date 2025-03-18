package com.cripto.dao;

import com.cripto.model.CarteiraCripto;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}

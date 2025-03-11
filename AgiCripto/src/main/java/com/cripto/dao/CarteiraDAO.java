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
}

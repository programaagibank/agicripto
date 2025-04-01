package com.cripto.agi.agi.dao;

import com.cripto.agi.agi.model.Assinatura;

import java.sql.*;

public class AssinaturaDAO {
    private final Connection conexao;

    public AssinaturaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean novaAssinatura(Assinatura assinatura) {
        String sql = "INSERT INTO agicripto.Assinatura (id_cliente, valor, beneficios" +
                ", data_inicio, data_fim, status, id_cripto) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, assinatura.getIdCliente());
            ps.setDouble(2, assinatura.getValor());
            ps.setString(3, assinatura.getBeneficios());
            ps.setDate(4, Date.valueOf(assinatura.getDataInicio()));
            ps.setDate(5, Date.valueOf(assinatura.getDataFim()));
            ps.setString(6, assinatura.getStatus());
            ps.setInt(7, assinatura.getIdCripto());

            ps.execute();
            ps.close();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao realizar assinatura", e);
        }
    }

    public Assinatura acharAssinaturaPeloIdCliente(Integer id) {
        String sql = "SELECT * FROM agicripto.Assinatura WHERE id_cliente = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Assinatura assinatura = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                assinatura = new Assinatura(
                        rs.getInt("id_assinatura"),
                        rs.getInt("id_cliente"),
                        rs.getDouble("valor"),
                        rs.getString("beneficios"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_fim").toLocalDate(),
                        rs.getString("status"),
                        rs.getInt("id_cripto")
                );
            } else {
                rs.close();
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar assinatura ", e);
        }
        return assinatura;
    }

    public boolean desativarAssinatura(Integer idCliente) {
        String sql = "UPDATE agicripto.Assinatura SET status = ? WHERE id_cliente = ?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, "inativo");
            ps.setInt(2, idCliente);

            ps.execute();
            ps.close();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desativar assinatura ", e);
        }
    }
}

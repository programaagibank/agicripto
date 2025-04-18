package com.cripto.dao;

import com.cripto.model.Transacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {
    private final Connection conexao;

    public TransacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean comprar(Transacao transacao) {
        String sql = "INSERT INTO Transacao (id_carteira, id_cliente, id_cripto, status, id_tipo_transacao, valor, data) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1,transacao.getIdCarteira());
            ps.setInt(2, transacao.getIdCliente());
            ps.setInt(3, transacao.getIdCripto());
            ps.setString(4, transacao.getStatus());
            ps.setInt(5, transacao.getIdTipoTransacao());
            ps.setDouble(6, transacao.getValor());
            ps.setDate(7, Date.valueOf(transacao.getData().toLocalDate()));

            ps.execute();
            ps.close();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao efetuar transacao", e);
        }
    }

    public List<Transacao> listarTransacoesPorCliente(int idCliente) {
        String sql = "SELECT * FROM agicripto.Transacao WHERE id_cliente = ?";
        List<Transacao> transacoes = new ArrayList<>();
        PreparedStatement ps;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transacao transacao = new Transacao(
                        rs.getInt("id_transacao"),
                        rs.getInt("id_carteira"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_cripto"),
                        rs.getString("status"),
                        rs.getInt("id_tipo_transacao"),
                        rs.getDouble("valor"),
                        rs.getTimestamp("data").toLocalDateTime()
                );
                transacoes.add(transacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transacoes;
    }
}

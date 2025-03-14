package com.cripto.dao;

import com.cripto.model.Carteira;
import com.cripto.model.Cliente;
import com.cripto.model.TipoTransacao;
import com.cripto.model.Transacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransacaoDAO {
    private final Connection conexao;

    public TransacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean comprar(Transacao transacao) {
        String sql = "INSERT INTO Transacao (id_carteira, id_cliente, status, id_tipo_transacao, valor, data) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1,transacao.getIdCarteira());
            ps.setInt(2, transacao.getIdCliente());
            ps.setString(3, transacao.getStatus());
            ps.setInt(4, transacao.getIdTipoTransacao());
            ps.setDouble(5, transacao.getValor());
            ps.setDate(6, Date.valueOf(transacao.getData().toLocalDate()));

            ps.execute();
            ps.close();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao efetuar transacao", e);
        }
    }
}

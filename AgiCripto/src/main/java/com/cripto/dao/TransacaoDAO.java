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
        String sql = "INSERT INTO Transacao (id_carteira, id_cliente, id_cripto, status, id_tipo_transacao, valor, data) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

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
}

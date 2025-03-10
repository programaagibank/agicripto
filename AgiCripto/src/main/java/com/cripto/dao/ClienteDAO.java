package com.cripto.dao;

import com.cripto.model.Cliente;
import com.cripto.model.database.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {
    private final Connection conexao;

    public ClienteDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void cadastrarCliente(Cliente cliente) {
        String sql = "INSERT INTO agicripto.Cliente (nome, email, cpf, senha, status) values(?,?,?,?,?)";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getCpf());
            ps.setString(4, cliente.getSenha());
            ps.setString(5, cliente.getStatus());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException("Nao conseguiu se conectar para realizar o cadastro!");
        }
    }
}

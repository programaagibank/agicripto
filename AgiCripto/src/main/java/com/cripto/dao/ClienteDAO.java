package com.cripto.dao;

import com.cripto.model.Cliente;
import com.cripto.model.database.Conexao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class ClienteDAO {
    private Conexao conexao;

    public ClienteDAO() {
        this.conexao = new Conexao();
    }

    public void cadastrarCliente(Cliente cliente) {
        String sql = "INSERT INTO agicripto.Cliente (nome, email, cpf, senha, status) values(?,?,?,?,?)";
        PreparedStatement ps = null;

        try {
            ps = Objects.requireNonNull(conexao.getConexao(1)).prepareStatement(sql);

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

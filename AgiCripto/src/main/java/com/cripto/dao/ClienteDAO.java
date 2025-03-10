package com.cripto.dao;

import com.cripto.model.Cliente;

import java.sql.*;

public class ClienteDAO {
    private final Connection conexao;

    public ClienteDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void cadastrarCliente(Cliente cliente) {
        String sql = "INSERT INTO agicripto.Cliente (nome, email, cpf, senha, status) values(?,?,?,?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getCpf());
            ps.setString(4, cliente.getSenha());
            ps.setString(5, cliente.getStatus());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId_cliente(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Nao conseguiu se conectar para realizar o cadastro!");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao tentar fechar o rs e ps");
            }
        }
    }
}

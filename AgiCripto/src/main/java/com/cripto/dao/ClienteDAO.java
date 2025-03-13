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

    public boolean excluirCliente(int id){
        String sql = "DELETE FROM agicripto.Cliente WHERE ID_CLIENTE = ?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();
            ps.close();
            return linhasAfetadas > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String email, String senha) {
        String sql = "SELECT * FROM Cliente WHERE email = ? AND senha = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, senha);

            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar logar: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                throw new RuntimeException("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    public Cliente acharPeloId(int id){
        String sql = "SELECT * FROM agicripto.Cliente WHERE id_cliente = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cliente cliente = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();

            if (rs.next()){
                cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        rs.getString("senha"),
                        rs.getString("status"),
                        rs.getInt("id_assinatura")
                );
            } else{
                rs.close();
                ps.close();
            }
        } catch(Exception e){
            throw new RuntimeException("usuário não encontrado");
        }

        return cliente;
    }

}

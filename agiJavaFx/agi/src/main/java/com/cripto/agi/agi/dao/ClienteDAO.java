package com.cripto.agi.agi.dao;

import com.cripto.agi.agi.controller.ClienteController;
import com.cripto.agi.agi.model.Cliente;

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

    public Cliente encontrarEmail(String login) {
        String sql = "SELECT id_cliente, nome, email, cpf, senha, status, id_assinatura FROM Cliente WHERE email = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cliente cliente = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, login);
            rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        rs.getString("senha"),
                        rs.getString("status"),
                        rs.getInt("id_assinatura")
                );
            } else {
                rs.close();
                ps.close();
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao fechar recursos: " + e.getMessage());
        }
        return cliente;
    }

    public boolean alterarSenha(String novaSenha, String login){
        String sql = "UPDATE agicripto.Cliente SET senha = ? WHERE email = ?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, novaSenha);
            ps.setString(2, login);

            ps.executeUpdate();

            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fechar recursos: " + e.getMessage());
        }finally {
            try {
                if (ps != null) ps.close();

            } catch (Exception e) {
                throw new RuntimeException("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    public Cliente acharPeloId(int id) {
        String sql = "SELECT * FROM Cliente WHERE id_cliente = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cliente cliente = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        rs.getString("senha"),
                        rs.getString("status"),
                        rs.getInt("id_assinatura")
                );
            } else {
                rs.close();
                ps.close();
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao fechar recursos: " + e.getMessage());
        }
        return cliente;
    }

    public boolean ativarConta(int idCliente) {
        String sql = "UPDATE agicripto.Cliente SET status = 'ATIVO' WHERE id_cliente = ?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idCliente);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean desativarCarteira(Integer idCliente) {
        String sql = "UPDATE agicripto.Cliente SET status = ? WHERE id_cliente = ?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, "desativado");
            ps.setInt(2, idCliente);

            ps.execute();
            ps.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desativar conta", e);
        }
    }

}

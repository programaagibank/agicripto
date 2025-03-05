package com.cripto.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/agicripto";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public Conexao() {

    }

    public Connection getConexao() throws SQLException {
        Connection conexao = null;

        try {
            Class.forName(DRIVER);

            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            if (conexao != null) {
                System.out.println("CONEXAO AUTORIZADA");
            } else {
                System.out.println("CONEXAO FALHOU!");
            }
            return conexao;

        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado.");
            throw new SQLException("Driver não encontrado.", e);

        } catch (SQLException e) {

            System.err.println("Erro: Não foi possível conectar ao banco de dados.");
            throw new SQLException("Erro ao conectar ao banco de dados.", e);
        }
    }

    public void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexao com o banco de dados " + e.getMessage());
            }
        }
    }
}

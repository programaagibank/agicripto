package com.cripto.agi.agi.model.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Objects;

public class Conexao {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String[] INFOS = {
            "DB_URL_ONLINE",
            "DB_USER_ONLINE",
            "DB_PASSWORD_ONLINE",
            "DB_URL_LOCAL",
            "DB_USER_LOCAL",
            "DB_PASSWORD_LOCAL"
    };

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private Connection conexao = null;

    public Connection getConexao(int c) throws SQLException {
        try {
            Class.forName(DRIVER);
            try {
                if (c == 0) {
                    conexao = DriverManager.getConnection(
                            Objects.requireNonNull(dotenv.get(INFOS[0])),
                            Objects.requireNonNull(dotenv.get(INFOS[1])),
                            Objects.requireNonNull(dotenv.get(INFOS[2]))
                    );
                } else if (c == 1) {
                    conexao = DriverManager.getConnection(
                            Objects.requireNonNull(dotenv.get(INFOS[3])),
                            Objects.requireNonNull(dotenv.get(INFOS[4])),
                            Objects.requireNonNull(dotenv.get(INFOS[5]))
                    );
                }
            } catch (InputMismatchException e) {
                throw new InputMismatchException("Apenas 0 ou 1");
            }

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

    public Connection getConnection() {
        return conexao;
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

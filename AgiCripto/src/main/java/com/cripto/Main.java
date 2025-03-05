package com.cripto;

import com.cripto.database.Conexao;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Conexao conexao = new Conexao();
        conexao.getConexao();
    }
}
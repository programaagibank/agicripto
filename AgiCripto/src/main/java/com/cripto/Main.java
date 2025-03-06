package com.cripto;

import com.cripto.database.Conexao;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite 0 para conexao online e 1 para local: ");
        int opcao = scanner.nextInt();

        Conexao conexao = new Conexao();
        conexao.getConexao(opcao);
    }
}
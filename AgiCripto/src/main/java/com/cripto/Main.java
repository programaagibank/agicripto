package com.cripto;

import com.cripto.controller.ClienteController;
import com.cripto.dao.CarteiraDAO;
import com.cripto.dao.ClienteDAO;
import com.cripto.model.Cliente;
import com.cripto.model.database.Conexao;
import com.cripto.view.TelaView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite 0 para conexao online e 1 para local: ");
        int opcao = scanner.nextInt();

        Conexao conexao = new Conexao();
        Connection connection = conexao.getConexao(opcao);

        CarteiraDAO carteiraDAO = new CarteiraDAO(connection);
        ClienteDAO clienteDAO = new ClienteDAO(connection);

        TelaView tela = new TelaView();
        tela.mostrarTelaInicial();

        ClienteController controller = new ClienteController(clienteDAO, carteiraDAO);
        controller.fazerLogin();
        controller.cadastro();
        controller.excluirCliente();


    }
}
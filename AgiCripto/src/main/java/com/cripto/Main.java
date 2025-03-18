package com.cripto;

import com.cripto.controller.ClienteController;
import com.cripto.dao.CarteiraDAO;
import com.cripto.dao.ClienteDAO;
import com.cripto.dao.TransacaoDAO;
import com.cripto.model.Cliente;
import com.cripto.model.Transacao;
import com.cripto.model.database.Conexao;
import com.cripto.view.CarteiraView;
import com.cripto.view.ClienteView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("""
                0 - para conexao online
                1 - para local
                Digite: """);
        int opcao = scanner.nextInt();
        Conexao conexao = new Conexao();
        Connection connection = conexao.getConexao(opcao);
        CarteiraDAO carteiraDAO = new CarteiraDAO(connection);
        ClienteDAO clienteDAO = new ClienteDAO(connection);
        TransacaoDAO transacaoDAO = new TransacaoDAO(connection);
        ClienteController controller = new ClienteController(clienteDAO, carteiraDAO, transacaoDAO);
        ClienteView view = new ClienteView(controller, carteiraDAO);
        CarteiraView cart = new CarteiraView(controller,carteiraDAO);

        // Comeca chamar o aplicativo...
      //  cart.exibirTutorial();  todo método de exibir o tutorial

        view.escolhaMenu();
//        System.out.println(view.comprar());
    }
}
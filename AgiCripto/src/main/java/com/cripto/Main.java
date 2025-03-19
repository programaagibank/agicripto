package com.cripto;

import com.cripto.controller.CarteiraCriptoController;
import com.cripto.controller.ClienteController;
import com.cripto.dao.CarteiraCriptoDAO;
import com.cripto.dao.CarteiraDAO;
import com.cripto.dao.ClienteDAO;
import com.cripto.dao.TransacaoDAO;
import com.cripto.model.database.Conexao;
import com.cripto.view.CarteiraCriptoView;
import com.cripto.view.ClienteView;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("""
                0 - para conexao online
                1 - para local
                Digite:\s""");
        int opcao = scanner.nextInt();
        Conexao conexao = new Conexao();
        Connection connection = conexao.getConexao(opcao);
        ClienteView view = getClienteView(connection);

        // Comeca chamar o aplicativo...

        view.escolhaMenu();
    }

    @NotNull
    private static ClienteView getClienteView(Connection connection) {
        CarteiraDAO carteiraDAO = new CarteiraDAO(connection);
        CarteiraCriptoDAO carteiraCriptoDAO = new CarteiraCriptoDAO(connection);
        ClienteDAO clienteDAO = new ClienteDAO(connection);
        TransacaoDAO transacaoDAO = new TransacaoDAO(connection);
        ClienteController controller = new ClienteController(clienteDAO, carteiraDAO, transacaoDAO);
        CarteiraCriptoDAO criptoDAO = new CarteiraCriptoDAO(connection);
        CarteiraCriptoController carteiraCriptoController = new CarteiraCriptoController(
                controller,
                criptoDAO,
                carteiraDAO,
                clienteDAO,
                transacaoDAO
        );
        CarteiraCriptoView carteiraCriptoView = new CarteiraCriptoView(carteiraCriptoController, controller, carteiraDAO);
        return new ClienteView(controller, carteiraDAO, carteiraCriptoController, carteiraCriptoView, carteiraCriptoDAO);
    }
}
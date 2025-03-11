package com.cripto.controller;

import com.cripto.dao.ClienteDAO;
import com.cripto.model.Cliente;

import java.util.Locale;
import java.util.Scanner;

public class ClienteController {
    private Scanner scanner;
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
        this.clienteDAO = new ClienteDAO();
    }

    public void cadastro() {
        String nome = scanner.nextLine();
        String email = scanner.nextLine();
        String senha = scanner.nextLine();
        String cpf = scanner.nextLine();

        Cliente cliente = new Cliente(nome, email, cpf, senha);
        cliente.setStatus("ativo");

        clienteDAO.cadastrarCliente(cliente);
    }

    public void excluirCliente() {
        System.out.print("\u001b[2J\u001b[H");

        System.out.printf("Digite o ID do usuário a ser excluído: ");
        int id = scanner.nextInt();

        boolean sucesso = clienteDAO.excluirCliente(id);

        if (sucesso) {
            System.out.println("Usuário excluído com sucesso.");
        } else {
            System.out.println("Erro ao excluir usuário. Verifique se o ID está correto.");
        }
    }
}

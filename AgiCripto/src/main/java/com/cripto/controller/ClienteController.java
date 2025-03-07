package com.cripto.controller;

import com.cripto.dao.ClienteDAO;
import com.cripto.model.Cliente;

import java.util.Locale;
import java.util.Scanner;

public class ClienteController {
    private final Scanner scanner;
    private final ClienteDAO clienteDAO;

    public ClienteController(ClienteDAO clienteDAO) {
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
        this.clienteDAO = clienteDAO;
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
}

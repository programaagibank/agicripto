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
}

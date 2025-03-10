package com.cripto.controller;

import com.cripto.dao.CarteiraDAO;
import com.cripto.dao.ClienteDAO;
import com.cripto.model.Carteira;
import com.cripto.model.Cliente;

import java.util.Locale;
import java.util.Scanner;

public class ClienteController {
    private final Scanner scanner;
    private final ClienteDAO clienteDAO;
    private final CarteiraDAO carteiraDAO;

    public ClienteController(ClienteDAO clienteDAO, CarteiraDAO carteiraDAO) {
        this.carteiraDAO = carteiraDAO;
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
        this.clienteDAO = clienteDAO;
    }

    public void cadastro() {
        String nome = scanner.nextLine();
        String email = scanner.nextLine();
        String senha = scanner.nextLine();
        String cpf = scanner.nextLine();

        Cliente cliente = new Cliente(nome, email, cpf);
        cliente.setStatus("ativo");
        String senhaCriptografada = cliente.criptografarSenha(senha);
        cliente.setSenha(senhaCriptografada);
        clienteDAO.cadastrarCliente(cliente);

        Carteira carteira = new Carteira();
        carteira.setId_cliente(cliente.getId_cliente());
        carteira.gerarSaldoAleatoriamente();
        carteiraDAO.criarCarteira(carteira);
    }
}

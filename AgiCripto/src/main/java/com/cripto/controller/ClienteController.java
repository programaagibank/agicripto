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

    public void cadastro() throws Exception {
        String nome = scanner.nextLine();
        String email = scanner.nextLine();
        String senha = scanner.nextLine();
        String cpf = scanner.nextLine();

        if (cpf.length() > 11) {
            throw new Exception("CPF nao pode ser maior que 11");
        }

        Cliente cliente = new Cliente(nome, email, cpf);
        cliente.setStatus("ativo");
        cliente.setCpf(cliente.formatarCpf(cpf));
        String senhaCriptografada = cliente.criptografarSenha(senha);
        cliente.setSenha(senhaCriptografada);
        clienteDAO.cadastrarCliente(cliente);

        Carteira carteira = new Carteira();
        carteira.setId_cliente(cliente.getId_cliente());
        carteira.gerarSaldoAleatoriamente();
        carteiraDAO.criarCarteira(carteira);
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

    public void fazerLogin() {
        Cliente cliente = new Cliente();
        System.out.println("E-mail: ");
        String email = scanner.nextLine();

        System.out.println("Senha: ");
        String senha = scanner.nextLine();

        if (clienteDAO.login(email, cliente.criptografarSenha(senha))) {
            System.out.println("Login bem sucedido!");
        } else {
            System.out.println("E-mail ou senha incorretos");
        }

    }
}

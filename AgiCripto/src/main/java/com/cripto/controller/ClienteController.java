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
    private final Cliente cliente = new Cliente();

    public ClienteController(
            ClienteDAO clienteDAO,
            CarteiraDAO carteiraDAO
    ) {
        this.carteiraDAO = carteiraDAO;
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
        this.clienteDAO = clienteDAO;
    }

    public boolean cadastro(String nome, String email, String senha, String cpf) {
        Cliente cliente = new Cliente(nome, email, cpf);
        cliente.setSenha(cliente.criptografarSenha(senha));
        cliente.setStatus("ativo");
        clienteDAO.cadastrarCliente(cliente);

        Carteira carteira = new Carteira();
        carteira.setId_cliente(cliente.getId_cliente());
        carteira.gerarSaldoAleatoriamente();
        carteiraDAO.criarCarteira(carteira);

        return true;
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

    public boolean fazerLogin(String email, String senha) {
        return clienteDAO.login(email, this.cliente.criptografarSenha(senha));
    }

//    public boolean alterarSenha(String email, String novaSenha, String confirmarSenha){
//        if (!clienteDAO.encontrarEmail(email)) return false;
//        if (!novaSenha.equals(confirmarSenha)) return false;
//
//        return clienteDAO.alterarSenha(this.cliente.criptografarSenha(novaSenha), email);
//    }
//
//    public boolean encontrarPeloEmail(String email) {
//        Cliente atual = clienteDAO.encontrarEmail(email);
//    }
}

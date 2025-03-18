package com.cripto.controller;

import com.cripto.dao.CarteiraDAO;
import com.cripto.dao.ClienteDAO;
import com.cripto.model.Carteira;
import com.cripto.model.Cliente;
import com.cripto.view.ClienteView;

import java.util.Locale;
import java.util.Scanner;

public class ClienteController {
    private final ClienteDAO clienteDAO;
    private final CarteiraDAO carteiraDAO;
    private final ClienteView clienteView;

    public ClienteController(ClienteDAO clienteDAO, CarteiraDAO carteiraDAO, ClienteView clienteView) {
        this.carteiraDAO = carteiraDAO;
        this.clienteDAO = clienteDAO;
        this.clienteView = clienteView;
    }

    public void cadastro() throws Exception {
        String nome = clienteView.solicitaNome();
        String email = clienteView.solicitaEmail();
        String senha = clienteView.solicitaSenha();
        String cpf = clienteView.solicitaCpf();

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
        int id = Integer.parseInt(clienteView.solicitaId());

        boolean sucesso = clienteDAO.excluirCliente(id);

        if (sucesso) {
            System.out.println("Usuário excluído com sucesso.");
        } else {
            System.out.println("Erro ao excluir usuário. Verifique se o ID está correto.");
        }
    }
}

package com.cripto.controller;

import com.cripto.dao.CarteiraDAO;
import com.cripto.dao.ClienteDAO;
import com.cripto.dao.TransacaoDAO;
import com.cripto.model.Carteira;
import com.cripto.model.Cliente;
import com.cripto.model.Transacao;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;

public class ClienteController {
    private final Scanner scanner;
    private final ClienteDAO clienteDAO;
    private final CarteiraDAO carteiraDAO;
    private final Cliente cliente = new Cliente();
    private Cliente clienteLogado;
    private final TransacaoDAO transacaoDAO;

    public ClienteController(
            ClienteDAO clienteDAO,
            CarteiraDAO carteiraDAO,
            TransacaoDAO transacaoDAO
    ) {
        this.carteiraDAO = carteiraDAO;
        this.transacaoDAO = transacaoDAO;
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
        this.clienteDAO = clienteDAO;
    }

    public boolean cadastro(String nome, String email, String senha, String cpf) {
        Cliente cliente = new Cliente(nome, email, cpf);
        cliente.setSenha(cliente.criptografarSenha(senha));
        cliente.setStatus("ativo");
        cliente.setCpf(cliente.formatarCpf(cliente.getCpf()));
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
        Cliente clienteEncontrado = clienteDAO.encontrarEmail(email);
        if (clienteEncontrado != null && clienteEncontrado.getSenha().equals(this.cliente.criptografarSenha(senha))) {
            this.clienteLogado = clienteEncontrado;
            return true;
        }
        return false;
    }

    public boolean alterarSenha(String email, String novaSenha, String confirmarSenha){
        if (clienteDAO.encontrarEmail(email) == null) return false;
        if (!novaSenha.equals(confirmarSenha)) return false;

        return clienteDAO.alterarSenha(this.cliente.criptografarSenha(novaSenha), email);
    }

    public Cliente encontrarPeloEmail(String email) {
        return clienteDAO.encontrarEmail(email);
    }

    public boolean comprar(Double valor) {
        if (this.clienteLogado == null) {
            System.out.println("Nenhum cliente está logado.");
            return false;
        }

        Carteira carteiraLogada = carteiraDAO.pegarCarteiraPeloClienteId(this.clienteLogado.getId_cliente());
        if (carteiraLogada == null) return false;

        carteiraDAO.atualizarSaldo(
                (carteiraLogada.getSaldoContaCorrente() - valor),
                carteiraLogada.getId_carteira()
        );

        LocalDateTime data = LocalDateTime.now();
        Transacao transacao = new Transacao(
                carteiraLogada.getId_carteira(),
                this.clienteLogado.getId_cliente(),
                1,
                valor,
                data
        );
        transacao.setStatus("PAGO");
        return transacaoDAO.comprar(transacao);
    }

}

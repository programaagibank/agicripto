package com.cripto.agi.agi.controller;

import com.cripto.agi.agi.dao.CarteiraDAO;
import com.cripto.agi.agi.dao.ClienteDAO;
import com.cripto.agi.agi.dao.TransacaoDAO;
import com.cripto.agi.agi.model.Carteira;
import com.cripto.agi.agi.model.Cliente;
import com.cripto.agi.agi.model.Transacao;

import java.time.LocalDateTime;
import java.util.List;
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
        cliente.setStatus("DESATIVADO");
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

    public boolean alterarSenha(String email, String cpf, String novaSenha, String confirmarSenha){
        Cliente clienteAlterar = clienteDAO.encontrarEmail(email);

        if (clienteAlterar == null){
            System.out.println("Erro, conta não localizada");
            return false;
        }
        if (!clienteAlterar.getCpf().equals(cpf)) {
            System.out.println("Cpf errado");
            return false;
        }

        if (!novaSenha.equals(confirmarSenha)) {
            return false;
        }

        return clienteDAO.alterarSenha(this.cliente.criptografarSenha(novaSenha), email);
    }

    public Cliente encontrarPeloEmail(String email) {
        return clienteDAO.encontrarEmail(email);
    }

    public Cliente pegarClienteLogado() {
        return this.clienteLogado;
    }

    public boolean comprar(Double valor) {
        if (this.clienteLogado == null) {
            System.out.println("Nenhum cliente está logado.");
            return false;
        }

        Carteira carteiraLogada = carteiraDAO.pegarCarteiraPeloClienteId(this.clienteLogado.getId_cliente());
        if (carteiraLogada == null) return false;

        if (carteiraLogada.getSaldoContaCorrente() < valor) return false;

        LocalDateTime data = LocalDateTime.now();
        Transacao transacao = new Transacao(
                carteiraLogada.getId_carteira(),
                this.clienteLogado.getId_cliente(),
                4,
                "PAGO",
                1,
                valor,
                data
        );

        carteiraDAO.atualizarSaldo(
                (carteiraLogada.getSaldoContaCorrente() - valor),
                carteiraLogada.getId_carteira()
        );

        return transacaoDAO.comprar(transacao);
    }

    public List<Transacao> listarTransacoes() {
        return transacaoDAO.listarTransacoesPorCliente(pegarClienteLogado().getId_cliente());
    }

    public CarteiraDAO getCarteiraDAO() {
        return this.carteiraDAO;
    }
}

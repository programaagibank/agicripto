package com.cripto.controller;

import com.cripto.dao.CarteiraCriptoDAO;
import com.cripto.dao.CarteiraDAO;
import com.cripto.dao.ClienteDAO;
import com.cripto.dao.TransacaoDAO;
import com.cripto.model.Carteira;
import com.cripto.model.CarteiraCripto;
import com.cripto.model.Cliente;
import com.cripto.model.Transacao;

import java.time.LocalDateTime;

public class CarteiraCriptoController {
    private final ClienteController clienteController;
    private final CarteiraCriptoDAO carteiraCriptoDAO;
    private final CarteiraDAO carteiraDAO;
    private final ClienteDAO clienteDAO;
    private final TransacaoDAO transacaoDAO;

    public CarteiraCriptoController(ClienteController clienteController, CarteiraCriptoDAO carteiraCriptoDAO, CarteiraDAO carteiraDAO, ClienteDAO clienteDAO, TransacaoDAO transacaoDAO) {
        this.clienteController = clienteController;
        this.carteiraCriptoDAO = carteiraCriptoDAO;
        this.carteiraDAO = carteiraDAO;
        this.clienteDAO = clienteDAO;
        this.transacaoDAO = transacaoDAO;
    }

    public boolean ativarCarteiraCripto() {
        Cliente cliente = clienteController.pegarClienteLogado();
        if (cliente == null) return false;

        CarteiraCripto carteiraCripto = new CarteiraCripto(
                cliente.getId_cliente(),
                0.0,
                0.00000000,
                0.00000000,
                0.00000000,
                0.00000000
        );

        if (carteiraCriptoDAO.criarCarteiraCripto(carteiraCripto)) {
            clienteDAO.ativarConta(cliente.getId_cliente());
            return true;
        }
        return false;
    }

    public boolean comprarCripto(Integer opcao, Double valor) {
        Cliente cliente = clienteController.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoDAO.acharPeloIdCliente(cliente.getId_cliente());

        if (carteiraCripto == null) return false;
        if (carteira == null) return false;

        double novoValor;
        int idCripto;
        if (opcao == 1) {
            novoValor = carteiraCripto.getSaldoBTC() + valor;
            idCripto = 1;
        } else if (opcao == 2) {
            novoValor = carteiraCripto.getSaldoETH() + valor;
            idCripto = 2;
        } else if (opcao == 3) {
            novoValor = carteiraCripto.getSaldoSOl() + valor;
            idCripto = 3;
        } else {
            return false;
        }

        if (carteira.getSaldoContaCorrente() < valor || carteira.getSaldoContaCorrente() == 0) return false;

        LocalDateTime data = LocalDateTime.now();
        Transacao transacao = new Transacao(
                carteira.getId_carteira(),
                cliente.getId_cliente(),
                idCripto,
                "PAGO",
                1,
                valor,
                data
        );

        carteiraDAO.atualizarSaldo((carteira.getSaldoContaCorrente() - valor), carteira.getId_carteira());
        transacaoDAO.comprar(transacao);

        double saldoBRl = carteiraCripto.getSaldoSOl() + carteiraCripto.getSaldoETH() + carteiraCripto.getSaldoBTC() + valor;
        carteiraCriptoDAO.atualizarSaldoBrl(saldoBRl, cliente.getId_cliente());

        return carteiraCriptoDAO.comprarCriptomoedas(opcao, novoValor, cliente.getId_cliente());
    }

    public boolean venderCriptoMoeda(int opcao, double valor){
        Cliente cliente = clienteController.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoDAO.acharPeloIdCliente(cliente.getId_cliente());

        if (carteiraCripto == null) return false;
        if (carteira == null) return false;

        int idCripto;
        double novoValor;

        if (opcao == 1){
            if (carteiraCripto.getSaldoBTC() < valor) return false;
            idCripto = 1;
            novoValor = carteiraCripto.getSaldoBTC() - valor;
            carteiraCriptoDAO.subtrairCripto(valor,opcao,carteiraCripto.getIdCliente());
            carteiraDAO.atualizarSaldo(carteira.getSaldoContaCorrente() + valor,carteira.getId_carteira());
        } else if (opcao == 2){
            if (carteiraCripto.getSaldoETH() < valor) return false;
            idCripto = 2;
            novoValor = carteiraCripto.getSaldoETH() - valor;
            carteiraCriptoDAO.subtrairCripto(valor,opcao,carteiraCripto.getIdCliente());
            carteiraDAO.atualizarSaldo(carteira.getSaldoContaCorrente() + valor,carteira.getId_carteira());
        } else if (opcao == 3){
            if (carteiraCripto.getSaldoSOl() < valor) return false;
            idCripto = 3;
            novoValor = carteiraCripto.getSaldoSOl() - valor;
            carteiraCriptoDAO.subtrairCripto(valor,opcao,carteiraCripto.getIdCliente()); // subtrair valor
            carteiraDAO.atualizarSaldo(carteira.getSaldoContaCorrente() + valor,carteira.getId_carteira()); // adc saldo na conta
        } else {
            return false;
        }

        LocalDateTime data = LocalDateTime.now();
        Transacao transacao = new Transacao(
                carteira.getId_carteira(),
                cliente.getId_cliente(),
                idCripto,
                "PAGO",
                1,
                valor,
                data
        );

        carteiraDAO.atualizarSaldo((carteira.getSaldoContaCorrente() + valor), carteira.getId_carteira());
        transacaoDAO.comprar(transacao);

        double saldoBRl = carteiraCripto.getSaldoSOl() + carteiraCripto.getSaldoETH() + carteiraCripto.getSaldoBTC() + valor;
        carteiraCriptoDAO.atualizarSaldoBrl(saldoBRl, cliente.getId_cliente());

        return carteiraCriptoDAO.venderCriptomoedas(opcao, novoValor, cliente.getId_cliente());
    }

    public CarteiraCripto pegarCarteiraCripto(Integer id) {
        return carteiraCriptoDAO.acharPeloIdCliente(id);
    }

    public boolean realizarCashback(double valor, int id) {
        try {
            carteiraCriptoDAO.cashback(valor, id);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao processar cashback: " + e.getMessage());
            return false;
        }
    }

    public boolean desativarCarteiraCripto() {
        Cliente cliente = clienteController.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoDAO.acharPeloIdCliente(cliente.getId_cliente());

        if (clienteDAO.desativarCarteira(cliente.getId_cliente())) {
            carteiraDAO.atualizarSaldo((carteiraCripto.getSaldoBRL() + carteira.getSaldoContaCorrente()), carteira.getId_carteira());
            carteiraCriptoDAO.atualizarSaldoBrl(0.0, cliente.getId_cliente());
            carteiraCriptoDAO.excluirCarteiraCripto(cliente.getId_cliente());

            return true;
        }
        return false;
    }
}

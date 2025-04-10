package com.cripto.agi.agi.controller;

import com.cripto.agi.agi.dao.*;
import com.cripto.agi.agi.model.Carteira;
import com.cripto.agi.agi.model.CarteiraCripto;
import com.cripto.agi.agi.model.Cliente;
import com.cripto.agi.agi.model.Transacao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

public class CarteiraCriptoController {
    private final ClienteController clienteController;
    private final CarteiraCriptoDAO carteiraCriptoDAO;
    private final CarteiraDAO carteiraDAO;
    private final ClienteDAO clienteDAO;
    private final TransacaoDAO transacaoDAO;
    private final AssinaturaDAO assinaturaDAO;
    private final AssinaturaController assinaturaController;


    public CarteiraCriptoController(ClienteController clienteController,AssinaturaController assinaturaController, CarteiraCriptoDAO carteiraCriptoDAO, CarteiraDAO carteiraDAO, ClienteDAO clienteDAO, TransacaoDAO transacaoDAO, AssinaturaDAO assinaturaDAO) {
        this.clienteController = clienteController;
        this.assinaturaController = assinaturaController;
        this.carteiraCriptoDAO = carteiraCriptoDAO;
        this.carteiraDAO = carteiraDAO;
        this.clienteDAO = clienteDAO;
        this.transacaoDAO = transacaoDAO;
        this.assinaturaDAO = assinaturaDAO;
    }

    public boolean ativarCarteiraCripto() {
        Cliente cliente = clienteController.pegarClienteLogado();
        if (cliente == null) return false;

        CarteiraCripto carteiraCripto = new CarteiraCripto(
                cliente.getId_cliente(),
                0.00,
                0.00,
                0.00,
                0.00,
                0.00
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
            carteiraCriptoDAO.excluirCarteiraCripto(cliente.getId_cliente());

            return true;
        }
        return false;
    }

    public boolean trocarCripto(int criptoOrigem, int criptoDestino, double valor) {
        Cliente cliente = clienteController.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoDAO.acharPeloIdCliente(cliente.getId_cliente());

        if (carteiraCripto == null) return false;

        double saldoAtual = 0;
        if (criptoOrigem == 1) saldoAtual = carteiraCripto.getSaldoBTC();
        else if (criptoOrigem == 2) saldoAtual = carteiraCripto.getSaldoETH();
        else if (criptoOrigem == 3) saldoAtual = carteiraCripto.getSaldoSOl();
        
        double saldoDestino = 0;
        if (criptoDestino == 1) saldoDestino = carteiraCripto.getSaldoBTC();
        else if (criptoDestino == 2) saldoDestino = carteiraCripto.getSaldoETH();
        else if (criptoDestino == 3) saldoDestino = carteiraCripto.getSaldoSOl();

        if (saldoAtual < valor) return false;

        carteiraCriptoDAO.atualizarSaldoCripto(cliente.getId_cliente(), criptoOrigem, saldoAtual - valor);
        carteiraCriptoDAO.atualizarSaldoCripto(cliente.getId_cliente(), criptoDestino, (valor + saldoDestino));

        LocalDateTime data = LocalDateTime.now();
        Transacao transacao = new Transacao(
                carteira.getId_carteira(),
                cliente.getId_cliente(),
                 criptoDestino,
                "PAGO",
                2,
                valor,
                data
        );
        transacaoDAO.comprar(transacao);

        return true;
    }

//    public boolean venderCriptoMoeda(int opcao, double valor){
//        Cliente cliente = clienteController.pegarClienteLogado();
//        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
//        CarteiraCripto carteiraCripto = carteiraCriptoDAO.acharPeloIdCliente(cliente.getId_cliente());
//
//        if (carteiraCripto == null) return false;
//        if (carteira == null) return false;
//
//        int idCripto;
//
//        if (opcao == 1){
//            if (carteiraCripto.getSaldoBTC() < valor) return false;
//            idCripto = 1;
//            carteiraCriptoDAO.subtrairCripto(valor,opcao,carteiraCripto.getIdCliente());
//            carteiraDAO.atualizarSaldo(carteira.getSaldoContaCorrente() + valor,carteira.getId_carteira());
//        } else if (opcao == 2){
//            if (carteiraCripto.getSaldoETH() < valor) return false;
//            idCripto = 2;
//            carteiraCriptoDAO.subtrairCripto(valor,opcao,carteiraCripto.getIdCliente());
//            carteiraDAO.atualizarSaldo(carteira.getSaldoContaCorrente() + valor,carteira.getId_carteira());
//        } else if (opcao == 3){
//            if (carteiraCripto.getSaldoSOl() < valor) return false;
//            idCripto = 3;
//            carteiraCriptoDAO.subtrairCripto(valor,opcao,carteiraCripto.getIdCliente()); // subtrair valor
//            carteiraDAO.atualizarSaldo(carteira.getSaldoContaCorrente() + valor,carteira.getId_carteira()); // adc saldo na conta
//        } else {
//            return false;
//        }
//
//        LocalDateTime data = LocalDateTime.now();
//        Transacao transacao = new Transacao(
//                carteira.getId_carteira(),
//                cliente.getId_cliente(),
//                idCripto,
//                "PAGO",
//                3,
//                valor,
//                data
//        );
//        transacaoDAO.comprar(transacao);
//
//        double saldoBRl = carteiraCripto.getSaldoSOl() + carteiraCripto.getSaldoETH() + carteiraCripto.getSaldoBTC();
//        carteiraCriptoDAO.atualizarSaldoBrl(saldoBRl, cliente.getId_cliente());
//
//        return carteiraCriptoDAO.venderCriptomoedas(opcao, valor, cliente.getId_cliente());
//    }

    public boolean venderCriptoMoeda(int opcao, double valor) {
        Cliente cliente = clienteController.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoDAO.acharPeloIdCliente(cliente.getId_cliente());

        double valorDebitado = 0;
        if (opcao == 1 && (valor <= carteiraCripto.getSaldoBTC())) {
            valorDebitado = carteiraCripto.getSaldoBTC() - valor;
        } else if (opcao == 2 && (valor <= carteiraCripto.getSaldoETH())) {
            valorDebitado = carteiraCripto.getSaldoETH() - valor;
        } else if (opcao == 3 && (valor <= carteiraCripto.getSaldoSOl())) {
            valorDebitado = carteiraCripto.getSaldoSOl() - valor;
        }

        try {
            carteiraDAO.atualizarSaldo((carteira.getSaldoContaCorrente() + valor), carteira.getId_carteira());
            carteiraCriptoDAO.atualizarSaldoCripto(cliente.getId_cliente(), opcao, valorDebitado);
            carteiraCriptoDAO.atualizarSaldoBrl(
                    (carteiraCripto.getSaldoSOl()
                            + carteiraCripto.getSaldoBTC()
                            + carteiraCripto.getSaldoETH() - valor),
                    cliente.getId_cliente()
            );

            LocalDateTime data = LocalDateTime.now();
            Transacao transacao = new Transacao(
                    carteira.getId_carteira(),
                    cliente.getId_cliente(),
                    opcao,
                    "PAGO",
                    3,
                    valor,
                    data
            );
            transacaoDAO.comprar(transacao);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("ERRO AO VENDER! ", e);
        }
    }

    public boolean transferirCripto(String emailRecebidor, double valor, int idCripto) {
        Cliente clienteRemetente = clienteController.pegarClienteLogado();
        Cliente clienteRecebidor = clienteDAO.encontrarEmail(emailRecebidor);

        if (Objects.equals(clienteRemetente.getId_cliente(), clienteRecebidor.getId_cliente())) {
            System.out.println("Erro: Você não pode transferir para si mesmo!");
            return false;
        }

        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(clienteRemetente.getId_cliente());
        System.out.println("ID da carteira do remetente: " + carteira.getId_carteira());

        CarteiraCripto carteiraRemetente = carteiraCriptoDAO.acharPeloIdCliente(clienteRemetente.getId_cliente());
        CarteiraCripto carteiraRecebidor = carteiraCriptoDAO.acharPeloIdCliente(clienteRecebidor.getId_cliente());

        double saldoRecebidor = 0, saldoRemetente = 0;

        switch (idCripto) {
            case 1 -> {
                saldoRecebidor = carteiraRecebidor.getSaldoBTC();
                saldoRemetente = carteiraRemetente.getSaldoBTC();
            }
            case 2 -> {
                saldoRecebidor = carteiraRecebidor.getSaldoETH();
                saldoRemetente = carteiraRemetente.getSaldoETH();
            }
            case 3 -> {
                saldoRecebidor = carteiraRecebidor.getSaldoSOl();
                saldoRemetente = carteiraRemetente.getSaldoSOl();
            }
            default -> {
                System.out.println("Erro: ID de criptomoeda inválido!");
                return false;
            }
        }

        if (saldoRemetente < valor) {
            System.out.println("Erro: Saldo insuficiente para realizar a transferência!");
            return false;
        }

        carteiraCriptoDAO.atualizarSaldoCripto(carteiraRecebidor.getIdCliente(), idCripto, saldoRecebidor + valor);
        carteiraCriptoDAO.atualizarSaldoCripto(carteiraRemetente.getIdCliente(), idCripto, saldoRemetente - valor);

        carteiraCriptoDAO.atualizarSaldoBrl((carteiraRemetente.getSaldoBRL() - valor), carteiraRemetente.getIdCliente());
        carteiraCriptoDAO.atualizarSaldoBrl((carteiraRecebidor.getSaldoBRL() + valor), carteiraRecebidor.getIdCliente());

        LocalDateTime data = LocalDateTime.now();
        Transacao transacao = new Transacao(
                carteira.getId_carteira(),
                clienteRemetente.getId_cliente(),
                idCripto,
                "PAGO",
                4,
                valor,
                data
        );

        transacaoDAO.comprar(transacao);

        System.out.println("Transferência realizada com sucesso!");
        return true;
    }

}

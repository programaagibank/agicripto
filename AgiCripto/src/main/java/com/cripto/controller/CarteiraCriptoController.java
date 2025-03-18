package com.cripto.controller;

import com.cripto.dao.CarteiraCriptoDAO;
import com.cripto.dao.CarteiraDAO;
import com.cripto.dao.ClienteDAO;
import com.cripto.model.Carteira;
import com.cripto.model.CarteiraCripto;
import com.cripto.model.Cliente;

public class CarteiraCriptoController {
    private final ClienteController clienteController;
    private final CarteiraCriptoDAO carteiraCriptoDAO;
    private final CarteiraDAO carteiraDAO;
    private final ClienteDAO clienteDAO;

    public CarteiraCriptoController(ClienteController clienteController, CarteiraCriptoDAO carteiraCriptoDAO, CarteiraDAO carteiraDAO, ClienteDAO clienteDAO) {
        this.clienteController = clienteController;
        this.carteiraCriptoDAO = carteiraCriptoDAO;
        this.carteiraDAO = carteiraDAO;
        this.clienteDAO = clienteDAO;
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
        if (opcao == 1) {
            novoValor = carteiraCripto.getSaldoBRL() + valor;
        } else if (opcao == 2) {
            novoValor = carteiraCripto.getSaldoETH() + valor;
        } else if (opcao == 3) {
            novoValor = carteiraCripto.getSaldoSOl() + valor;
        } else {
            return false;
        }

        if (carteira.getSaldoContaCorrente() < valor || carteira.getSaldoContaCorrente() == 0) return false;

        carteiraDAO.atualizarSaldo((carteira.getSaldoContaCorrente() - valor), carteira.getId_carteira());

        return carteiraCriptoDAO.comprarCriptomoedas(opcao, novoValor, cliente.getId_cliente());
    }
}

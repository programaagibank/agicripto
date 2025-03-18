package com.cripto.controller;

import com.cripto.dao.CarteiraCriptoDAO;
import com.cripto.dao.CarteiraDAO;
import com.cripto.model.CarteiraCripto;
import com.cripto.model.Cliente;

public class CarteiraCriptoController {
    private final ClienteController clienteController;
    private final CarteiraCriptoDAO carteiraCriptoDAO;
    private final CarteiraDAO carteiraDAO;

    public CarteiraCriptoController(ClienteController clienteController, CarteiraCriptoDAO carteiraCriptoDAO, CarteiraDAO carteiraDAO) {
        this.clienteController = clienteController;
        this.carteiraCriptoDAO = carteiraCriptoDAO;
        this.carteiraDAO = carteiraDAO;
    }

    public String ativarCarteiraCripto() {
        Cliente cliente = clienteController.pegarClienteLogado();
        if (cliente == null) return null;

        CarteiraCripto carteiraCripto = new CarteiraCripto(
                cliente.getId_cliente(),
                0.0,
                0.00000000,
                0.00000000,
                0.00000000,
                0.00000000
        );

        if (carteiraCriptoDAO.criarCarteiraCripto(carteiraCripto)) {
            return "Carteira cripto criada com sucesso.";
        }
        return "Carteira cripto nao criada!";
    }
}

package com.cripto.controller;

import com.cripto.dao.AssinaturaDAO;
import com.cripto.model.Assinatura;
import com.cripto.model.Cliente;

import java.time.LocalDate;

public class AssinaturaController {
    private final ClienteController clienteController;
    private final AssinaturaDAO assinaturaDAO;

    public AssinaturaController(
            ClienteController clienteController,
            AssinaturaDAO assinaturaDAO
    ) {
        this.clienteController = clienteController;
        this.assinaturaDAO = assinaturaDAO;
    }

    public void assinar(double valor, int opcao) {
        Cliente cliente = clienteController.pegarClienteLogado();

        if (cliente.getStatus().equals("desativado")) return;

        LocalDate dataInicio = LocalDate.now();
        LocalDate dataFim = dataInicio.plusDays(30);

        Assinatura assinatura = new Assinatura(
                cliente.getId_cliente(),
                valor,
                "1.5X AGICOIN",
                dataInicio,
                dataFim,
                "ativo",
                opcao
        );

        assinaturaDAO.novaAssinatura(assinatura);
    }

    public void desativar(int id) {
        assinaturaDAO.desativarAssinatura(id);
    }

    public Assinatura pegarPeloId(int id) {
        return assinaturaDAO.acharAssinaturaPeloIdCliente(id);
    }
}

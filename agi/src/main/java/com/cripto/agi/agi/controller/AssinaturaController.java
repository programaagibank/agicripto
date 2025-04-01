package com.cripto.agi.agi.controller;

import com.cripto.agi.agi.dao.AssinaturaDAO;
import com.cripto.agi.agi.model.Assinatura;
import com.cripto.agi.agi.model.Cliente;

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

    public boolean assinar(double valor, int opcao) {
        Cliente cliente = clienteController.pegarClienteLogado();

        if (cliente.getStatus().equals("desativado")) return false;

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

        return assinaturaDAO.novaAssinatura(assinatura);
    }

    public boolean desativar(int id) {
        return assinaturaDAO.desativarAssinatura(id);
    }

    public Assinatura pegarPeloId(int id) {
        return assinaturaDAO.acharAssinaturaPeloIdCliente(id);
    }
}

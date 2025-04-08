package com.cripto.agi.agi.controller;

import com.cripto.agi.agi.dao.AssinaturaDAO;
import com.cripto.agi.agi.model.Assinatura;
import com.cripto.agi.agi.model.Cliente;

import java.time.LocalDate;
import java.util.List;

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

        List<Assinatura> assinaturasExistentes = assinaturaDAO.acharAssinaturasPorIdCliente(cliente.getId_cliente());

        for (Assinatura a : assinaturasExistentes) {
            if (a.getIdCripto() == opcao && a.getStatus().equalsIgnoreCase("ativo")) {
                return false;
            }
        }

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


    public boolean desativar(int idCliente, int idAssinatura) {
        return assinaturaDAO.desativarAssinatura(idCliente, idAssinatura);
    }

    public List<Assinatura> pegarPeloId(int id) {
        return assinaturaDAO.acharAssinaturasPorIdCliente(id);
    }
}

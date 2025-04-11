package com.cripto.agi.agi.controller;

import com.cripto.agi.agi.dao.AssinaturaDAO;
import com.cripto.agi.agi.dao.CarteiraCriptoDAO;
import com.cripto.agi.agi.dao.CarteiraDAO;
import com.cripto.agi.agi.javafx.controllers.CarteiraCorrenteController;
import com.cripto.agi.agi.model.Assinatura;
import com.cripto.agi.agi.model.Carteira;
import com.cripto.agi.agi.model.CarteiraCripto;
import com.cripto.agi.agi.model.Cliente;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.List;

public class AssinaturaController {
    private final ClienteController clienteController;
    private final AssinaturaDAO assinaturaDAO;
    private final CarteiraDAO carteiraDAO;
    private final CarteiraCriptoDAO carteiraCriptoDAO;


    public AssinaturaController(
            ClienteController clienteController,
            AssinaturaDAO assinaturaDAO,
            CarteiraDAO carteiraDAO,
            CarteiraCriptoDAO carteiraCriptoDAO


    ) {
        this.clienteController = clienteController;
        this.assinaturaDAO = assinaturaDAO;
        this.carteiraDAO = carteiraDAO;
        this.carteiraCriptoDAO = carteiraCriptoDAO;
    }

    public boolean assinar(double valor, int opcao) {
        Cliente cliente = clienteController.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());

        if (cliente.getStatus().equals("desativado")) return false;

        List<Assinatura> assinaturasExistentes = assinaturaDAO.acharAssinaturasPorIdCliente(cliente.getId_cliente());

        for (Assinatura a : assinaturasExistentes) {
            if (a.getIdCripto() == opcao && a.getStatus().equalsIgnoreCase("ativo")) {
                return false;
            }
        }

        if (valor > carteira.getSaldoContaCorrente()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Saldo insuficiente!");
            alert.setHeaderText(null);
            alert.setContentText("Erro, você não pode realizar uma assinatura sem o saldo disponível!");
            alert.showAndWait();
            return false;
        }

        LocalDate dataInicio = LocalDate.now();
        LocalDate dataFim = dataInicio.plusDays(30);

        CarteiraCripto carteiraCripto = carteiraCriptoDAO.acharPeloIdCliente(cliente.getId_cliente());

        carteiraDAO.atualizarSaldo((carteira.getSaldoContaCorrente() - valor),carteira.getId_carteira());


        if (opcao == 1)carteiraCriptoDAO.comprarCriptomoedas(opcao,(carteiraCripto.getSaldoBTC()+valor),cliente.getId_cliente());
        if (opcao == 2)carteiraCriptoDAO.comprarCriptomoedas(opcao,(carteiraCripto.getSaldoETH()+valor),cliente.getId_cliente());
        if (opcao == 3)carteiraCriptoDAO.comprarCriptomoedas(opcao,(carteiraCripto.getSaldoSOl()+valor),cliente.getId_cliente());

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

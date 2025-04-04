package com.cripto.agi.agi.javafx.controllers;

import com.cripto.agi.agi.api.Criptomoedas;
import com.cripto.agi.agi.controller.CarteiraCriptoController;
import com.cripto.agi.agi.controller.ClienteController;
import com.cripto.agi.agi.dao.CarteiraDAO;
import com.cripto.agi.agi.model.Carteira;
import com.cripto.agi.agi.model.CarteiraCripto;
import com.cripto.agi.agi.model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CompraController {
    @FXML
    public Label saldoLabel;
    @FXML
    public Label nomeLabel;
    @FXML
    public Label btcValor;
    @FXML
    public Label ethValor;
    @FXML
    public Label solValor;
    @FXML
    public Label saldoGeral;
    @FXML
    public TextField quantidadeCripto;
    @FXML
    public ChoiceBox<String> escolhaCripto;

    private ClienteController controller;
    private CarteiraDAO carteiraDAO;
    private CarteiraCriptoController carteiraCriptoController;

    public void setClienteController(ClienteController controller, CarteiraDAO carteiraDAO, CarteiraCriptoController carteiraCriptoController) {
        this.controller = controller;
        this.carteiraDAO = carteiraDAO;
        this.carteiraCriptoController = carteiraCriptoController;
        this.carregarInfos();
    }

    public void carregarInfos() {
        Cliente cliente = controller.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoController.pegarCarteiraCripto(cliente.getId_cliente());
        Criptomoedas criptomoedas = new Criptomoedas();
        criptomoedas.consultarPrecoBitcoin();
        criptomoedas.consultarPrecoEthereum();
        criptomoedas.consultarPrecoSolana();

        nomeLabel.setText(cliente.getNome());
        saldoGeral.setText(String.format("%.2f", carteiraCripto.getSaldoBRL()));
        saldoLabel.setText(String.valueOf(carteira.getSaldoContaCorrente()));
        btcValor.setText(String.format("%.2f", criptomoedas.getPrecoBtc()));
        ethValor.setText(String.format("%.2f", criptomoedas.getPrecoEth()));
        solValor.setText(String.format("%.2f", criptomoedas.getPrecoSol()));
    }

    public void comprarCripto(ActionEvent actionEvent) throws IOException {
        int opcao = escolhaCripto.getSelectionModel().getSelectedIndex() + 1;
        Double valor = Double.parseDouble(quantidadeCripto.getText());

        if (carteiraCriptoController.comprarCripto(opcao, valor)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Compra realizado");
            alert.setHeaderText(null);
            alert.setContentText("Compra realizada com sucesso!");
            alert.showAndWait();
            voltarParaCarteiraCripto(actionEvent);
        } else {
            System.out.println("ERROR");
        }
    }

    public void voltarParaCarteiraCripto(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/carteiraCripto.fxml"));
        Parent root = loader.load();

        CriptoController criptoController = loader.getController();
        criptoController.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void carteiraCorrente(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/carteiraCorrente.fxml"));
        Parent root = loader.load();

        CarteiraCorrenteController carteiraCorrenteController = loader.getController();
        carteiraCorrenteController.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void sair(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/login.fxml"));
        Parent root = loader.load();

        LoginController loginController = loader.getController();
        loginController.setClienteController(controller, carteiraDAO, carteiraCriptoController);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    @FXML
    public void initialize() {
        escolhaCripto.getItems().addAll("Bitcoin", "Ethereum", "Solana");
    }
}

package com.cripto.agi.agi.javafx.controllers;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class QuantidadeController {
    @FXML
    private Label saldoLabel;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label btcValor;
    @FXML
    private Label ethValor;
    @FXML
    private Label solValor;
    @FXML
    private Label saldoGeral;
    @FXML
    private Label agicoinValor;

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

        nomeLabel.setText(cliente.getNome());
        saldoLabel.setText(String.valueOf(carteira.getSaldoContaCorrente()));

        btcValor.setText(String.format("%.8f", carteiraCripto.conversao(1, carteiraCripto.getSaldoBTC())));
        ethValor.setText(String.format("%.8f", carteiraCripto.conversao(2, carteiraCripto.getSaldoETH())));
        solValor.setText(String.format("%.8f",carteiraCripto.conversao(3, carteiraCripto.getSaldoSOl())));
        agicoinValor.setText(String.format("%.2f",carteiraCripto.getSaldoAGICOIN()));
        saldoGeral.setText(String.format("%.2f",carteiraCripto.getSaldoBRL()));
    }

    public void carteiraCorrente(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/carteiraCorrente.fxml"));
        Parent root = loader.load();

        CarteiraCorrenteController carteiraCorrenteController = loader.getController();
        carteiraCorrenteController.setClienteController(this.controller, this.carteiraDAO, this.carteiraCriptoController);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void verEmReal(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/carteiraCripto.fxml"));
        Parent root = loader.load();

        CriptoController criptoController = loader.getController();
        criptoController.setClienteController(this.controller, this.carteiraDAO, this.carteiraCriptoController);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void comprarCripto(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/comprar.fxml"));
        Parent root = loader.load();

        CompraController compraController = loader.getController();
        compraController.setClienteController(this.controller, this.carteiraDAO, this.carteiraCriptoController);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void venderCripto(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/vender.fxml"));
        Parent root = loader.load();

        VenderController verderController = loader.getController();
        verderController.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void trocarCripto(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/trocar.fxml"));
        Parent root = loader.load();

        TrocarController trocarController = loader.getController();
        trocarController.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void transferirCripto(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/transferir.fxml"));
        Parent root = loader.load();

        TransferenciaController transferenciaController = loader.getController();
        transferenciaController.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController);

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
}

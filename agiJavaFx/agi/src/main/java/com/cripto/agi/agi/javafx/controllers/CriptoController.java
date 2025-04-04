package com.cripto.agi.agi.javafx.controllers;

import com.cripto.agi.agi.controller.AssinaturaController;
import com.cripto.agi.agi.controller.CarteiraCriptoController;
import com.cripto.agi.agi.controller.ClienteController;
import com.cripto.agi.agi.dao.AssinaturaDAO;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;

public class CriptoController {
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
    private AssinaturaController assinaturaController;
    private CarteiraDAO carteiraDAO;
    private CarteiraCriptoController carteiraCriptoController;
    private AssinaturaDAO assinaturaDAO;


    public void setClienteController(ClienteController controller, CarteiraDAO carteiraDAO, CarteiraCriptoController carteiraCriptoController, AssinaturaController assinaturaController, AssinaturaDAO assinaturaDAO) {
        this.controller = controller;
        this.carteiraDAO = carteiraDAO;
        this.carteiraCriptoController = carteiraCriptoController;
        this.assinaturaController = assinaturaController;
        this.assinaturaDAO = assinaturaDAO;
        this.carregarInfos();
    }



    public void carregarInfos() {
        Cliente cliente = controller.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoController.pegarCarteiraCripto(cliente.getId_cliente());

        nomeLabel.setText(cliente.getNome());
        saldoLabel.setText(String.valueOf(carteira.getSaldoContaCorrente()));

        btcValor.setText(String.valueOf(carteiraCripto.getSaldoBTC()));
        ethValor.setText(String.valueOf(carteiraCripto.getSaldoETH()));
        solValor.setText(String.valueOf(carteiraCripto.getSaldoSOl()));
        agicoinValor.setText(String.valueOf(carteiraCripto.getSaldoAGICOIN()));
        saldoGeral.setText(String.valueOf(carteiraCripto.getSaldoBRL()));
    }

    public void carteiraCorrente(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/carteiraCorrente.fxml"));
        Parent root = loader.load();

        CarteiraCorrenteController carteiraCorrenteController = loader.getController();
        carteiraCorrenteController.setClienteController(this.controller, this.carteiraDAO, this.carteiraCriptoController, this.assinaturaController, this.assinaturaDAO);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void verQuantidadeCripto(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/quantidadeCripto.fxml"));
        Parent root = loader.load();

        QuantidadeController quantidadeController = loader.getController();
        quantidadeController.setClienteController(this.controller, this.carteiraDAO, this.carteiraCriptoController);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void comprarCripto(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/comprar.fxml"));
        Parent root = loader.load();

        CompraController compraController = loader.getController();
        compraController.setClienteController(this.controller, this.carteiraDAO, this.carteiraCriptoController, this.assinaturaController, this.assinaturaDAO);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void venderCripto(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/vender.fxml"));
        Parent root = loader.load();

        VenderController verderController = loader.getController();
        verderController.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController, this.assinaturaController, this.assinaturaDAO);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void trocarCripto(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/trocar.fxml"));
        Parent root = loader.load();

        TrocarController trocarController = loader.getController();
        trocarController.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController, this.assinaturaController, this.assinaturaDAO);

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

    public void desativarCarteira(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Desativar carteira");
        alert.setContentText("Tem certeza de que deseja desativar sua carteira? Esta ação não pode ser desfeita.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            boolean sucesso = carteiraCriptoController.desativarCarteiraCripto();

            if (sucesso) {
                Alert sucessoAlert = new Alert(Alert.AlertType.INFORMATION);
                sucessoAlert.setTitle("Sucesso");
                sucessoAlert.setHeaderText(null);
                sucessoAlert.setContentText("Carteira desativada com sucesso.");
                sucessoAlert.showAndWait();
                try {
                    carteiraCorrente(actionEvent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Alert erroAlert = new Alert(Alert.AlertType.ERROR);
                erroAlert.setTitle("Erro");
                erroAlert.setHeaderText(null);
                erroAlert.setContentText("Erro ao desativar a carteira. Tente novamente.");
                erroAlert.showAndWait();
            }
        }
    }

    public void assinarCripto(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/assinaturaCripto.fxml"));
        Parent root = loader.load();

        AssinaturaControllerFX assinaturaControllerFX = loader.getController();
        assinaturaControllerFX.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController,this.assinaturaController, assinaturaDAO);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    public void sair(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/login.fxml"));
        Parent root = loader.load();

        LoginController loginController = loader.getController();
        loginController.setClienteController(controller, carteiraDAO, carteiraCriptoController, assinaturaController, assinaturaDAO);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void abrirTutorial() {

        String url = "https://docs.google.com/document/d/1iYs-_-BqH6Q8N1AuYM1FrW0Hg1ZVmRV4ETuexy27N90/edit?usp=sharing";
        try {
            Desktop desktop = Desktop.getDesktop();
            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(new URI(url));
            }
        } catch (Exception e) {
            System.err.println("Erro ao abrir a URL: " + e.getMessage());
        }
    }
}

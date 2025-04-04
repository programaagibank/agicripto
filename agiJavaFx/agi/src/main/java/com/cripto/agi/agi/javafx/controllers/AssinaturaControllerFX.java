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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;



public class AssinaturaControllerFX {

    @FXML
    private TextField aporteMensalField;

    @FXML
    private ChoiceBox<String> moedaChoiceBox;


    private ClienteController controller;
    private CarteiraDAO carteiraDAO;
    private CarteiraCriptoController carteiraCriptoController;
    private AssinaturaDAO assinaturaDAO;
    private AssinaturaController assinaturaController;

    public void setClienteController(ClienteController controller, CarteiraDAO carteiraDAO, CarteiraCriptoController carteiraCriptoController, AssinaturaController assinaturaController, AssinaturaDAO assinaturaDAO) {
        this.controller = controller;
        this.carteiraDAO = carteiraDAO;
        this.assinaturaDAO = assinaturaDAO;
        this.assinaturaController = assinaturaController;
        this.carteiraCriptoController = carteiraCriptoController;
        this.carregarInfos();
    }

    public void carregarInfos() {
        Cliente cliente = controller.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoController.pegarCarteiraCripto(cliente.getId_cliente());
    }

    public void assinarCripto(ActionEvent actionEvent) throws IOException {
        int opcao = moedaChoiceBox.getSelectionModel().getSelectedIndex() + 1;
        double valor = Double.parseDouble(aporteMensalField.getText());

        if (assinaturaController.assinar(valor, opcao)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Assinatura Realizada!!");
            alert.setHeaderText(null);
            alert.setContentText("Sua assinatura: R$ " + valor + "mensal na moeda " + opcao);
            alert.showAndWait();
            voltarParaCarteiraCripto(actionEvent);
        }
    }

    public void carteiraCorrente(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/carteiraCorrente.fxml"));
        Parent root = loader.load();

        CarteiraCorrenteController carteiraCorrenteController = loader.getController();
        carteiraCorrenteController.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController, this.assinaturaController, this.assinaturaDAO);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
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

    public void voltarParaCarteiraCripto(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/carteiraCripto.fxml"));
        Parent root = loader.load();

        CriptoController criptoController = loader.getController();
        criptoController.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController, this.assinaturaController, this.assinaturaDAO);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    @FXML
    public void initialize() {
        moedaChoiceBox.getItems().addAll("Bitcoin", "Ethereum", "Solana");
    }

}

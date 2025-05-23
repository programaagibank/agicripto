package com.cripto.agi.agi.javafx.controllers;

import com.cripto.agi.agi.controller.AssinaturaController;
import com.cripto.agi.agi.controller.CarteiraCriptoController;
import com.cripto.agi.agi.controller.ClienteController;
import com.cripto.agi.agi.dao.AssinaturaDAO;
import com.cripto.agi.agi.dao.CarteiraDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    private TextField emailText;
    @FXML
    private TextField senhaText;

    private ClienteController controller;
    private CarteiraDAO carteiraDAO;
    private CarteiraCriptoController carteiraCriptoController;
    private AssinaturaController assinaturaController;
    private AssinaturaDAO assinaturaDAO;

    public void setClienteController(ClienteController controller, CarteiraDAO carteiraDAO, CarteiraCriptoController carteiraCriptoController, AssinaturaController assinaturaController, AssinaturaDAO assinaturaDAO) {
        this.controller = controller;
        this.carteiraDAO = carteiraDAO;
        this.carteiraCriptoController = carteiraCriptoController;
        this.assinaturaController = assinaturaController;
        this.assinaturaDAO = assinaturaDAO;
    }

    public void login(ActionEvent actionEvent) throws IOException {
        String email = emailText.getText();
        String senha = senhaText.getText();

        if (controller.fazerLogin(email, senha)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login realizado");
            alert.setHeaderText(null);
            alert.setContentText("Login concluído com sucesso!\nVocê será redirecionado para a sua carteira.");
            alert.showAndWait();

            carteiraCorrente(actionEvent);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Conta não encontrada");
            alert.setHeaderText(null);
            alert.setContentText("Verifique o login ou a senha digitada!");
            alert.showAndWait();
        }
    }

    public void carteiraCorrente(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/carteiraCorrente.fxml"));
        Parent root = loader.load();

        CarteiraCorrenteController carteiraController = loader.getController();
        carteiraController.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController, this.assinaturaController, this.assinaturaDAO);
        carteiraController.carregarInfos();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void cadastro(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/cadastro.fxml"));
        Parent root = loader.load();

        CadastroController cadastroController = loader.getController();
        cadastroController.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController, this.assinaturaController, this.assinaturaDAO);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void esqueceuSenha(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/esqueceuSenha.fxml"));
        Parent root = loader.load();

        EsqueceuSenhaController esqueceuSenhaController = loader.getController();
        esqueceuSenhaController.setClienteController(this.controller, this.controller.getCarteiraDAO(), carteiraCriptoController, this.assinaturaController, this.assinaturaDAO);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }
}
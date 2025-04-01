package com.cripto.agi.agi.javafx.controllers;

import com.cripto.agi.agi.controller.CarteiraCriptoController;
import com.cripto.agi.agi.controller.ClienteController;
import com.cripto.agi.agi.dao.CarteiraDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EsqueceuSenhaController {
    @FXML
    private TextField emailText;
    @FXML
    private TextField senhaText;
    @FXML
    private TextField confirmarSenhaText;
    @FXML
    private TextField cpfText;

    private ClienteController controller;
    private CarteiraDAO carteiraDAO;
    private CarteiraCriptoController carteiraCriptoController;

    public void setClienteController(ClienteController controller, CarteiraDAO carteiraDAO, CarteiraCriptoController carteiraCriptoController) {
        this.controller = controller;
        this.carteiraDAO = carteiraDAO;
        this.carteiraCriptoController = carteiraCriptoController;
    }

    public void esqueceuSenha(ActionEvent actionEvent) throws IOException {
        String email = emailText.getText();
        String cpf = cpfText.getText();
        String senha = senhaText.getText();
        String confirmarSenha = confirmarSenhaText.getText();

        if (controller.alterarSenha(email, cpf, senha, confirmarSenha)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alteracao realizada");
            alert.setHeaderText(null);
            alert.setContentText("Alteracao concluída com sucesso!\nVocê será redirecionado para o login.");
            alert.showAndWait();
            login(actionEvent);
        } else {
            System.out.println("Senha nao alterada");
        }
    }

    public void cadastro(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/cadastro.fxml"));
        Parent root = loader.load();

        CadastroController cadastroController = loader.getController();
        cadastroController.setClienteController(this.controller, this.carteiraDAO, this.carteiraCriptoController);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void login(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cripto/agi/agi/login.fxml"));
        Parent root = loader.load();

        LoginController loginController = loader.getController();
        loginController.setClienteController(this.controller, this.carteiraDAO, this.carteiraCriptoController);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }
}

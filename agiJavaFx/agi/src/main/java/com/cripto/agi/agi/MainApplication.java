package com.cripto.agi.agi;

import com.cripto.agi.agi.controller.AssinaturaController;
import com.cripto.agi.agi.controller.CarteiraCriptoController;
import com.cripto.agi.agi.controller.ClienteController;
import com.cripto.agi.agi.dao.*;
import com.cripto.agi.agi.javafx.controllers.LoginController;
import com.cripto.agi.agi.model.database.Conexao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Conexao conexao = new Conexao();
        Connection connection = conexao.getConexao(0);
        CarteiraDAO carteiraDAO = new CarteiraDAO(connection);
        CarteiraCriptoDAO carteiraCriptoDAO = new CarteiraCriptoDAO(connection);
        ClienteDAO clienteDAO = new ClienteDAO(connection);
        TransacaoDAO transacaoDAO = new TransacaoDAO(connection);
        AssinaturaDAO assinaturaDAO = new AssinaturaDAO(connection);
        ClienteController controller = new ClienteController(clienteDAO, carteiraDAO, transacaoDAO);
        AssinaturaController assinaturaController = new AssinaturaController(controller, assinaturaDAO);
        CarteiraCriptoController carteiraCriptoController = new CarteiraCriptoController(controller,assinaturaController, carteiraCriptoDAO, carteiraDAO, clienteDAO, transacaoDAO, assinaturaDAO);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setClienteController(controller, carteiraDAO, carteiraCriptoController, assinaturaController, assinaturaDAO);
        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}

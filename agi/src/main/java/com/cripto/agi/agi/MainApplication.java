package com.cripto.agi.agi;

import com.cripto.agi.agi.controller.CarteiraCriptoController;
import com.cripto.agi.agi.controller.ClienteController;
import com.cripto.agi.agi.dao.CarteiraCriptoDAO;
import com.cripto.agi.agi.dao.CarteiraDAO;
import com.cripto.agi.agi.dao.ClienteDAO;
import com.cripto.agi.agi.dao.TransacaoDAO;
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
        ClienteController controller = new ClienteController(clienteDAO, carteiraDAO, transacaoDAO);
        CarteiraCriptoController carteiraCriptoController = new CarteiraCriptoController(controller, carteiraCriptoDAO, carteiraDAO, clienteDAO, transacaoDAO);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setClienteController(controller, carteiraDAO, carteiraCriptoController);
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

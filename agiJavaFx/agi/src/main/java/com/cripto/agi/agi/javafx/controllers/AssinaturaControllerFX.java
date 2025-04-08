package com.cripto.agi.agi.javafx.controllers;

import com.cripto.agi.agi.controller.AssinaturaController;
import com.cripto.agi.agi.controller.CarteiraCriptoController;
import com.cripto.agi.agi.controller.ClienteController;
import com.cripto.agi.agi.dao.AssinaturaDAO;
import com.cripto.agi.agi.dao.CarteiraDAO;
import com.cripto.agi.agi.model.Assinatura;
import com.cripto.agi.agi.model.Carteira;
import com.cripto.agi.agi.model.CarteiraCripto;
import com.cripto.agi.agi.model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;


public class AssinaturaControllerFX {

    @FXML
    private TextField aporteMensalField;

    @FXML
    private ChoiceBox<String> moedaChoiceBox;

    @FXML
    private VBox assinaturasBox;



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

        Cliente cliente = controller.pegarClienteLogado();
        carregarAssinaturasCliente(cliente.getId_cliente());
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
            if (opcao == 1) alert.setContentText("Sua assinatura: R$ " + valor + " mensal na moeda BTC");
            if (opcao == 2) alert.setContentText("Sua assinatura: R$ " + valor + " mensal na moeda ETH");
            if (opcao == 3) alert.setContentText("Sua assinatura: R$ " + valor + " mensal na moeda SOL");

            alert.showAndWait();
            voltarParaCarteiraCripto(actionEvent);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Assinatura não realizada!");
            alert.setContentText("Você já possui uma assinatura nessa moeda!");
            alert.setHeaderText(null);
            alert.showAndWait();
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

    public void carregarAssinaturasCliente(Integer idCliente) {
        assinaturasBox.getChildren().clear();

        List<Assinatura> listaAssinaturas = assinaturaDAO.acharAssinaturasPorIdCliente(idCliente); // ajuste para o seu DAO real

        if (listaAssinaturas.isEmpty()) {
            Label semAssinaturas = new Label("Nenhuma assinatura ativa.");
            semAssinaturas.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");
            assinaturasBox.getChildren().add(semAssinaturas);
        } else {
            for (Assinatura a : listaAssinaturas) {
                HBox assinaturaItem = new HBox(10);
                assinaturaItem.setStyle("-fx-padding: 10; -fx-background-color: #f2f2f2; -fx-background-radius: 10;");
                assinaturaItem.setAlignment(Pos.CENTER_LEFT);

                String nomeMoeda = "";
                String imagemPath = "";
                switch (a.getIdCripto()) {
                    case 1:
                        nomeMoeda = "Bitcoin";
                        imagemPath = "/com/cripto/agi/agi/imagens/bitcoin-Photoroom.png";
                        break;
                    case 2:
                        nomeMoeda = "Ethereum";
                        imagemPath = "/com/cripto/agi/agi/imagens/pngtree-vector-illustration-of-crytocurrency-ethereum-png-image_3314668-Photoroom.png";
                        break;
                    case 3:
                        nomeMoeda = "Solana";
                        imagemPath = "/com/cripto/agi/agi/imagens/6001527.png";
                        break;
                }

                ImageView icone = new ImageView(new Image(getClass().getResourceAsStream(imagemPath)));
                icone.setFitWidth(32);
                icone.setFitHeight(32);

                Label detalhes = new Label(nomeMoeda + " - R$ " + String.format("%.2f", a.getValor()));
                detalhes.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");

                Button cancelar = getCancelar(idCliente, a, nomeMoeda);

                assinaturaItem.getChildren().addAll(icone, detalhes, cancelar);
                assinaturasBox.getChildren().add(assinaturaItem);
            }
        }
    }

    private @NotNull Button getCancelar(Integer idCliente, Assinatura a, String nomeMoeda) {
        Button cancelar = new Button("Cancelar");
        cancelar.setStyle("-fx-background-color: #e53935; -fx-text-fill: white;");
        String finalNomeMoeda = nomeMoeda;

        cancelar.setOnAction(e -> {
            
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmar Cancelamento");
            confirmacao.setHeaderText("Tem certeza que deseja cancelar a assinatura?");
            confirmacao.setContentText("Assinatura da moeda " + finalNomeMoeda + " no valor de R$" + String.format("%.2f", a.getValor()));

            ButtonType botaoSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
            ButtonType botaoNao = new ButtonType("Não", ButtonBar.ButtonData.NO);
            confirmacao.getButtonTypes().setAll(botaoSim, botaoNao);

            confirmacao.showAndWait().ifPresent(resposta -> {
                if (resposta == botaoSim) {
                    boolean sucesso = assinaturaDAO.desativarAssinatura(idCliente, a.getIdAssinatura());
                    if (sucesso) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Assinatura Cancelada");
                        alert.setHeaderText(null);
                        alert.setContentText("Assinatura da moeda " + finalNomeMoeda + " no valor de R$" + String.format("%.2f", a.getValor()) + " foi cancelada com sucesso.");
                        alert.showAndWait();

                        carregarAssinaturasCliente(idCliente);
                    }
                }
            });
        });

        return cancelar;
    }


    @FXML
    public void initialize() {
        moedaChoiceBox.getItems().addAll("Bitcoin", "Ethereum", "Solana");
    }

}

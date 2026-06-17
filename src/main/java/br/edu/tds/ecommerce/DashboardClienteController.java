package br.edu.tds.ecommerce;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DashboardClienteController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private void initialize() {
        System.out.println("Dashboard Cliente carregado com sucesso!");
    }

    @FXML
    private void abrirTelaCadastroVeiculos() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/tds/ecommerce/telaCadastroVeiculos.fxml"));
        Parent root = loader.load();

        // Obter a Stage da janela atual
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(new Scene(root, 700, 600));
    }

    @FXML
    private void fazerLogout() throws IOException {
        System.out.println("Usuário desconectado!");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/tds/ecommerce/telaLogin.fxml"));
        Parent root = loader.load();

        // Obter a Stage da janela atual
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(new Scene(root, 700, 500));
        stage.setTitle("Login");
    }
}


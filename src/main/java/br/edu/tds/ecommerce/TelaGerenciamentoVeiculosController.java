package br.edu.tds.ecommerce;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TelaGerenciamentoVeiculosController implements Initializable {

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableColumn<Produto, String> colNome;

    @FXML
    private TableColumn<Produto, String> colCategoria;

    @FXML
    private TableColumn<Produto, Double> colPreco;

    @FXML
    private TableColumn<Produto, Integer> colQuantidade;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        carregarProdutos();
    }

    @FXML
    private void abrirTelaCadastroProdutos() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/tds/ecommerce/telaCadastroVeiculos.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) tabelaProdutos.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void carregarProdutos() {
        ObservableList<Produto> lista = FXCollections.observableArrayList();

        String sql = "SELECT * FROM produtos";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setCategoria(rs.getString("categoria"));
                p.setPreco(rs.getDouble("preco"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setImagem(rs.getString("imagem"));
                p.setAtivo(rs.getBoolean("ativo"));

                lista.add(p);
            }

            tabelaProdutos.setItems(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void excluirVeiculo() {
        Produto pSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (pSelecionado == null) {
            mostrarAlerta("Selecione um veículo");
            return;
        }

        String sql = "DELETE FROM produtos WHERE nome = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pSelecionado.getNome());
            stmt.executeUpdate();
            carregarProdutos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editarVeiculo() {
        Produto pSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (pSelecionado == null) {
            mostrarAlerta("Selecione um veículo");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/tds/ecommerce/telaCadastroVeiculos.fxml"));
            Parent root = loader.load();

            TelaCadastroVeiculosController controller = loader.getController();
            controller.setProduto(pSelecionado);

            Stage stage = (Stage) tabelaProdutos.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTelaLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/tds/ecommerce/telaLogin.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) tabelaProdutos.getScene().getWindow();
        stage.setScene(new Scene(root, 700, 500));
        stage.setTitle("Login");
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sistema");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}

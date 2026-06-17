package br.edu.tds.ecommerce;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaCadastroVeiculosController {

    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtAno;
    @FXML
    private TextField txtPreco;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private TextField txtPlaca;
    @FXML
    private TextField txtImagem;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private CheckBox cAtivo;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private void initialize() {
        // Populate ComboBox with vehicle types
        ObservableList<String> tipos = FXCollections.observableArrayList(
                "Carro",
                "Moto",
                "Caminhão",
                "Ônibus",
                "SUV",
                "Sedã",
                "Outro"
        );
        cbTipo.setItems(tipos);
    }

    @FXML
    private void salvarVeiculo() {
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String ano = txtAno.getText().trim();
        String precoStr = txtPreco.getText().trim();
        String tipo = cbTipo.getValue();
        String placa = txtPlaca.getText().trim();
        String imagem = txtImagem.getText().trim();
        String descricao = txtDescricao.getText().trim();
        boolean ativo = cAtivo.isSelected();

        // Validação básica
        if (marca.isEmpty() || modelo.isEmpty() || ano.isEmpty() || precoStr.isEmpty() || tipo == null) {
            System.out.println("Preencha todos os campos obrigatórios!");
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr);
            
            // Criar nome completo do veículo
            String nomeVeiculo = marca + " " + modelo + " " + ano;
            
            // Usar categoria como tipo de veículo
            Produto veiculo = new Produto(
                    nomeVeiculo,
                    descricao,
                    tipo,
                    preco,
                    1, // quantidade padrão
                    imagem,
                    ativo
            );

            ProdutoDAO dao = new ProdutoDAO();
            dao.cadastrarProduto(veiculo);

            System.out.println("Veículo cadastrado com sucesso!");
            
            // Voltar para dashboard
            voltarParaDashboard();

        } catch (NumberFormatException e) {
            System.out.println("Preço deve ser um valor numérico!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar veículo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelar() throws IOException {
        voltarParaDashboard();
    }

    private void voltarParaDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/tds/ecommerce/dashboardCliente.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}

package br.edu.tds.ecommerce;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaLoginController {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblSenha;

    @FXML
    private void abrirTelaCadastroUsuario() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/tds/ecommerce/telaCadastroUsuario.fxml"));

        Parent root = loader.load();

        TelaCadastroUsuarioController controller = loader.getController();

        //Trocando de tela 
        Stage stage = (Stage) txtUsuario.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void realizarLogin() throws IOException {

        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        if (usuario.isEmpty() && senha.isEmpty()) {
            lblUsuario.setText("* Campo usuário é obrigatório");
            lblSenha.setText("* Campo senha é obrigatório");
            System.out.println("Campo usuário e campo senha são obrigatórios");
            return;
        }

        if (usuario.isEmpty()) {
            lblUsuario.setText("* Campo usuário é obrigatório");
            lblSenha.setText("");
            System.out.println("Campo usuário é obrigatório");
            return;
        }
        if (senha.isEmpty()) {
            lblUsuario.setText("");
            lblSenha.setText("* Campo senha é obrigatório");
            System.out.println("Campo senha é obrigatório");
            return;
        }

        lblUsuario.setText("");
        lblSenha.setText("");

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuarioLogado = dao.loginComUsuario(usuario, senha);

        if (usuarioLogado != null) {
            //Login com sucesso
            System.out.println("Login feito para: " + usuarioLogado.getNomeCompleto());
            System.out.println("Role: " + usuarioLogado.getRole());

            String tela;
            String title;

            // Redireção baseada no role
            if ("admin".equalsIgnoreCase(usuarioLogado.getRole())) {
                tela = "/br/edu/tds/ecommerce/telaGerenciamentoUsuarios.fxml";
                title = "Gerenciamento de Usuários";
            } else {
                // client ou qualquer outro role
                tela = "/br/edu/tds/ecommerce/dashboardCliente.fxml";
                title = "Dashboard Cliente";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela));
            Parent root = loader.load();

            //Trocando de tela
            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } else {
            //Falha no login (usuário ou senha inválido)
            lblUsuario.setText("Usuário/Senha incorreto(a)");
            lblSenha.setText("Usuário/Senha incorreto(a)");
            System.out.println("Falha no login");
        }

        System.out.println("Usuário: " + usuario);
        System.out.println("Senha  : " + senha);

    }

    @FXML
    private void abrirTelaCadastroVeiculos() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/tds/ecommerce/telaCadastroVeiculos.fxml"));
        Parent root = loader.load();

        //Trocando de tela
        Stage stage = (Stage) txtUsuario.getScene().getWindow();
        stage.setScene(new Scene(root, 700, 600));
        stage.setTitle("Cadastro de Veículos");
    }
}


package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.ClienteDAO;
import com.example.lj1_bd_2sem.dao.UsuarioDAO;
import com.example.lj1_bd_2sem.model.Cliente;
import com.example.lj1_bd_2sem.model.Usuario;
import com.example.lj1_bd_2sem.util.ScreenManager;
import com.example.lj1_bd_2sem.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;

public class CadastroUsuarioController {
    @FXML private TextField txtNome, txtLogin, txtBalanca;
    @FXML private PasswordField txtSenha;
    @FXML private ComboBox<String> comboPerfil, comboTipoProfissional;
    @FXML private VBox boxClienteExclusivo, boxProfissionalExclusivo;
    @FXML private Button btnCadastrar;
    @FXML private Hyperlink linkLogin;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @FXML
    public void initialize() {
        boxClienteExclusivo.setManaged(false);
        boxClienteExclusivo.setVisible(false);
        boxProfissionalExclusivo.setManaged(false);
        boxProfissionalExclusivo.setVisible(false);

        comboPerfil.setItems(FXCollections.observableArrayList("ADM", "PROFISSIONAL", "CLIENTE"));
        comboTipoProfissional.setItems(FXCollections.observableArrayList("MERCADO", "FARMACIA", "SALAO"));
    }

    @FXML
    public void handlePerfilChange() {
        String perfil = comboPerfil.getValue();
        if (perfil == null) return;
        if (perfil.equals("CLIENTE")) {
            boxClienteExclusivo.setManaged(true);
            boxClienteExclusivo.setVisible(true);
            boxProfissionalExclusivo.setManaged(false);
            boxProfissionalExclusivo.setVisible(false);
        } else if (perfil.equals("PROFISSIONAL")) {
            boxProfissionalExclusivo.setManaged(true);
            boxProfissionalExclusivo.setVisible(true);
            boxClienteExclusivo.setManaged(false);
            boxClienteExclusivo.setVisible(false);
        } else {
            boxClienteExclusivo.setManaged(false);
            boxClienteExclusivo.setVisible(false);
            boxProfissionalExclusivo.setManaged(false);
            boxProfissionalExclusivo.setVisible(false);
        }
    }

    @FXML
    public void handleCadastrar() {
        String nome = txtNome.getText().trim();
        String login = txtLogin.getText().trim();
        String senha = txtSenha.getText();
        String perfil = comboPerfil.getValue();
        if (nome.isEmpty() || login.isEmpty() || senha.isEmpty() || perfil == null) {
            mostrarErro("Preencha todos os campos obrigatórios.");
            return;
        }
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setLogin(login);
        u.setSenha(senha);
        u.setPerfil(perfil);
        if (perfil.equals("PROFISSIONAL")) {
            u.setTipoProfissional(comboTipoProfissional.getValue());
            if (u.getTipoProfissional() == null) {
                mostrarErro("Selecione o tipo de profissional.");
                return;
            }
        }
        Stage stage = (Stage) btnCadastrar.getScene().getWindow();
        try {
            int id = usuarioDAO.registrarUsuario(u);
            u.setId(id);
            if (perfil.equals("CLIENTE")) {
                double balanca;
                try {
                    balanca = Double.parseDouble(txtBalanca.getText());
                } catch (NumberFormatException e) {
                    mostrarErro("Saldo inválido.");
                    return;
                }
                Cliente c = new Cliente();
                c.setId(id);
                c.setBalanca(balanca);
                clienteDAO.registrarCliente(c);
            }
            SessionManager.setUsuarioLogado(u);
            if (perfil.equals("CLIENTE")) ScreenManager.trocarTela("/home.fxml", stage, "Home");
            else if (perfil.equals("ADM")) ScreenManager.trocarTela("/dashboard.fxml", stage, "Dashboard");
            else if (perfil.equals("PROFISSIONAL")) ScreenManager.trocarTela("/preencher_estoque.fxml", stage, "Estoque");
        } catch (SQLException e) {
            mostrarErro("Erro ao cadastrar: " + e.getMessage());
        }
    }

    @FXML public void irParaTelaLogin() {
        Stage stage = (Stage) linkLogin.getScene().getWindow();
        ScreenManager.trocarTela("/login.fxml", stage, "Login");
    }

    private void mostrarErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.UsuarioDAO;
import com.example.lj1_bd_2sem.model.Usuario;
import com.example.lj1_bd_2sem.util.NotificationService;
import com.example.lj1_bd_2sem.util.ScreenManager;
import com.example.lj1_bd_2sem.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.SQLException;

public class LoginController {
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtSenha;
    @FXML private Button btnEntrar;
    @FXML private Label lblErro;
    @FXML private Hyperlink linkLogin;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void handleLogin() {
        String login = txtUsuario.getText();
        String senha = txtSenha.getText();
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            lblErro.setText("Preencha usuário e senha.");
            return;
        }
        Stage stage = (Stage) btnEntrar.getScene().getWindow();
        try {
            Usuario u = usuarioDAO.fazerLogin(login, senha);
            if (u != null) {
                SessionManager.setUsuarioLogado(u);
                if (u.getPerfil().equals("ADM")) ScreenManager.trocarTela("/dashboard.fxml", stage, "Dashboard");
                else if (u.getPerfil().equals("CLIENTE")) {
                    ScreenManager.trocarTela("/home.fxml", stage, "Home");
                    NotificationService.getInstance().startPolling(stage);
                }
                else if (u.getPerfil().equals("PROFISSIONAL")) ScreenManager.trocarTela("/menu_profissional.fxml", stage, "Menu Profissional");
                else lblErro.setText("Perfil não reconhecido.");
            } else {
                lblErro.setText("Usuário ou senha inválidos.");
            }
        } catch (SQLException e) {
            lblErro.setText("Erro ao conectar.");
        }
    }

    @FXML
    public void irParaTelaCadastro() {
        Stage stage = (Stage) linkLogin.getScene().getWindow();
        ScreenManager.trocarTela("/cadastro_usuario.fxml", stage, "Cadastro");
    }
}
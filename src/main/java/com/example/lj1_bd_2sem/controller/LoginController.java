package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.UsuarioDAO;
import com.example.lj1_bd_2sem.model.Usuario;
import com.example.lj1_bd_2sem.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btnEntrar;

    @FXML
    private Label lblErro;

    @FXML
    private Hyperlink linkLogin;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();


    @FXML
    public void initialize() {

    }

    @FXML
    public void handleLogin() {
        String login = txtUsuario.getText();
        String senha = txtSenha.getText();

        Stage stage = (Stage) linkLogin.getScene().getWindow();
        if (login != null){
            if (senha != null){
                try {
                    Usuario u = usuarioDAO.fazerLogin(login, senha);
                    if (u != null){
                        System.out.println(u.getPerfil());
                        if (u.getPerfil().equals("ADM")){
                            System.out.println("A");
                            ScreenManager.trocarTela("/dashboard.fxml", stage, "Dashboard");
                        } else if (u.getPerfil().equals("CLIENTE")) {
                            System.out.println("B");
                            ScreenManager.trocarTela("/home.fxml", stage, "Home");
                        } else {
                            System.out.println("C");
                            ScreenManager.trocarTela("/preencher_estoque.fxml", stage, "Estoque");
                        }

                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }

            }
        }
    }

    @FXML
    public void irParaTelaCadastro() {
        Stage stage = (Stage) linkLogin.getScene().getWindow();
        ScreenManager.trocarTela("/cadastro_usuario.fxml", stage, "Cadastro de Usuário");
    }
}
package com.example.lj1_bd_2sem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    @FXML
    public void initialize() {
        // Inicialização vazia
    }

    @FXML
    public void handleLogin() {
        // Método vazio
    }

    @FXML
    public void irParaTelaCadastro() {
        // Método vazio
    }
}
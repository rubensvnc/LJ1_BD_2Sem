package com.example.lj1_bd_2sem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CadastroUsuarioController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private ComboBox<?> comboPerfil;

    @FXML
    private VBox boxClienteExclusivo;

    @FXML
    private TextField txtBalanca;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Hyperlink linkLogin;

    @FXML
    public void initialize() {
        // Inicialização vazia
    }

    @FXML
    public void handlePerfilChange() {
        // Método vazio
    }

    @FXML
    public void handleCadastrar() {
        // Método vazio
    }

    @FXML
    public void irParaTelaLogin() {
        // Método vazio
    }
}
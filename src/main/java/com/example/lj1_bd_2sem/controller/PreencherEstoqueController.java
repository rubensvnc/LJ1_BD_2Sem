package com.example.lj1_bd_2sem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PreencherEstoqueController {

    @FXML
    private ComboBox<?> comboTipoNegocio;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCodigoBarras;

    @FXML
    private TextField txtPreco;

    @FXML
    private TextField txtEstoque;

    @FXML
    private VBox boxFarmacia;

    @FXML
    private DatePicker pickerValidade;

    @FXML
    private CheckBox chkPrecedeReceita;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    public void initialize() {
        // Inicialização vazia
    }

    @FXML
    public void handleTipoNegocioChange() {
        // Método vazio
    }

    @FXML
    public void handleCancelar() {
        // Método vazio
    }

    @FXML
    public void handleSalvar() {
        // Método vazio
    }
}
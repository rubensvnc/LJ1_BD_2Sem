package com.example.lj1_bd_2sem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CadastroServicoController {

    @FXML
    private TextField txtNomeServico;

    @FXML
    private TextField txtValorServico;

    @FXML
    private TextField txtDuracaoServico;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnSalvarServico;

    @FXML
    private TableView<?> tabelaServicos;

    @FXML
    private TableColumn<?, ?> colIdServico;

    @FXML
    private TableColumn<?, ?> colNomeServico;

    @FXML
    private TableColumn<?, ?> colValorServico;

    @FXML
    private TableColumn<?, ?> colDuracaoServico;

    @FXML
    public void initialize() {
        // Inicialização vazia
    }

    @FXML
    public void limparCampos() {
        // Método vazio
    }

    @FXML
    public void salvarServico() {
        // Método vazio
    }
}
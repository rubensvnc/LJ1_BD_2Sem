package com.example.lj1_bd_2sem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PagamentoAtendimentoController {

    @FXML
    private TableView<?> tabelaEmAndamento;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colHora;

    @FXML
    private TableColumn<?, ?> colCliente;

    @FXML
    private TableColumn<?, ?> colProfissional;

    @FXML
    private TableColumn<?, ?> colServico;

    @FXML
    private TextField txtIdAgendamento;

    @FXML
    private TextField txtIdCliente;

    @FXML
    private TextField txtBalancaCliente;

    @FXML
    private ComboBox<?> comboFormaPagamento;

    @FXML
    private Label lblValorServico;

    @FXML
    private Button btnFinalizarPagamento;

    @FXML
    public void initialize() {
        // Inicialização vazia
    }

    @FXML
    public void carregarDetalhesAgendamento() {
        // Método vazio
    }

    @FXML
    public void processarPagamento() {
        // Método vazio
    }
}
package com.example.lj1_bd_2sem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AgendamentoClienteController {

    @FXML
    private ComboBox<?> comboServicos;

    @FXML
    private ComboBox<?> comboProfissionais;

    @FXML
    private DatePicker dpData;

    @FXML
    private TextField txtHoraAgendado;

    @FXML
    private Button btnSolicitarAgendamento;

    @FXML
    private Button btnCancelarSelecao;

    @FXML
    private TableView<?> tabelaMeusAgendamentos;

    @FXML
    private TableColumn<?, ?> colHora;

    @FXML
    private TableColumn<?, ?> colServico;

    @FXML
    private TableColumn<?, ?> colProfissional;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    public void initialize() {
        // Inicialização vazia
    }

    @FXML
    public void solicitarAgendamento() {
        // Método vazio
    }

    @FXML
    public void limparSelecao() {
        // Método vazio
    }
}
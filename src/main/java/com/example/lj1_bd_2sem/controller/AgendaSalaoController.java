package com.example.lj1_bd_2sem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AgendaSalaoController {

    @FXML
    private TextField txtClienteId;

    @FXML
    private ComboBox<?> comboProfissionais;

    @FXML
    private ComboBox<?> comboStatus;

    @FXML
    private ComboBox<?> comboServicos;

    @FXML
    private DatePicker pickerData;

    @FXML
    private TextField txtHora;

    @FXML
    private Button btnAgendar;

    @FXML
    private TableView<?> tabelaAgenda;

    @FXML
    private TableColumn<?, ?> colHora;

    @FXML
    private TableColumn<?, ?> colCliente;

    @FXML
    private TableColumn<?, ?> colProfissional;

    @FXML
    private TableColumn<?, ?> colServico;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    public void initialize() {
        // Inicialização vazia
    }

    @FXML
    public void salvarAgendamento() {
        // Método vazio
    }
}
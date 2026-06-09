package com.example.lj1_bd_2sem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class VendasProdutosController {

    @FXML
    private TableView<?> tabelaCarrinho;

    @FXML
    private TableColumn<?, ?> colProduto;

    @FXML
    private TableColumn<?, ?> colQtd;

    @FXML
    private TableColumn<?, ?> colPrecoUnitario;

    @FXML
    private TableColumn<?, ?> colTotalItem;

    @FXML
    private TextField txtCodigoProduto;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private Button btnAdicionar;

    @FXML
    private TableView<?> tabelaCarrinho1;

    @FXML
    private TableColumn<?, ?> colProduto1;

    @FXML
    private TableColumn<?, ?> colQtd1;

    @FXML
    private TableColumn<?, ?> colPrecoUnitario1;

    @FXML
    private TableColumn<?, ?> colTotalItem1;

    @FXML
    private ComboBox<?> comboPagamento;

    @FXML
    private Label lblTotalVenda;

    @FXML
    private Button btnFinalizarVenda;

    @FXML
    public void initialize() {
        // Inicialização vazia
    }

    @FXML
    public void adicionarItem() {
        // Método vazio
    }

    @FXML
    public void finalizarVenda() {
        // Método vazio
    }


}
package com.example.lj1_bd_2sem.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;

public class DashboardController {

    @FXML
    private Button btnModuloSupermercado;

    @FXML
    private Button btnModuloFarmacia;

    @FXML
    private Button btnModuloSalao;

    @FXML
    private Button btnSair;

    @FXML
    private BarChart<?, ?> graficoFaturamento;

    @FXML
    private CategoryAxis xAxisNegocios;

    @FXML
    private NumberAxis yAxisValores;

    @FXML
    public void initialize() {
        // Inicialização vazia
    }

    @FXML
    public void abrirSupermercado() {
        // Método vazio
    }

    @FXML
    public void abrirFarmacia() {
        // Método vazio
    }

    @FXML
    public void abrirSalao() {
        // Método vazio
    }

    @FXML
    public void handleLogout() {
        // Método vazio
    }
}
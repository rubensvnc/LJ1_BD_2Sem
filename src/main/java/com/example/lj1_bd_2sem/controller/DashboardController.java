package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.AgendaDAO;
import com.example.lj1_bd_2sem.dao.VendaDAO;
import com.example.lj1_bd_2sem.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class DashboardController {
    @FXML private BarChart<String, Number> graficoFaturamento;
    @FXML private ComboBox<String> comboNegocio;
    @FXML private Button btnCarregar, btnSair;

    private VendaDAO vendaDAO = new VendaDAO();
    private AgendaDAO agendaDAO = new AgendaDAO();

    @FXML
    public void initialize() {
        comboNegocio.getItems().addAll("MERCADO", "FARMACIA", "SALAO");
        comboNegocio.setValue("MERCADO");
        carregarGrafico();
    }

    @FXML
    public void carregarGrafico() {
        String negocio = comboNegocio.getValue();
        graficoFaturamento.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Faturamento - " + negocio);
        if (negocio.equals("SALAO")) {
            double total = agendaDAO.calcularFaturamentoPorStatus("CONCLUIDO");
            series.getData().add(new XYChart.Data<>("Salão", total));
        } else {
            double total = vendaDAO.calcularFaturamentoPorTipo(negocio);
            series.getData().add(new XYChart.Data<>(negocio, total));
        }
        graficoFaturamento.getData().add(series);
    }

    @FXML
    public void handleLogout() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        ScreenManager.trocarTela("/login.fxml", stage, "Login");
    }
}
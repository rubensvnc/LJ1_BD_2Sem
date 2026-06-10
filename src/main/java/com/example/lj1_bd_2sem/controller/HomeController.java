package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Button gotoMarketBtn;

    @FXML
    private Button gotoSalonBtn;

    @FXML
    private Button gotoPharmacyBtn;

    @FXML
    public void initialize() {

    }

    @FXML
    public void gotoMarket() {
        Stage stage = (Stage) gotoMarketBtn.getScene().getWindow();
        ScreenManager.trocarTela("/vendas_produtos.fxml", stage, "Supermercado - Vendas");
    }

    @FXML
    public void gotoSalon() {
        Stage stage = (Stage) gotoSalonBtn.getScene().getWindow();
        ScreenManager.trocarTela("/agendamento_servico.fxml", stage, "Salão de Beleza - Agendamentos");
    }

    @FXML
    public void gotoPharmacy() {
        Stage stage = (Stage) gotoPharmacyBtn.getScene().getWindow();
        ScreenManager.trocarTela("/vendas_produtos.fxml", stage, "Farmácia - Vendas");
    }

    private void mostrarMensagem(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
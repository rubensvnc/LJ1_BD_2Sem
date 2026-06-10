package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.AgendaDAO;
import com.example.lj1_bd_2sem.util.ScreenManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AguardandoPagamentoController {
    @FXML private Label lblMensagem;
    @FXML private Button btnLimparCabelos;
    private int agendaId;
    private AgendaDAO agendaDAO = new AgendaDAO();
    private Timeline pollingTimeline;

    public void setAgendamento(int agendaId, boolean saldoInsuficiente) {
        this.agendaId = agendaId;
        if (saldoInsuficiente) {
            lblMensagem.setText("Saldo insuficiente!\nClique no botão para finalizar.");
            btnLimparCabelos.setVisible(true);
            btnLimparCabelos.setManaged(true);
        } else {
            lblMensagem.setText("Aguardando confirmação do pagamento");
            btnLimparCabelos.setVisible(false);
            btnLimparCabelos.setManaged(false);
            iniciarPollingPagamento();
        }
    }

    private void iniciarPollingPagamento() {
        pollingTimeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            // Verifica se o agendamento foi pago
            if (agendaDAO.isPago(agendaId)) {
                pollingTimeline.stop();
                Stage stage = (Stage) lblMensagem.getScene().getWindow();
                ScreenManager.trocarTela("/home.fxml", stage, "Home");
            }
        }));
        pollingTimeline.setCycleCount(Timeline.INDEFINITE);
        pollingTimeline.play();
    }

    @FXML
    public void limparCabelosChao() {
        agendaDAO.atualizarPago(agendaId, true);
        agendaDAO.atualizarStatus(agendaId, "CONCLUIDO");
        Stage stage = (Stage) btnLimparCabelos.getScene().getWindow();
        ScreenManager.trocarTela("/home.fxml", stage, "Home");
    }
}
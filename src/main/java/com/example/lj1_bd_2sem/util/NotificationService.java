package com.example.lj1_bd_2sem.util;

import com.example.lj1_bd_2sem.controller.ExecutandoServicoController;
import com.example.lj1_bd_2sem.dao.AgendaDAO;
import com.example.lj1_bd_2sem.dto.AgendamentoDTO;
import com.example.lj1_bd_2sem.model.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationService {
    private static NotificationService instance;
    private Timeline pollingTimeline;
    private AgendaDAO agendaDAO = new AgendaDAO();
    private Map<Integer, String> lastKnownStatus = new HashMap<>();
    private Stage primaryStage;

    private NotificationService() {}

    public static NotificationService getInstance() {
        if (instance == null) instance = new NotificationService();
        return instance;
    }

    public void startPolling(Stage stage) {
        this.primaryStage = stage;
        if (pollingTimeline != null) pollingTimeline.stop();

        verificarAgendamentoEmAndamento();

        pollingTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            Usuario cliente = SessionManager.getUsuarioLogado();
            if (cliente == null || !"CLIENTE".equals(cliente.getPerfil())) return;

            List<AgendamentoDTO> agendamentos = agendaDAO.listarPorCliente(cliente.getId());
            for (AgendamentoDTO dto : agendamentos) {
                Integer id = dto.getId();
                String currentStatus = dto.getStatusConclusao();
                String lastStatus = lastKnownStatus.get(id);

                if (lastStatus == null) {
                    lastKnownStatus.put(id, currentStatus);
                } else if (!lastStatus.equals(currentStatus)) {
                    lastKnownStatus.put(id, currentStatus);
                    if ("EM ANDAMENTO".equals(currentStatus)) {
                        Platform.runLater(() -> abrirTelaExecucao(id));
                        break;
                    }
                }
            }
        }));
        pollingTimeline.setCycleCount(Timeline.INDEFINITE);
        pollingTimeline.play();
    }

    private void verificarAgendamentoEmAndamento() {
        Usuario cliente = SessionManager.getUsuarioLogado();
        if (cliente != null && "CLIENTE".equals(cliente.getPerfil())) {
            List<AgendamentoDTO> agendamentos = agendaDAO.listarPorCliente(cliente.getId());
            for (AgendamentoDTO dto : agendamentos) {
                if ("EM ANDAMENTO".equals(dto.getStatusConclusao())) {
                    Platform.runLater(() -> abrirTelaExecucao(dto.getId()));
                    break;
                }
            }
        }
    }

    public void stopPolling() {
        if (pollingTimeline != null) {
            pollingTimeline.stop();
            pollingTimeline = null;
        }
        lastKnownStatus.clear();
    }

    private void abrirTelaExecucao(int agendaId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/executando_servico.fxml"));
            Parent root = loader.load();
            ExecutandoServicoController controller = loader.getController();
            controller.setAgendamento(agendaId);
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Executando Serviço");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
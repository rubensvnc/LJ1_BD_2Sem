package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.AgendaDAO;
import com.example.lj1_bd_2sem.dao.ServicoDAO;
import com.example.lj1_bd_2sem.model.Agenda;
import com.example.lj1_bd_2sem.model.Servico;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import java.io.File;

public class ExecutandoServicoController {
    @FXML private Label lblStatusServico;
    @FXML private MediaView mediaViewVideo;
    @FXML private Label lblTempoRestante;
    @FXML private ProgressBar progressServico;

    private Timeline timeline;
    private int duracaoTotalSegundos;
    private int segundosRestantes;
    private int agendaId;

    // Método alterado para receber apenas o agendaId
    public void setAgendamento(int agendaId) {
        this.agendaId = agendaId;
        AgendaDAO agendaDAO = new AgendaDAO();
        Agenda agenda = agendaDAO.buscarPorId(agendaId);
        if (agenda != null) {
            ServicoDAO servicoDAO = new ServicoDAO();
            Servico servico = servicoDAO.buscarPorId(agenda.getServicoId());
            if (servico != null) {
                this.duracaoTotalSegundos = servico.getDuracao();
                this.segundosRestantes = duracaoTotalSegundos;
                iniciarCronometro();
                carregarVideo(servico.getNome());
            } else {
                lblStatusServico.setText("Erro: Serviço não encontrado.");
            }
        } else {
            lblStatusServico.setText("Erro: Agendamento não encontrado.");
        }
    }

    private void iniciarCronometro() {
        progressServico.setProgress(0.0);
        atualizarLabelTempo();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (segundosRestantes > 0) {
                segundosRestantes--;
                double progresso = (double) (duracaoTotalSegundos - segundosRestantes) / duracaoTotalSegundos;
                progressServico.setProgress(progresso);
                atualizarLabelTempo();
            } else {
                timeline.stop();
                lblStatusServico.setText("Serviço Concluído!");
                AgendaDAO agendaDAO = new AgendaDAO();
                agendaDAO.atualizarStatus(agendaId, "CONCLUIDO");
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // Exibe apenas segundos restantes
    private void atualizarLabelTempo() {
        lblTempoRestante.setText("Tempo restante: " + segundosRestantes + " segundos");
    }

    private void carregarVideo(String nomeServico) {
        String videoPath = "videos/" + nomeServico.toLowerCase().replace(" ", "_") + ".mp4";
        File videoFile = new File(videoPath);
        if (videoFile.exists()) {
            Media media = new Media(videoFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaViewVideo.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        } else {
            lblStatusServico.setText("Vídeo não encontrado para " + nomeServico);
        }
    }

    public void pararCronometro() {
        if (timeline != null) timeline.stop();
    }
}
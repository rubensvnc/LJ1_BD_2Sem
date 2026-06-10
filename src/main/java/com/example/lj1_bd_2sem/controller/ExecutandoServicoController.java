package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.AgendaDAO;
import com.example.lj1_bd_2sem.dao.ClienteDAO;
import com.example.lj1_bd_2sem.dao.ServicoDAO;
import com.example.lj1_bd_2sem.model.Agenda;
import com.example.lj1_bd_2sem.model.Cliente;
import com.example.lj1_bd_2sem.model.Servico;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;

public class ExecutandoServicoController {
    @FXML private Label lblStatusServico;
    @FXML private ImageView imageViewGif;
    @FXML private Label lblTempoRestante;
    @FXML private ProgressBar progressServico;

    private Timeline timeline;
    private int duracaoTotalSegundos;
    private int segundosRestantes;
    private int agendaId;
    private AgendaDAO agendaDAO = new AgendaDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private ServicoDAO servicoDAO = new ServicoDAO();

    public void setAgendamento(int agendaId) {
        this.agendaId = agendaId;
        Agenda agenda = agendaDAO.buscarPorId(agendaId);
        if (agenda != null) {
            Servico servico = servicoDAO.buscarPorId(agenda.getServicoId());
            if (servico != null) {
                this.duracaoTotalSegundos = servico.getDuracao();
                this.segundosRestantes = duracaoTotalSegundos;
                iniciarCronometro();
                carregarGif(servico.getNome());
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
                agendaDAO.atualizarStatus(agendaId, "CONCLUIDO");
                verificarSaldoERedirecionarCliente();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void verificarSaldoERedirecionarCliente() {
        Agenda agenda = agendaDAO.buscarPorId(agendaId);
        if (agenda == null) return;
        Cliente cliente = clienteDAO.buscarPorId(agenda.getClienteId());
        Servico servico = servicoDAO.buscarPorId(agenda.getServicoId());
        double valorServico = (servico != null) ? servico.getValor() : 0;
        boolean insuficiente = (cliente == null || cliente.getBalanca() < valorServico);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aguardando_pagamento.fxml"));
            Parent root = loader.load();
            AguardandoPagamentoController controller = loader.getController();
            controller.setAgendamento(agendaId, insuficiente);
            Stage stage = (Stage) lblStatusServico.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Aguardando Pagamento");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void atualizarLabelTempo() {
        lblTempoRestante.setText("Tempo restante: " + segundosRestantes + " segundos");
    }

    private void carregarGif(String nomeServico) {
        String nomeArquivo = nomeServico.toLowerCase().replace(" ", "_") + ".gif";
        String resourcePath = "/videos/" + nomeArquivo;

        java.net.URL resourceUrl = getClass().getResource(resourcePath);
        if (resourceUrl != null) {
            Image gifImage = new Image(resourceUrl.toString());
            imageViewGif.setImage(gifImage);
            return;
        }

        File gifFile = new File("videos/" + nomeArquivo);
        if (gifFile.exists()) {
            Image gifImage = new Image(gifFile.toURI().toString());
            imageViewGif.setImage(gifImage);
        } else {
            lblStatusServico.setText("GIF não encontrado: " + nomeArquivo);
        }
    }

    public void pararCronometro() {
        if (timeline != null) timeline.stop();
    }
}
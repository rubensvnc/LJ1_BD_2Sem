package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.AgendaDAO;
import com.example.lj1_bd_2sem.dao.ClienteDAO;
import com.example.lj1_bd_2sem.dao.ServicoDAO;
import com.example.lj1_bd_2sem.model.Agenda;
import com.example.lj1_bd_2sem.model.Cliente;
import com.example.lj1_bd_2sem.model.Servico;
import com.example.lj1_bd_2sem.util.ScreenManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AguardandoPagamentoController {
    @FXML private Label lblMensagem;
    @FXML private Button btnLimparCabelos;
    private int agendaId;
    private AgendaDAO agendaDAO = new AgendaDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();


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
        // Buscar agendamento e serviço
        Agenda agenda = agendaDAO.buscarPorId(agendaId);
        if (agenda == null) {
            mostrarAlerta("Agendamento não encontrado.");
            return;
        }
        Servico servico = new ServicoDAO().buscarPorId(agenda.getServicoId());
        if (servico == null) {
            mostrarAlerta("Serviço não encontrado.");
            return;
        }
        double valorServico = servico.getValor();

        // Buscar cliente atual
        Cliente cliente = clienteDAO.buscarPorId(agenda.getClienteId());
        if (cliente == null) {
            mostrarAlerta("Cliente não encontrado.");
            return;
        }

        double saldoAtual = cliente.getBalanca();
        double valorFaltante = valorServico - saldoAtual;

        // Adiciona a diferença para que o saldo fique igual ao valor do serviço
        if (valorFaltante > 0) {
            double novoSaldo = saldoAtual + valorFaltante;
            clienteDAO.atualizarSaldo(cliente.getId(), novoSaldo);
            System.out.println("LIMPAR CABELOS: Crédito de R$" + valorFaltante + " aplicado. Saldo agora: R$" + novoSaldo);
            // Recarrega o cliente atualizado
            cliente = clienteDAO.buscarPorId(cliente.getId());
        }

        // Agora o saldo é suficiente (ou já era). Efetua o pagamento (debitando o valor)
        if (cliente.getBalanca() >= valorServico) {
            double novoSaldo = cliente.getBalanca() - valorServico;
            clienteDAO.atualizarSaldo(cliente.getId(), novoSaldo);
            // Marca como pago e concluído
            agendaDAO.atualizarPago(agendaId, true);
            agendaDAO.atualizarStatus(agendaId, "CONCLUIDO");
            mostrarAlerta("Procedimento finalizado! Saldo restante: R$ " + String.format("%.2f", novoSaldo));
        } else {
            mostrarAlerta("Erro: Saldo ainda insuficiente após crédito.");
            return;
        }

        // Retorna para a home do cliente
        Stage stage = (Stage) btnLimparCabelos.getScene().getWindow();
        ScreenManager.trocarTela("/home.fxml", stage, "Home");
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
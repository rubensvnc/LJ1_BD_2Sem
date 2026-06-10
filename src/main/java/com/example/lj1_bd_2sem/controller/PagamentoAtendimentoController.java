package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.AgendaDAO;
import com.example.lj1_bd_2sem.dao.ClienteDAO;
import com.example.lj1_bd_2sem.dao.ServicoDAO;
import com.example.lj1_bd_2sem.dto.AgendamentoDTO;
import com.example.lj1_bd_2sem.model.Agenda;
import com.example.lj1_bd_2sem.model.Cliente;
import com.example.lj1_bd_2sem.model.Servico;
import com.example.lj1_bd_2sem.model.Usuario;
import com.example.lj1_bd_2sem.util.ScreenManager;
import com.example.lj1_bd_2sem.util.SessionManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PagamentoAtendimentoController {
    @FXML private Button btnVoltarMenu;
    @FXML private TableView<AgendamentoDTO> tabelaEmAndamento;
    @FXML private TableColumn<AgendamentoDTO, Integer> colId;
    @FXML private TableColumn<AgendamentoDTO, String> colHora, colCliente, colProfissional, colServico;
    @FXML private TextField txtIdAgendamento, txtIdCliente, txtBalancaCliente;
    @FXML private ComboBox<String> comboFormaPagamento;
    @FXML private Label lblValorServico;
    @FXML private Button btnFinalizarPagamento;

    private Timeline pollingTimeline;

    private AgendaDAO agendaDAO = new AgendaDAO();
    private ServicoDAO servicoDAO = new ServicoDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private ObservableList<AgendamentoDTO> emAndamentoList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("horaAgendado"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("clienteNome"));
        colProfissional.setCellValueFactory(new PropertyValueFactory<>("profissionalNome"));
        colServico.setCellValueFactory(new PropertyValueFactory<>("servicoNome"));
        comboFormaPagamento.getItems().addAll("Dinheiro", "Cartão Débito", "Cartão Crédito", "PIX");
        carregarAgendamentos();
        tabelaEmAndamento.setItems(emAndamentoList);
        tabelaEmAndamento.setOnMouseClicked(e -> carregarDetalhesAgendamento());

        iniciarPolling();
    }

    private void carregarAgendamentos() {
        emAndamentoList.clear();
        Usuario profissional = SessionManager.getUsuarioLogado();
        if (profissional != null && "PROFISSIONAL".equals(profissional.getPerfil())) {
            emAndamentoList.addAll(agendaDAO.listarConcluidosNaoPagosPorProfissional(profissional.getId()));
        }
    }

    @FXML
    public void carregarDetalhesAgendamento() {
        AgendamentoDTO selecionado = tabelaEmAndamento.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;
        txtIdAgendamento.setText(String.valueOf(selecionado.getId()));
        txtIdCliente.setText(selecionado.getClienteNome());
        Agenda a = agendaDAO.buscarPorId(selecionado.getId());
        if (a != null) {
            Servico s = servicoDAO.buscarPorId(a.getServicoId());
            if (s != null) lblValorServico.setText(String.format("R$ %.2f", s.getValor()));
            Cliente c = clienteDAO.buscarPorId(a.getClienteId());
            if (c != null) {
                txtBalancaCliente.setText(String.format("R$ %.2f", c.getBalanca()));
            } else {
                txtBalancaCliente.setText("Cliente não encontrado!");
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Cliente não encontrado. Verifique a tabela cliente.");
            }
        }
    }

    @FXML
    public void processarPagamento() {
        if (txtIdAgendamento.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Selecione um agendamento.");
            return;
        }
        int agendaId = Integer.parseInt(txtIdAgendamento.getText());
        Agenda a = agendaDAO.buscarPorId(agendaId);
        if (a == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Agendamento não encontrado.");
            return;
        }
        Servico s = servicoDAO.buscarPorId(a.getServicoId());
        if (s == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Serviço não encontrado.");
            return;
        }
        Cliente cliente = clienteDAO.buscarPorId(a.getClienteId());
        if (cliente == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Cliente não encontrado na base de dados.");
            return;
        }
        double valorServico = s.getValor();
        double saldoAtual = cliente.getBalanca();
        String formaPagamento = comboFormaPagamento.getValue();
        if (formaPagamento == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Selecione uma forma de pagamento.");
            return;
        }
        if (saldoAtual >= valorServico) {
            double novoSaldo = saldoAtual - valorServico;
            clienteDAO.atualizarSaldo(cliente.getId(), novoSaldo);
            agendaDAO.atualizarStatus(agendaId, "CONCLUIDO");
            agendaDAO.atualizarPago(agendaId, true);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Pagamento realizado! Saldo do cliente: R$ " + String.format("%.2f", novoSaldo));
            carregarAgendamentos();
            limparCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Saldo insuficiente", "Cliente não possui saldo suficiente. Utilize a opção de limpeza.");
        }
    }

    public void setAgendamentoId(int agendaId) {
        Platform.runLater(() -> {
            for (AgendamentoDTO dto : tabelaEmAndamento.getItems()) {
                if (dto.getId() == agendaId) {
                    tabelaEmAndamento.getSelectionModel().select(dto);
                    carregarDetalhesAgendamento();
                    break;
                }
            }
        });
    }

    private void limparCampos() {
        txtIdAgendamento.clear();
        txtIdCliente.clear();
        txtBalancaCliente.clear();
        lblValorServico.setText("R$ 0,00");
        comboFormaPagamento.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String msg) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void iniciarPolling() {
        pollingTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            carregarAgendamentos();
        }));
        pollingTimeline.setCycleCount(Timeline.INDEFINITE);
        pollingTimeline.play();
    }

    @FXML
    public void voltarMenu() {
        Stage stage = (Stage) btnVoltarMenu.getScene().getWindow();
        ScreenManager.trocarTela("/menu_profissional.fxml", stage, "Menu Profissional");
    }
}
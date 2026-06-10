package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.AgendaDAO;
import com.example.lj1_bd_2sem.dao.ClienteDAO;
import com.example.lj1_bd_2sem.dao.ServicoDAO;
import com.example.lj1_bd_2sem.dto.AgendamentoDTO;
import com.example.lj1_bd_2sem.model.Agenda;
import com.example.lj1_bd_2sem.model.Cliente;
import com.example.lj1_bd_2sem.model.Servico;
import com.example.lj1_bd_2sem.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PagamentoAtendimentoController {

    @FXML private Button btnVoltarMenu;

    @FXML
    private TableView<AgendamentoDTO> tabelaEmAndamento;
    @FXML
    private TableColumn<AgendamentoDTO, Integer> colId;
    @FXML
    private TableColumn<AgendamentoDTO, String> colHora;
    @FXML
    private TableColumn<AgendamentoDTO, String> colCliente;
    @FXML
    private TableColumn<AgendamentoDTO, String> colProfissional;
    @FXML
    private TableColumn<AgendamentoDTO, String> colServico;
    @FXML
    private TextField txtIdAgendamento;
    @FXML
    private TextField txtIdCliente;
    @FXML
    private TextField txtBalancaCliente;
    @FXML
    private ComboBox<String> comboFormaPagamento;
    @FXML
    private Label lblValorServico;
    @FXML
    private Button btnFinalizarPagamento;

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

        carregarFormasPagamento();
        carregarAgendamentosEmAndamento();

        tabelaEmAndamento.setItems(emAndamentoList);
    }

    @FXML
    public void voltarMenu() {
        Stage stage = (Stage) btnVoltarMenu.getScene().getWindow();
        ScreenManager.trocarTela("/menu_profissional.fxml", stage, "Menu Profissional");
    }

    private void carregarFormasPagamento() {
        comboFormaPagamento.getItems().clear();
        comboFormaPagamento.getItems().addAll("Dinheiro", "Cartão Débito", "Cartão Crédito", "PIX");
    }

    private void carregarAgendamentosEmAndamento() {
        emAndamentoList.clear();
        emAndamentoList.addAll(agendaDAO.listarPorStatus("EM ANDAMENTO"));
    }

    @FXML
    public void carregarDetalhesAgendamento() {
        AgendamentoDTO selecionado = tabelaEmAndamento.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Selecione um agendamento na tabela.");
            return;
        }

        txtIdAgendamento.setText(String.valueOf(selecionado.getId()));
        txtIdCliente.setText(selecionado.getClienteNome());

        Agenda agenda = agendaDAO.buscarPorId(selecionado.getId());
        if (agenda != null) {
            Servico servico = servicoDAO.buscarPorId(agenda.getServicoId());
            if (servico != null) {
                lblValorServico.setText(String.format("R$ %.2f", servico.getValor()));
            } else {
                lblValorServico.setText("R$ 0,00");
            }

            Cliente cliente = clienteDAO.buscarPorId(agenda.getClienteId());
            if (cliente != null) {
                txtBalancaCliente.setText(String.format("R$ %.2f", cliente.getBalanca()));
            } else {
                txtBalancaCliente.setText("R$ 0,00");
            }
        }
    }

    @FXML
    public void processarPagamento() {
        if (txtIdAgendamento.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Nenhum agendamento selecionado.");
            return;
        }

        int agendaId = Integer.parseInt(txtIdAgendamento.getText());
        Agenda agenda = agendaDAO.buscarPorId(agendaId);
        if (agenda == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Agendamento não encontrado.");
            return;
        }

        Servico servico = servicoDAO.buscarPorId(agenda.getServicoId());
        if (servico == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Serviço não encontrado.");
            return;
        }

        double valorServico = servico.getValor();
        Cliente cliente = clienteDAO.buscarPorId(agenda.getClienteId());
        if (cliente == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Cliente não encontrado.");
            return;
        }

        double saldoAtual = cliente.getBalanca();
        String formaPagamento = comboFormaPagamento.getSelectionModel().getSelectedItem();
        if (formaPagamento == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Selecione uma forma de pagamento.");
            return;
        }

        if (saldoAtual >= valorServico) {
            double novoSaldo = saldoAtual - valorServico;
            clienteDAO.atualizarSaldo(cliente.getId(), novoSaldo);
            agendaDAO.atualizarStatus(agendaId, "CONCLUIDO");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Pagamento realizado com sucesso! Saldo atual: R$ " + String.format("%.2f", novoSaldo));
            carregarAgendamentosEmAndamento();
            limparCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Saldo insuficiente", "O cliente não possui saldo suficiente para pagar o serviço.");
        }
    }

    private void limparCampos() {
        txtIdAgendamento.clear();
        txtIdCliente.clear();
        txtBalancaCliente.clear();
        lblValorServico.setText("R$ 0,00");
        comboFormaPagamento.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
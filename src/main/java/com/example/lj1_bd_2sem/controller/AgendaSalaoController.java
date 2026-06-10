package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.AgendaDAO;
import com.example.lj1_bd_2sem.dao.ServicoDAO;
import com.example.lj1_bd_2sem.dao.UsuarioDAO;
import com.example.lj1_bd_2sem.dto.AgendamentoDTO;
import com.example.lj1_bd_2sem.model.Servico;
import com.example.lj1_bd_2sem.model.Usuario;
import com.example.lj1_bd_2sem.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AgendaSalaoController {
    @FXML private Button btnVoltarMenu;
    @FXML private TextField txtClienteId;
    @FXML private ComboBox<Usuario> comboProfissionais;
    @FXML private ComboBox<String> comboStatus;
    @FXML private ComboBox<Servico> comboServicos;
    @FXML private DatePicker pickerData;
    @FXML private TextField txtHora;
    @FXML private Button btnAgendar;
    @FXML private TableView<AgendamentoDTO> tabelaAgenda;
    @FXML private TableColumn<AgendamentoDTO, String> colHora, colCliente, colProfissional, colServico, colStatus;

    private AgendaDAO agendaDAO = new AgendaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ServicoDAO servicoDAO = new ServicoDAO();
    private ObservableList<AgendamentoDTO> agendaList = FXCollections.observableArrayList();
    private ObservableList<Usuario> profissionaisList = FXCollections.observableArrayList();
    private ObservableList<Servico> servicosList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colHora.setCellValueFactory(new PropertyValueFactory<>("horaAgendado"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("clienteNome"));
        colProfissional.setCellValueFactory(new PropertyValueFactory<>("profissionalNome"));
        colServico.setCellValueFactory(new PropertyValueFactory<>("servicoNome"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusConclusao"));

        carregarProfissionais();
        carregarServicos();
        carregarStatus();
        carregarAgenda();

        comboProfissionais.setItems(profissionaisList);
        comboServicos.setItems(servicosList);
        tabelaAgenda.setItems(agendaList);

        tabelaAgenda.getSelectionModel().selectedItemProperty().addListener((obs, old, newSelection) -> {
            if (newSelection != null) carregarDetalhesAgendamento(newSelection);
        });
    }

    @FXML
    public void voltarMenu() {
        Stage stage = (Stage) btnVoltarMenu.getScene().getWindow();
        ScreenManager.trocarTela("/menu_profissional.fxml", stage, "Menu Profissional");
    }

    private void carregarProfissionais() {
        profissionaisList.clear();
        profissionaisList.addAll(usuarioDAO.listarProfissionaisPorTipo("SALAO"));
    }

    private void carregarServicos() {
        servicosList.clear();
        servicosList.addAll(servicoDAO.listarTodos());
    }

    private void carregarStatus() {
        comboStatus.getItems().setAll("PENDENTE", "EM ANDAMENTO", "CONCLUIDO", "CANCELADO");
    }

    private void carregarAgenda() {
        agendaList.clear();
        agendaList.addAll(agendaDAO.listarTodos());
    }

    private void carregarDetalhesAgendamento(AgendamentoDTO a) {
        txtClienteId.setText(a.getClienteNome());
        for (Usuario u : profissionaisList) if (u.getNome().equals(a.getProfissionalNome())) { comboProfissionais.setValue(u); break; }
        for (Servico s : servicosList) if (s.getNome().equals(a.getServicoNome())) { comboServicos.setValue(s); break; }
        comboStatus.setValue(a.getStatusConclusao());
        pickerData.setValue(LocalDate.now());
        txtHora.setText(a.getHoraAgendado().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @FXML
    public void salvarAgendamento() {
        AgendamentoDTO selecionado = tabelaAgenda.getSelectionModel().getSelectedItem();
        if (selecionado == null) { mostrarAlerta("Selecione um agendamento."); return; }
        String novoStatus = comboStatus.getValue();
        if (novoStatus == null) { mostrarAlerta("Selecione um status."); return; }
        agendaDAO.atualizarStatus(selecionado.getId(), novoStatus);
        mostrarAlerta("Status atualizado!");
        carregarAgenda();
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
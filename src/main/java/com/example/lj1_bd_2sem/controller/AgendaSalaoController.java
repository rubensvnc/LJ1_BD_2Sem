package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.AgendaDAO;
import com.example.lj1_bd_2sem.dao.ServicoDAO;
import com.example.lj1_bd_2sem.dao.UsuarioDAO;
import com.example.lj1_bd_2sem.dto.AgendamentoDTO;
import com.example.lj1_bd_2sem.model.Agenda;
import com.example.lj1_bd_2sem.model.Servico;
import com.example.lj1_bd_2sem.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AgendaSalaoController {

    @FXML private TextField txtClienteId;
    @FXML private ComboBox<Usuario> comboProfissionais;
    @FXML private ComboBox<String> comboStatus;
    @FXML private ComboBox<Servico> comboServicos;
    @FXML private DatePicker pickerData;
    @FXML private TextField txtHora;
    @FXML private Button btnSalvarAlteracoes;
    @FXML private Button btnCancelarSelecao;
    @FXML private TableView<AgendamentoDTO> tabelaAgenda;
    @FXML private TableColumn<AgendamentoDTO, String> colHora;
    @FXML private TableColumn<AgendamentoDTO, String> colCliente;
    @FXML private TableColumn<AgendamentoDTO, String> colProfissional;
    @FXML private TableColumn<AgendamentoDTO, String> colServico;
    @FXML private TableColumn<AgendamentoDTO, String> colStatus;

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

        // Inicialmente, desabilitar campos de edição
        setCamposEditaveis(false);
        btnSalvarAlteracoes.setDisable(true);

        tabelaAgenda.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                carregarDetalhesAgendamento(newSelection);
                setCamposEditaveis(true);
                btnSalvarAlteracoes.setDisable(false);
            } else {
                setCamposEditaveis(false);
                btnSalvarAlteracoes.setDisable(true);
                limparCampos();
            }
        });
    }

    private void setCamposEditaveis(boolean editavel) {
        comboProfissionais.setDisable(!editavel);
        comboStatus.setDisable(!editavel);
        comboServicos.setDisable(!editavel);
        pickerData.setDisable(!editavel);
        txtHora.setEditable(editavel);
    }

    private void limparCampos() {
        txtClienteId.clear();
        comboProfissionais.getSelectionModel().clearSelection();
        comboStatus.getSelectionModel().clearSelection();
        comboServicos.getSelectionModel().clearSelection();
        pickerData.setValue(null);
        txtHora.clear();
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
        comboStatus.getItems().clear();
        comboStatus.getItems().addAll("PENDENTE", "EM ANDAMENTO", "CONCLUIDO", "CANCELADO");
    }

    private void carregarAgenda() {
        agendaList.clear();
        agendaList.addAll(agendaDAO.listarTodos());
    }

    private void carregarDetalhesAgendamento(AgendamentoDTO agendamento) {
        txtClienteId.setText(agendamento.getClienteNome());

        // Selecionar profissional
        for (Usuario u : profissionaisList) {
            if (u.getNome().equals(agendamento.getProfissionalNome())) {
                comboProfissionais.getSelectionModel().select(u);
                break;
            }
        }

        // Selecionar serviço
        for (Servico s : servicosList) {
            if (s.getNome().equals(agendamento.getServicoNome())) {
                comboServicos.getSelectionModel().select(s);
                break;
            }
        }

        comboStatus.getSelectionModel().select(agendamento.getStatusConclusao());
        pickerData.setValue(LocalDate.now()); // A agenda não tem data, apenas hora? Vamos manter a data atual ou buscar do banco? Por simplicidade, deixamos a data atual.
        txtHora.setText(agendamento.getHoraAgendado().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @FXML
    public void salvarAgendamento() {
        AgendamentoDTO selecionado = tabelaAgenda.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Selecione um agendamento na tabela.");
            return;
        }

        // Validar campos
        if (comboStatus.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Selecione um status.");
            return;
        }

        // Obter os novos valores
        String novoStatus = comboStatus.getSelectionModel().getSelectedItem();
        Usuario novoProfissional = comboProfissionais.getSelectionModel().getSelectedItem();
        Servico novoServico = comboServicos.getSelectionModel().getSelectedItem();
        LocalDate novaData = pickerData.getValue();
        String horaStr = txtHora.getText();

        if (novoProfissional == null || novoServico == null || novaData == null || horaStr == null || horaStr.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Preencha todos os campos.");
            return;
        }

        LocalTime novaHora;
        try {
            novaHora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Hora inválida. Use HH:mm:ss");
            return;
        }

        // Atualizar no banco
        Agenda agenda = agendaDAO.buscarPorId(selecionado.getId());
        if (agenda != null) {
            agenda.setProfissionalId(novoProfissional.getId());
            agenda.setServicoId(novoServico.getId());
            agenda.setHoraAgendado(novaHora);
            agenda.setStatusConclusao(novoStatus);
            // Nota: a tabela agenda não tem data, apenas hora. Se quiser suporte a data, precisaria alterar o banco.
            // Por enquanto, ignoramos a data.
            agendaDAO.atualizar(agenda); // Precisamos criar este método no AgendaDAO
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Agendamento atualizado!");
            carregarAgenda();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Agendamento não encontrado.");
        }
    }

    @FXML
    public void limparSelecao() {
        tabelaAgenda.getSelectionModel().clearSelection();
        limparCampos();
        setCamposEditaveis(false);
        btnSalvarAlteracoes.setDisable(true);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.AgendaDAO;
import com.example.lj1_bd_2sem.dao.ClienteDAO;
import com.example.lj1_bd_2sem.dao.ServicoDAO;
import com.example.lj1_bd_2sem.dao.UsuarioDAO;
import com.example.lj1_bd_2sem.model.Agenda;
import com.example.lj1_bd_2sem.model.Cliente;
import com.example.lj1_bd_2sem.dto.AgendamentoDTO;
import com.example.lj1_bd_2sem.model.Servico;
import com.example.lj1_bd_2sem.model.Usuario;
import com.example.lj1_bd_2sem.util.ScreenManager;
import com.example.lj1_bd_2sem.util.SessionManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AgendamentoClienteController {
    @FXML private ComboBox<Servico> comboServicos;
    @FXML private ComboBox<Usuario> comboProfissionais;
    @FXML private DatePicker dpData;
    @FXML private TextField txtHoraAgendado;
    @FXML private Button btnSolicitarAgendamento, btnCancelarSelecao;
    @FXML private TableView<AgendamentoDTO> tabelaMeusAgendamentos;
    @FXML private TableColumn<AgendamentoDTO, String> colHora, colServico, colProfissional, colStatus;
    @FXML private Label lblSaldo;

    private ServicoDAO servicoDAO = new ServicoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private AgendaDAO agendaDAO = new AgendaDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private ObservableList<Servico> servicosList = FXCollections.observableArrayList();
    private ObservableList<Usuario> profissionaisList = FXCollections.observableArrayList();
    private ObservableList<AgendamentoDTO> meusAgendamentosList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colHora.setCellValueFactory(new PropertyValueFactory<>("horaAgendado"));
        colServico.setCellValueFactory(new PropertyValueFactory<>("servicoNome"));
        colProfissional.setCellValueFactory(new PropertyValueFactory<>("profissionalNome"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusConclusao"));

        carregarServicos();
        carregarProfissionais();
        carregarMeusAgendamentos();
        atualizarSaldo();

        comboServicos.setItems(servicosList);
        comboProfissionais.setItems(profissionaisList);
        tabelaMeusAgendamentos.setItems(meusAgendamentosList);
    }

    private void atualizarSaldo() {
        Usuario user = SessionManager.getUsuarioLogado();
        if (user != null) {
            Cliente c = clienteDAO.buscarPorId(user.getId());
            if (c != null) lblSaldo.setText("Saldo: R$ " + String.format("%.2f", c.getBalanca()));
        }
    }

    private void abrirTelaExecucao(int agendaId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/executando_servico.fxml"));
            Parent root = loader.load();
            ExecutandoServicoController controller = loader.getController();
            Agenda agenda = agendaDAO.buscarPorId(agendaId);
            if (agenda != null) controller.setAgendamento(agendaId);
            Stage stage = (Stage) tabelaMeusAgendamentos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Executando Serviço");
        } catch (IOException ex) { ex.printStackTrace(); }
    }

    private void carregarServicos() {
        servicosList.clear();
        servicosList.addAll(servicoDAO.listarTodos());
    }

    private void carregarProfissionais() {
        profissionaisList.clear();
        profissionaisList.addAll(usuarioDAO.listarProfissionaisPorTipo("SALAO"));
    }

    private void carregarMeusAgendamentos() {
        meusAgendamentosList.clear();
        Usuario cliente = SessionManager.getUsuarioLogado();
        if (cliente != null && "CLIENTE".equals(cliente.getPerfil())) {
            List<AgendamentoDTO> lista = agendaDAO.listarPorCliente(cliente.getId());
            meusAgendamentosList.addAll(lista);
            System.out.println("Carregados " + lista.size() + " agendamentos do cliente.");
        }
    }

    @FXML
    public void solicitarAgendamento() {
        Servico servicoSelecionado = comboServicos.getSelectionModel().getSelectedItem();
        Usuario profissionalSelecionado = comboProfissionais.getSelectionModel().getSelectedItem();
        LocalDate dataSelecionada = dpData.getValue();
        String horaStr = txtHoraAgendado.getText();
        if (servicoSelecionado == null || profissionalSelecionado == null || dataSelecionada == null || horaStr == null || horaStr.trim().isEmpty()) {
            mostrarAlerta("Preencha todos os campos.");
            return;
        }
        LocalTime hora;
        try {
            hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (Exception e) {
            mostrarAlerta("Hora inválida. Use HH:mm:ss");
            return;
        }
        Usuario cliente = SessionManager.getUsuarioLogado();
        if (cliente == null || !"CLIENTE".equals(cliente.getPerfil())) {
            mostrarAlerta("Nenhum cliente logado.");
            return;
        }
        Agenda agenda = new Agenda();
        agenda.setClienteId(cliente.getId());
        agenda.setProfissionalId(profissionalSelecionado.getId());
        agenda.setServicoId(servicoSelecionado.getId());
        agenda.setHoraAgendado(hora);
        agenda.setStatusConclusao("PENDENTE");
        agendaDAO.inserir(agenda);
        mostrarAlerta("Agendamento solicitado com sucesso!");
        limparSelecao();
        carregarMeusAgendamentos();
    }

    @FXML public void limparSelecao() {
        comboServicos.getSelectionModel().clearSelection();
        comboProfissionais.getSelectionModel().clearSelection();
        dpData.setValue(null);
        txtHoraAgendado.clear();
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
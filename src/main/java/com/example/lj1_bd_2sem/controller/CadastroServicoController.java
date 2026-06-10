package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.ServicoDAO;
import com.example.lj1_bd_2sem.model.Servico;
import com.example.lj1_bd_2sem.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CadastroServicoController {


    @FXML private Button btnVoltarMenu;

    @FXML
    private TextField txtNomeServico;
    @FXML
    private TextField txtValorServico;
    @FXML
    private TextField txtDuracaoServico;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnSalvarServico;
    @FXML
    private TableView<Servico> tabelaServicos;
    @FXML
    private TableColumn<Servico, Integer> colIdServico;
    @FXML
    private TableColumn<Servico, String> colNomeServico;
    @FXML
    private TableColumn<Servico, Double> colValorServico;
    @FXML
    private TableColumn<Servico, Integer> colDuracaoServico;

    private ServicoDAO servicoDAO = new ServicoDAO();
    private ObservableList<Servico> servicosList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colIdServico.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeServico.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colValorServico.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colDuracaoServico.setCellValueFactory(new PropertyValueFactory<>("duracao"));

        carregarServicos();
        tabelaServicos.setItems(servicosList);
    }

    private void carregarServicos() {
        servicosList.clear();
        servicosList.addAll(servicoDAO.listarTodos());
    }

    @FXML
    public void voltarMenu() {
        Stage stage = (Stage) btnVoltarMenu.getScene().getWindow();
        ScreenManager.trocarTela("/menu_profissional.fxml", stage, "Menu Profissional");
    }

    @FXML
    public void limparCampos() {
        txtNomeServico.clear();
        txtValorServico.clear();
        txtDuracaoServico.clear();
    }

    @FXML
    public void salvarServico() {
        String nome = txtNomeServico.getText().trim();
        String valorStr = txtValorServico.getText().trim();
        String duracaoStr = txtDuracaoServico.getText().trim();

        if (nome.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Informe o nome do serviço.");
            return;
        }
        if (valorStr.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Informe o valor do serviço.");
            return;
        }
        if (duracaoStr.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Informe a duração em segundos.");
            return;
        }

        double valor;
        int duracao;
        try {
            valor = Double.parseDouble(valorStr);
            duracao = Integer.parseInt(duracaoStr);
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Valor ou duração inválidos.");
            return;
        }

        Servico servico = new Servico();
        servico.setNome(nome);
        servico.setValor(valor);
        servico.setDuracao(duracao);

        servicoDAO.inserir(servico);
        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Serviço cadastrado com sucesso!");
        limparCampos();
        carregarServicos();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
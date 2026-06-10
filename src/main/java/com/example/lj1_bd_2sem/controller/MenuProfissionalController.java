package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.model.Usuario;
import com.example.lj1_bd_2sem.util.ScreenManager;
import com.example.lj1_bd_2sem.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuProfissionalController {
    @FXML private Label lblTipo;
    @FXML private Button btnCadastrarServico;
    @FXML private Button btnAgendaSalao;
    @FXML private Button btnPagamentoAtendimento;
    @FXML private Button btnCadastrarProduto;
    @FXML private Button btnVendas;
    @FXML private Button btnSair;

    @FXML
    public void initialize() {
        Usuario user = SessionManager.getUsuarioLogado();
        String tipo = user.getTipoProfissional();
        lblTipo.setText("Profissional - " + tipo);
        if ("SALAO".equals(tipo)) {
            btnCadastrarServico.setVisible(true);
            btnAgendaSalao.setVisible(true);
            btnPagamentoAtendimento.setVisible(true);
            btnCadastrarProduto.setVisible(false);
            btnVendas.setVisible(false);
        } else if ("MERCADO".equals(tipo) || "FARMACIA".equals(tipo)) {
            btnCadastrarServico.setVisible(false);
            btnAgendaSalao.setVisible(false);
            btnPagamentoAtendimento.setVisible(false);
            btnCadastrarProduto.setVisible(true);
            btnVendas.setVisible(true);
        }
    }

    @FXML public void cadastrarServico() { trocarTela("/cadastro_servico.fxml", "Cadastro de Serviço"); }
    @FXML public void agendaSalao() { trocarTela("/agenda_salao.fxml", "Agenda do Salão"); }
    @FXML public void pagamentoAtendimento() { trocarTela("/pagamento_atendimento.fxml", "Pagamento de Atendimento"); }
    @FXML public void cadastrarProduto() { trocarTela("/preencher_estoque.fxml", "Cadastro de Produto"); }
    @FXML public void vendas() {
        Usuario user = SessionManager.getUsuarioLogado();
        if ("MERCADO".equals(user.getTipoProfissional())) trocarTela("/vendas_produtos_mercado.fxml", "Vendas - Mercado");
        else trocarTela("/vendas_produtos_farmacia.fxml", "Vendas - Farmácia");
    }
    @FXML public void sair() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        ScreenManager.trocarTela("/login.fxml", stage, "Login");
    }
    private void trocarTela(String fxml, String titulo) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        ScreenManager.trocarTela(fxml, stage, titulo);
    }
}
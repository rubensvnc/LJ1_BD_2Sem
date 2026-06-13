package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.ProdutoDAO;
import com.example.lj1_bd_2sem.dao.RemedioDAO;
import com.example.lj1_bd_2sem.model.Produto;
import com.example.lj1_bd_2sem.model.Remedio;
import com.example.lj1_bd_2sem.model.Usuario;
import com.example.lj1_bd_2sem.util.ScreenManager;
import com.example.lj1_bd_2sem.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PreencherEstoqueController {
    @FXML private ComboBox<String> comboTipoNegocio;
    @FXML private TextField txtNome, txtCodigoBarras, txtPreco, txtEstoque;
    @FXML private VBox boxFarmacia;
    @FXML private DatePicker pickerValidade;
    @FXML private CheckBox chkPrecedeReceita;
    @FXML private Button btnCancelar, btnSalvar;
    @FXML private Button btnVoltarMenu;

    ProdutoDAO produtoDAO = new ProdutoDAO();
    RemedioDAO remedioDAO = new RemedioDAO();

    @FXML
    public void initialize() {
        boxFarmacia.setVisible(false);
        boxFarmacia.setManaged(false);

        Usuario user = SessionManager.getUsuarioLogado();
        if (user != null && user.getPerfil().equals("PROFISSIONAL") && user.getTipoProfissional() != null) {
            String tipo = user.getTipoProfissional();
            comboTipoNegocio.setItems(FXCollections.observableArrayList(tipo));
            comboTipoNegocio.setValue(tipo);
            comboTipoNegocio.setDisable(true);
            handleTipoNegocioChange();
        } else {
            comboTipoNegocio.setItems(FXCollections.observableArrayList("MERCADO", "FARMACIA"));
        }
    }

    @FXML
    public void voltarMenu() {
        Stage stage = (Stage) btnVoltarMenu.getScene().getWindow();
        ScreenManager.trocarTela("/menu_profissional.fxml", stage, "Menu Profissional");
    }

    @FXML
    public void handleTipoNegocioChange() {
        if (comboTipoNegocio.getValue() == null) return;
        boolean isFarmacia = comboTipoNegocio.getValue().equals("FARMACIA");
        boxFarmacia.setVisible(isFarmacia);
        boxFarmacia.setManaged(isFarmacia);
    }

    @FXML
    public void handleCancelar() {
        txtNome.clear();
        txtCodigoBarras.clear();
        txtPreco.clear();
        txtEstoque.clear();
        pickerValidade.setValue(null);
        chkPrecedeReceita.setSelected(false);
    }

    @FXML
    public void handleSalvar() {
        if (comboTipoNegocio.getValue() == null) return;
        String tipo = comboTipoNegocio.getValue();
        Produto p = new Produto();
        p.setCodigoBarras(txtCodigoBarras.getText());
        p.setNome(txtNome.getText());
        p.setPrecoBase(Double.parseDouble(txtPreco.getText()));
        p.setTipo(tipo);
        p.setQtd(Integer.parseInt(txtEstoque.getText()));
        p.setValidade(pickerValidade.getValue());
        try {
            int id = produtoDAO.salvarProduto(p);
            if (tipo.equals("FARMACIA")) {
                Remedio r = new Remedio();
                r.setIdProduto(id);
                r.setUsaReceita(chkPrecedeReceita.isSelected());
                remedioDAO.inserirRemedio(r);
            }
            mostrarAlerta("Produto salvo com sucesso!");
            handleCancelar();
        } catch (SQLException e) { e.printStackTrace(); mostrarAlerta("Erro ao salvar."); }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
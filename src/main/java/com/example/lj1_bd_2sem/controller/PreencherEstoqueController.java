package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.ProdutoDAO;
import com.example.lj1_bd_2sem.dao.RemedioDAO;
import com.example.lj1_bd_2sem.model.Produto;
import com.example.lj1_bd_2sem.model.Remedio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PreencherEstoqueController {

    @FXML
    private ComboBox<String> comboTipoNegocio;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCodigoBarras;

    @FXML
    private TextField txtPreco;

    @FXML
    private TextField txtEstoque;

    @FXML
    private VBox boxFarmacia;

    @FXML
    private DatePicker pickerValidade;

    @FXML
    private CheckBox chkPrecedeReceita;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    ProdutoDAO produtoDAO = new ProdutoDAO();
    RemedioDAO remedioDAO = new RemedioDAO();

    @FXML
    public void initialize() {
        boxFarmacia.setVisible(false);
        boxFarmacia.setManaged(false);

        List<String> estabelecimentos = new ArrayList<>();
        estabelecimentos.add("MERCADO");
        estabelecimentos.add("FARMACIA");
        ObservableList<String> opcoesEstabelecimento = FXCollections.observableArrayList(estabelecimentos);
        comboTipoNegocio.setItems(opcoesEstabelecimento);
    }

    @FXML
    public void handleTipoNegocioChange() {
        if (comboTipoNegocio.getValue() == null) return;

        if (comboTipoNegocio.getValue().equals("FARMACIA")){
            boxFarmacia.setVisible(true);
            boxFarmacia.setManaged(true);
        } else {
            boxFarmacia.setVisible(false);
            boxFarmacia.setManaged(false);
        }
    }

    @FXML
    public void handleCancelar() {
        txtNome.setText("");
        txtCodigoBarras.setText("");
        txtPreco.setText("");
        txtEstoque.setText("");
        pickerValidade.setValue(null);
        chkPrecedeReceita.setSelected(false);
    }

    @FXML
    public void handleSalvar() {
        String tipo;
        if (comboTipoNegocio.getValue() == null) return;

        if (comboTipoNegocio.getValue().equals("FARMACIA")) tipo = "FARMACIA";
        else tipo = "MERCADO";

        Produto p = new Produto();
        p.setCodigoBarras(txtCodigoBarras.getText());
        p.setNome(txtNome.getText());
        p.setPrecoBase(Double.parseDouble(txtPreco.getText()));
        p.setTipo(tipo);
        p.setQtd(Integer.parseInt(txtEstoque.getText()));
        p.setValidade(pickerValidade.getValue());

        try{
            int id = produtoDAO.salvarProduto(p);

            if (tipo.equals("FARMACIA")){
                Remedio r = new Remedio();
                r.setIdProduto(id);
                r.setUsaReceita(chkPrecedeReceita.isSelected());
                remedioDAO.inserirRemedio(r);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
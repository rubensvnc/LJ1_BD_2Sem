package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.*;
import com.example.lj1_bd_2sem.dto.TabelaProdutoDTO;
import com.example.lj1_bd_2sem.model.*;
import com.example.lj1_bd_2sem.util.ScreenManager;
import com.example.lj1_bd_2sem.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VendasProdutosFarmaciaController {
    @FXML private Button gotoHomeBtn;
    @FXML private TableView<TabelaProdutoDTO> tabelaEstoque;
    @FXML private TableColumn<TabelaProdutoDTO, String> colCodigoBarras, colProduto;
    @FXML private TableColumn<TabelaProdutoDTO, Integer> colQtd;
    @FXML private TableColumn<TabelaProdutoDTO, Double> colPrecoUnitario;
    @FXML private TextField txtCodigoProduto, txtQuantidade;
    @FXML private Button btnAdicionar, btnRemover;
    @FXML private TableView<TabelaProdutoDTO> tabelaCarrinho;
    @FXML private TableColumn<TabelaProdutoDTO, String> colCodigoBarrasCarrinho, colProdutoCarrinho;
    @FXML private TableColumn<TabelaProdutoDTO, Integer> colQtdCarrinho;
    @FXML private TableColumn<TabelaProdutoDTO, Double> colPrecoUnitarioCarrinho, colTotalItemCarrinho;
    @FXML private ComboBox<String> comboPagamento;
    @FXML private Label lblTotalVenda, lblSaldo;
    @FXML private Button btnFinalizarVenda;

    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final RemedioDAO remedioDAO = new RemedioDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private ObservableList<TabelaProdutoDTO> carrinhoItens = FXCollections.observableArrayList();
    private TabelaProdutoDTO itemSelecionadoCarrinho = null;

    @FXML
    public void initialize() {
        colCodigoBarras.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
        colProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPrecoUnitario.setCellValueFactory(new PropertyValueFactory<>("preco"));

        colCodigoBarrasCarrinho.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
        colProdutoCarrinho.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colQtdCarrinho.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPrecoUnitarioCarrinho.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colTotalItemCarrinho.setCellValueFactory(cell -> {
            TabelaProdutoDTO item = cell.getValue();
            return new javafx.beans.property.SimpleObjectProperty<>(item.getQuantidade() * item.getPreco());
        });

        comboPagamento.getItems().addAll("Dinheiro", "Cartão de Crédito", "Cartão de Débito", "PIX");
        comboPagamento.setValue("Dinheiro");

        carregarTabelaEstoque();
        tabelaEstoque.setOnMouseClicked(e -> recuperarItemSelecionado());
        tabelaCarrinho.setItems(carrinhoItens);
        tabelaCarrinho.setOnMouseClicked(e -> recuperarItemSelecionadoCarrinho());
        btnRemover.setVisible(false);
        btnRemover.setManaged(false);
        atualizarSaldo();
    }

    private void atualizarSaldo() {
        Usuario user = SessionManager.getUsuarioLogado();
        if (user != null && user.getPerfil().equals("CLIENTE")) {
            Cliente c = clienteDAO.buscarPorId(user.getId());
            if (c != null) lblSaldo.setText("Saldo: R$ " + String.format("%.2f", c.getBalanca()));
        }
    }

    private void carregarTabelaEstoque() {
        List<Produto> produtos = produtoDAO.listarProdutosFarmacia();
        List<TabelaProdutoDTO> dtoList = new ArrayList<>();
        for (Produto p : produtos) {
            dtoList.add(new TabelaProdutoDTO(p.getCodigoBarras(), p.getNome(), p.getQtd(), p.getPrecoBase()));
        }
        tabelaEstoque.setItems(FXCollections.observableArrayList(dtoList));
    }

    private void recuperarItemSelecionado() {
        btnRemover.setVisible(false);
        TabelaProdutoDTO item = tabelaEstoque.getSelectionModel().getSelectedItem();
        if (item != null) {
            txtCodigoProduto.setText(item.getCodigoBarras());
            txtQuantidade.requestFocus();
        }
    }

    private void recuperarItemSelecionadoCarrinho() {
        itemSelecionadoCarrinho = tabelaCarrinho.getSelectionModel().getSelectedItem();
        btnRemover.setVisible(itemSelecionadoCarrinho != null);
        btnRemover.setManaged(itemSelecionadoCarrinho != null);
    }

    @FXML
    public void adicionarItem() {
        try {
            String cod = txtCodigoProduto.getText();
            int qtd = Integer.parseInt(txtQuantidade.getText());
            if (cod.isEmpty() || qtd <= 0) return;
            TabelaProdutoDTO produto = buscarProdutoPorCodigoBarras(cod);
            if (produto == null || produto.getQuantidade() < qtd) return;

            Remedio remedio = remedioDAO.buscarRemedioPorCodigoBarras(cod);
            if (remedio != null && remedio.getUsaReceita()) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Receita Médica");
                dialog.setHeaderText("Este medicamento exige retenção de receita.");
                dialog.setContentText("Digite o CRM do médico:");
                Optional<String> result = dialog.showAndWait();
                if (!result.isPresent() || result.get().trim().isEmpty()) return;
            }

            TabelaProdutoDTO itemCarrinho = buscarItemNoCarrinho(cod);
            if (itemCarrinho != null) itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + qtd);
            else carrinhoItens.add(new TabelaProdutoDTO(cod, produto.getProduto(), qtd, produto.getPreco()));
            produto.setQuantidade(produto.getQuantidade() - qtd);
            tabelaEstoque.refresh();
            atualizarTotalVenda();
            limparCamposEntrada();
        } catch (NumberFormatException e) {}
    }

    @FXML
    public void removerItem() {
        if (itemSelecionadoCarrinho == null) return;
        String cod = itemSelecionadoCarrinho.getCodigoBarras();
        int qtdRemover = itemSelecionadoCarrinho.getQuantidade();
        TabelaProdutoDTO produto = buscarProdutoPorCodigoBarras(cod);
        if (produto != null) produto.setQuantidade(produto.getQuantidade() + qtdRemover);
        carrinhoItens.remove(itemSelecionadoCarrinho);
        itemSelecionadoCarrinho = null;
        btnRemover.setVisible(false);
        tabelaEstoque.refresh();
        atualizarTotalVenda();
    }

    private void atualizarTotalVenda() {
        double total = 0;
        for (TabelaProdutoDTO item : carrinhoItens) total += item.getQuantidade() * item.getPreco();
        lblTotalVenda.setText(String.format("R$ %.2f", total));
    }

    private void limparCamposEntrada() {
        txtCodigoProduto.clear();
        txtQuantidade.setText("1");
    }

    private TabelaProdutoDTO buscarProdutoPorCodigoBarras(String cod) {
        for (TabelaProdutoDTO item : tabelaEstoque.getItems()) if (item.getCodigoBarras().equals(cod)) return item;
        return null;
    }

    private TabelaProdutoDTO buscarItemNoCarrinho(String cod) {
        for (TabelaProdutoDTO item : carrinhoItens) if (item.getCodigoBarras().equals(cod)) return item;
        return null;
    }

    @FXML
    public void finalizarVenda() {
        if (carrinhoItens.isEmpty()) return;
        double total = Double.parseDouble(lblTotalVenda.getText().replace("R$ ", "").replace(",", "."));
        Usuario user = SessionManager.getUsuarioLogado();
        if (user == null || !user.getPerfil().equals("CLIENTE")) {
            mostrarAlerta("Apenas clientes logados podem comprar.");
            return;
        }
        Cliente cliente = clienteDAO.buscarPorId(user.getId());
        if (cliente.getBalanca() < total) {
            mostrarAlerta("Saldo insuficiente! Seu saldo: R$ " + String.format("%.2f", cliente.getBalanca()));
            return;
        }
        List<String> codigos = new ArrayList<>();
        List<Venda> vendas = new ArrayList<>();
        for (TabelaProdutoDTO item : carrinhoItens) codigos.add(item.getCodigoBarras());
        List<Integer> ids = produtoDAO.recuperarIds(codigos);
        for (int i = 0; i < carrinhoItens.size(); i++) {
            TabelaProdutoDTO item = carrinhoItens.get(i);
            Venda v = new Venda();
            v.setClienteId(cliente.getId());
            v.setProdutoId(ids.get(i));
            v.setQtd(item.getQuantidade());
            v.setPrecoVenda(item.getQuantidade() * item.getPreco());
            vendas.add(v);
        }
        vendaDAO.salvarVenda(vendas);
        clienteDAO.atualizarSaldo(cliente.getId(), cliente.getBalanca() - total);
        carrinhoItens.clear();
        atualizarTotalVenda();
        atualizarSaldo();
        carregarTabelaEstoque();
        mostrarAlerta("Venda finalizada! Novo saldo: R$ " + String.format("%.2f", cliente.getBalanca() - total));
    }

    @FXML
    public void goToHome() {
        Stage stage = (Stage) gotoHomeBtn.getScene().getWindow();
        Usuario user = SessionManager.getUsuarioLogado();
        if (user != null && user.getPerfil().equals("PROFISSIONAL")) {
            ScreenManager.trocarTela("/menu_profissional.fxml", stage, "Menu Profissional");
        } else {
            ScreenManager.trocarTela("/home.fxml", stage, "Home");
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.dao.ProdutoDAO;
import com.example.lj1_bd_2sem.dao.RemedioDAO;
import com.example.lj1_bd_2sem.dao.VendaDAO;
import com.example.lj1_bd_2sem.dto.TabelaProdutoDTO;
import com.example.lj1_bd_2sem.model.Produto;
import com.example.lj1_bd_2sem.model.Remedio;
import com.example.lj1_bd_2sem.model.Venda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendasProdutosController {

    @FXML
    private ComboBox<String> comboEstabelecimento;

    @FXML
    private TableView<TabelaProdutoDTO> tabelaEstoque;

    @FXML
    private TableColumn<TabelaProdutoDTO, String> colCodigoBarras;

    @FXML
    private TableColumn<TabelaProdutoDTO, String> colProduto;

    @FXML
    private TableColumn<TabelaProdutoDTO, Integer> colQtd;

    @FXML
    private TableColumn<TabelaProdutoDTO, Double> colPrecoUnitario;

    @FXML
    private TextField txtCodigoProduto;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnRemover;

    @FXML
    private TableView<TabelaProdutoDTO> tabelaCarrinho;

    @FXML
    private TableColumn<TabelaProdutoDTO, String> colCodigoBarrasCarrinho;

    @FXML
    private TableColumn<TabelaProdutoDTO, String> colProdutoCarrinho;

    @FXML
    private TableColumn<TabelaProdutoDTO, Integer> colQtdCarrinho;

    @FXML
    private TableColumn<TabelaProdutoDTO, Double> colPrecoUnitarioCarrinho;

    @FXML
    private TableColumn<TabelaProdutoDTO, Double> colTotalItemCarrinho;

    @FXML
    private ComboBox<String> comboPagamento;

    @FXML
    private Label lblTotalVenda;

    @FXML
    private Button btnFinalizarVenda;

    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final RemedioDAO remedioDAO = new RemedioDAO();

    private ObservableList<TabelaProdutoDTO> carrinhoItens = FXCollections.observableArrayList();

    private TabelaProdutoDTO itemSelecionadoCarrinho = null;

    @FXML
    public void initialize() {
        List<String> estabelecimentos = new ArrayList<>();
        estabelecimentos.add("MERCADO");
        estabelecimentos.add("FARMACIA");
        ObservableList<String> opcoesEstabelecimento = FXCollections.observableArrayList(estabelecimentos);
        comboEstabelecimento.setItems(opcoesEstabelecimento);
        comboEstabelecimento.setValue("MERCADO");

        // Configurar as colunas da tabelaEstoque
        colCodigoBarras.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
        colProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPrecoUnitario.setCellValueFactory(new PropertyValueFactory<>("preco"));

        // Configurar as colunas da tabelaCarrinho
        colCodigoBarrasCarrinho.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
        colProdutoCarrinho.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colQtdCarrinho.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPrecoUnitarioCarrinho.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colTotalItemCarrinho.setCellValueFactory(cellData -> {
            TabelaProdutoDTO item = cellData.getValue();
            double total = item.getQuantidade() * item.getPreco();
            return new javafx.beans.property.SimpleObjectProperty<>(total);
        });

        comboPagamento.getItems().addAll("Dinheiro", "Cartão de Crédito", "Cartão de Débito", "PIX");
        comboPagamento.setValue("Dinheiro");

        carregarTabelaEstoque();

        tabelaEstoque.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                recuperarItemSelecionado();
            }
        });

        tabelaCarrinho.setItems(carrinhoItens);

        tabelaCarrinho.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                recuperarItemSelecionadoCarrinho();
            }
        });

        // Inicialmente o botão remover fica invisível
        btnRemover.setVisible(false);
        btnRemover.setManaged(false);
    }

    private void carregarTabelaEstoque() {
        List<Produto> produtoList;
        if (comboEstabelecimento.getValue().equals("FARMACIA")){
            produtoList = produtoDAO.listarProdutosFarmacia();
        } else {
            produtoList = produtoDAO.listarProdutosMercado();
        }

        List<TabelaProdutoDTO> produtoDTOList = new ArrayList<>();

        for (Produto p : produtoList) {
            TabelaProdutoDTO linhaDTO = new TabelaProdutoDTO(
                    p.getCodigoBarras(),
                    p.getNome(),
                    p.getQtd(),
                    p.getPrecoBase()
            );
            produtoDTOList.add(linhaDTO);
        }

        ObservableList<TabelaProdutoDTO> linhasProdutos = FXCollections.observableArrayList(produtoDTOList);
        tabelaEstoque.setItems(linhasProdutos);
    }

    private void recuperarItemSelecionado() {
        btnRemover.setVisible(false);
        btnRemover.setManaged(false);

        TabelaProdutoDTO itemSelecionado = tabelaEstoque.getSelectionModel().getSelectedItem();

        if (itemSelecionado != null) {
            txtCodigoProduto.setText(itemSelecionado.getCodigoBarras());
            txtQuantidade.requestFocus();
        }
    }

    private void recuperarItemSelecionadoCarrinho() {
        itemSelecionadoCarrinho = tabelaCarrinho.getSelectionModel().getSelectedItem();

        if (itemSelecionadoCarrinho != null) {
            btnRemover.setVisible(true);
            btnRemover.setManaged(true);
        }
    }

    @FXML
    public void adicionarItem() {
        try {
            String codigoBarras = txtCodigoProduto.getText();
            int quantidade = Integer.parseInt(txtQuantidade.getText());

            if (codigoBarras.isEmpty() || quantidade <= 0) {
                System.out.println("Código de barras inválido ou quantidade inválida");
                return;
            }

            // Buscar o produto na tabela de estoque
            TabelaProdutoDTO produtoEncontrado = buscarProdutoPorCodigoBarras(codigoBarras);
            if (produtoEncontrado == null) {
                System.out.println("Produto não encontrado!");
                return;
            }

            if ("FARMACIA".equals(comboEstabelecimento.getValue())) {
                Remedio remedio = remedioDAO.buscarRemedioPorCodigoBarras(codigoBarras);

                if (remedio != null && Boolean.TRUE.equals(remedio.getUsaReceita())) {
                    // Abre o popup do JavaFX exigindo o CRM
                    javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
                    dialog.setTitle("Controle de Medicamento");
                    dialog.setHeaderText("Este remédio exige retenção de receita.");
                    dialog.setContentText("Por favor, digite o CRM do médico:");

                    java.util.Optional<String> result = dialog.showAndWait();

                    // Se o usuário cancelou ou deixou o campo vazio, cancela a inserção
                    if (result.isPresent() && !result.get().trim().isEmpty()) {
                        String crmDigitado = result.get().trim();
                        System.out.println("CRM validado: " + crmDigitado);
                        // Opcional: Se você precisar salvar esse CRM em algum lugar temporário
                        // para a venda, guarde-o aqui.
                    } else {
                        System.out.println("Adição cancelada: CRM não informado.");
                        return; // Barra a execução do método aqui
                    }
                }
            }

            // Verificar se tem estoque suficiente
            if (produtoEncontrado.getQuantidade() < quantidade) {
                System.out.println("Estoque insuficiente! Disponível: " + produtoEncontrado.getQuantidade());
                return;
            }

            // Verificar se o produto já está no carrinho
            TabelaProdutoDTO itemCarrinho = buscarItemNoCarrinho(codigoBarras);

            if (itemCarrinho != null) {
                // Atualizar quantidade no carrinho
                int novaQuantidade = itemCarrinho.getQuantidade() + quantidade;
                itemCarrinho.setQuantidade(novaQuantidade);
                tabelaCarrinho.refresh();
            } else {
                // Adicionar novo item ao carrinho
                TabelaProdutoDTO novoItem = new TabelaProdutoDTO(
                        produtoEncontrado.getCodigoBarras(),
                        produtoEncontrado.getProduto(),
                        quantidade,
                        produtoEncontrado.getPreco()
                );
                carrinhoItens.add(novoItem);
            }

            // Atualizar o estoque visualmente
            produtoEncontrado.setQuantidade(produtoEncontrado.getQuantidade() - quantidade);
            tabelaEstoque.refresh();

            // Atualizar total da venda
            atualizarTotalVenda();

            // Limpar campos
            limparCamposEntrada();

        } catch (NumberFormatException e) {
            System.out.println("Quantidade inválida!");
        }
    }

    @FXML
    public void handleTrocaEstabelecimento(){
        carregarTabelaEstoque();
    }

    @FXML
    public void removerItem() {
        if (itemSelecionadoCarrinho == null) {
            System.out.println("Nenhum item selecionado para remover!");
            return;
        }

        String codigoBarras = itemSelecionadoCarrinho.getCodigoBarras();
        int quantidadeRemover = itemSelecionadoCarrinho.getQuantidade();

        // Buscar o produto no estoque para devolver a quantidade
        TabelaProdutoDTO produtoEstoque = buscarProdutoPorCodigoBarras(codigoBarras);

        if (produtoEstoque != null) {
            // Devolver a quantidade ao estoque
            int quantidadeAtual = produtoEstoque.getQuantidade();
            produtoEstoque.setQuantidade(quantidadeAtual + quantidadeRemover);
            tabelaEstoque.refresh();
        }

        // Remover o item do carrinho
        carrinhoItens.remove(itemSelecionadoCarrinho);

        // Limpar seleção e esconder botão remover
        itemSelecionadoCarrinho = null;
        btnRemover.setVisible(false);
        btnRemover.setManaged(false);

        // Atualizar total da venda
        atualizarTotalVenda();

        System.out.println("Item removido do carrinho!");
    }

    private void atualizarTotalVenda() {
        double total = 0;
        for (TabelaProdutoDTO item : carrinhoItens) {
            total += item.getQuantidade() * item.getPreco();
        }
        lblTotalVenda.setText(String.format("R$ %.2f", total));
    }

    private void limparCamposEntrada() {
        txtCodigoProduto.clear();
        txtQuantidade.setText("1");
        txtCodigoProduto.requestFocus();
    }

    private TabelaProdutoDTO buscarProdutoPorCodigoBarras(String codigoBarras) {
        for (TabelaProdutoDTO item : tabelaEstoque.getItems()) {
            if (item.getCodigoBarras().equals(codigoBarras)) {
                return item;
            }
        }
        return null;
    }

    private TabelaProdutoDTO buscarItemNoCarrinho(String codigoBarras) {
        for (TabelaProdutoDTO item : carrinhoItens) {
            if (item.getCodigoBarras().equals(codigoBarras)) {
                return item;
            }
        }
        return null;
    }

    private void atualizarEstoque(List<Produto> produtoList){
        try {
            produtoDAO.atualizarValoresProduto(produtoList);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void finalizarVenda() {
        if (carrinhoItens.isEmpty()) {
            System.out.println("Carrinho vazio!");
            return;
        }

        String formaPagamento = comboPagamento.getValue();
        if (formaPagamento == null) {
            System.out.println("Selecione uma forma de pagamento!");
            return;
        }

        List<String> codigoBarrasList = new ArrayList<>();
        List<Produto> produtosAfetados = new ArrayList<>();

        for (TabelaProdutoDTO tpdto : tabelaCarrinho.getItems()) {
            codigoBarrasList.add(tpdto.getCodigoBarras());

            // Busca o item correspondente na tabela de estoque (que já está com a quantidade reduzida)
            TabelaProdutoDTO produtoEstoqueAtualizado = buscarProdutoPorCodigoBarras(tpdto.getCodigoBarras());

            Produto p = new Produto();
            p.setCodigoBarras(tpdto.getCodigoBarras());
            p.setTipo(comboEstabelecimento.getValue());
            p.setNome(tpdto.getProduto());

            // Se encontrou no estoque, pega a quantidade que sobrou lá.
            // Caso contrário (segurança), envia o que está no DTO original do estoque.
            if (produtoEstoqueAtualizado != null) {
                p.setQtd(produtoEstoqueAtualizado.getQuantidade());
            } else {
                p.setQtd(tpdto.getQuantidade());
            }

            p.setPrecoBase(tpdto.getPreco());

            produtosAfetados.add(p);
        }

        List<Integer> produtoIdsList = produtoDAO.recuperarIds(codigoBarrasList);
        List<Venda> vendaList = new ArrayList<>();
        for (int i=0; i<= tabelaCarrinho.getItems().size()-1; i++){
            TabelaProdutoDTO tpdto = tabelaCarrinho.getItems().get(i);
            Venda v = new Venda();
            v.setProdutoId(produtoIdsList.get(i));
            v.setQtd(tpdto.getQuantidade());
            v.setPrecoVenda(tpdto.getPreco() * tpdto.getQuantidade());
            vendaList.add(v);
        }

        vendaDAO.salvarVenda(vendaList);

        // Limpar o carrinho após finalizar
        carrinhoItens.clear();
        lblTotalVenda.setText("R$ 0,00");
        itemSelecionadoCarrinho = null;
        btnRemover.setVisible(false);
        btnRemover.setManaged(false);

        // Recarregar a tabela de estoque para atualizar os valores
        atualizarEstoque(produtosAfetados);
        carregarTabelaEstoque();

        System.out.println("Venda finalizada com sucesso!");
    }
}
package com.example.lj1_bd_2sem.model;


public class Venda {
    private Integer id;
    private Integer produtoId;
    private Integer qtd;
    private Double precoVenda;
    private String estabelecimento; // MERCADO, FARMACIA

    // Construtor vazio
    public Venda() {
    }

    // Construtor completo
    public Venda(Integer id, Integer produtoId, Integer qtd, Double precoVenda, String estabelecimento) {
        this.id = id;
        this.produtoId = produtoId;
        this.qtd = qtd;
        this.precoVenda = precoVenda;
        this.estabelecimento = estabelecimento;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
}
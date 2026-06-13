package com.example.lj1_bd_2sem.model;

public class Venda {
    private Integer id;
    private Integer clienteId;
    private Integer produtoId;
    private Integer qtd;
    private Double precoVenda;

    public Venda() {}
    public Venda(Integer id, Integer clienteId, Integer produtoId, Integer qtd, Double precoVenda) {
        this.id = id;
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.qtd = qtd;
        this.precoVenda = precoVenda;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }
    public Integer getProdutoId() { return produtoId; }
    public void setProdutoId(Integer produtoId) { this.produtoId = produtoId; }
    public Integer getQtd() { return qtd; }
    public void setQtd(Integer qtd) { this.qtd = qtd; }
    public Double getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(Double precoVenda) { this.precoVenda = precoVenda; }
}
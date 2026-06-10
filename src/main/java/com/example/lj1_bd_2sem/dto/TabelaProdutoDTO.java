package com.example.lj1_bd_2sem.dto;

public class TabelaProdutoDTO {
    private String codigoBarras;
    private String produto;
    private Integer quantidade;
    private Double preco;

    public TabelaProdutoDTO(String codigoBarras, String produto, Integer quantidade, Double preco) {
        this.codigoBarras = codigoBarras;
        this.produto = produto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
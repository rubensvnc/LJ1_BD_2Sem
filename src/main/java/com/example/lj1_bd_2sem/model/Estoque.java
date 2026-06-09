package com.example.lj1_bd_2sem.model;


public class Estoque {
    private Integer id;
    private Integer produtoId;
    private Integer qtd;

    // Construtor vazio
    public Estoque() {
    }

    // Construtor completo
    public Estoque(Integer id, Integer produtoId, Integer qtd) {
        this.id = id;
        this.produtoId = produtoId;
        this.qtd = qtd;
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
}

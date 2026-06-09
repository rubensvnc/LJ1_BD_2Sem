package com.example.lj1_bd_2sem.model;


import java.time.LocalDateTime;

public class Produto {
    private Integer id;
    private String codigoBarras;
    private String nome;
    private Double precoBase;
    private LocalDateTime validade;

    // Construtor vazio
    public Produto() {
    }

    // Construtor completo
    public Produto(Integer id, String codigoBarras, String nome, Double precoBase, LocalDateTime validade) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.nome = nome;
        this.precoBase = precoBase;
        this.validade = validade;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(Double precoBase) {
        this.precoBase = precoBase;
    }

    public LocalDateTime getValidade() {
        return validade;
    }

    public void setValidade(LocalDateTime validade) {
        this.validade = validade;
    }
}

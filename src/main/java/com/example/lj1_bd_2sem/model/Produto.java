package com.example.lj1_bd_2sem.model;


import java.time.LocalDate;

public class Produto {
    private Integer id;
    private String codigoBarras;
    private String nome;
    private Double precoBase;
    private LocalDate validade;
    private String tipo;
    private Integer qtd;

    public Produto() {
    }

    public Produto(Integer id, String codigoBarras, String nome, Double precoBase,
                   LocalDate validade, String tipo, Integer qtd) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.nome = nome;
        this.precoBase = precoBase;
        this.validade = validade;
        this.tipo = tipo;
        this.qtd = qtd;
    }

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

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }
}

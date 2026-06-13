package com.example.lj1_bd_2sem.model;

public class Servico {
    private Integer id;
    private String nome;
    private Double valor;
    private Integer duracao;

    public Servico() {
    }

    public Servico(Integer id, String nome, Double valor, Integer duracao) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.duracao = duracao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    @Override
    public String toString() {
        return nome;
    }
}

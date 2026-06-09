package com.example.lj1_bd_2sem.model;

public class Cliente {
    private Integer id;
    private Double balanca;

    // Construtor vazio
    public Cliente() {
    }

    // Construtor completo
    public Cliente(Integer id, Double balanca) {
        this.id = id;
        this.balanca = balanca;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBalanca() {
        return balanca;
    }

    public void setBalanca(Double balanca) {
        this.balanca = balanca;
    }
}
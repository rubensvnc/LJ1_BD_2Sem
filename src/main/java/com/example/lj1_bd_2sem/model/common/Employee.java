package com.example.lj1_bd_2sem.model.common;

public abstract class Employee {
    private String cpf;
    private Double income;
    private String name;
    private Integer idade;

    public Employee(String cpf, Integer idade, String name) {
        this.cpf = cpf;
        this.idade = idade;
        this.name = name;
        this.income = 0.0;
    }

    public String getCpf() {
        return cpf;
    }

    public Double getIncome() {
        return income;
    }

    public String getName() {
        return name;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIncome(Double income){
        this.income = income;
    }

}

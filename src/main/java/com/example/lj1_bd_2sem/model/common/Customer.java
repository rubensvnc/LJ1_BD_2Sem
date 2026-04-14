package com.example.lj1_bd_2sem.model.common;

public class Customer {

    private int cpf;
    private int age;
    private String name;
    private Double balance;

    public Customer(int cpf, int age, String name, Double balance) {
        this.cpf = cpf;
        this.age = age;
        this.name = name;
        this.balance = balance;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance(){return this.balance; }
}

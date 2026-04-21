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

    public void purchase(Product product, Business business){
        if (balance >= product.getPrice()){
            boolean success = business.processSale(product);
            if(success){
                balance -= product.getPrice();
                System.out.println("Purchase successful!");
            }
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public void purchase(Double value){
        if (balance >= value){
            balance -= value;
            System.out.println("Purchase successful!");
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public int getCpf() {
        return cpf;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Double getBalance(){ return this.balance; }
}

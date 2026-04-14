package com.example.lj1_bd_2sem.model.common;

public abstract class Product {
    private String name;
    private Double price;
    private int quantity;

    public Product(String name, Double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void reduceQuantity(int quantity){
        this.quantity -= quantity;
    }

    public void addQuantity(int quantity){
        this.quantity += quantity;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public int getQuantity() { return quantity; }
}

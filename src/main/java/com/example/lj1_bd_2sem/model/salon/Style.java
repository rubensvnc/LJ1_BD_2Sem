package com.example.lj1_bd_2sem.model.salon;

public class Style {
    private String name;
    private Double price;

    public Style(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }
}

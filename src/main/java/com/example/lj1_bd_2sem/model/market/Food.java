package com.example.lj1_bd_2sem.model.market;

import com.example.lj1_bd_2sem.model.common.Product;

public class Food extends Product {
    private String expirationDate;

    public Food (String name, Double price, int quantity, String expirationDate){
        super(name, price, quantity);
        this.expirationDate = expirationDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}

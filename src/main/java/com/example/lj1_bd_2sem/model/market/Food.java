package com.example.lj1_bd_2sem.model.market;

import com.example.lj1_bd_2sem.model.common.Product;

public class Food extends Product {
    private String expirationDate;

    public Food (String name, Double price, String expirationDate){
        super(name, price);
        this.expirationDate = expirationDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}

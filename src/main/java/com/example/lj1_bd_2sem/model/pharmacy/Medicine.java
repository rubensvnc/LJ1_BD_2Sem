package com.example.lj1_bd_2sem.model.pharmacy;

import com.example.lj1_bd_2sem.model.common.Product;

public class Medicine extends Product {
    private String expirationDate;
    private String indicatedUse;
    private String precautions;

    public Medicine(String name, Double price, int quantity, String expirationDate, String indicatedUse,
                    String precautions){
        super(name, price, quantity);
        this.expirationDate = expirationDate;
        this.indicatedUse = indicatedUse;
        this.precautions = precautions;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getIndicatedUse() {
        return indicatedUse;
    }

    public String getPrecautions() {
        return precautions;
    }
}

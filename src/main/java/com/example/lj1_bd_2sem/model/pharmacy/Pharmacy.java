package com.example.lj1_bd_2sem.model.pharmacy;

import com.example.lj1_bd_2sem.model.common.Business;
import com.example.lj1_bd_2sem.model.common.Employee;
import com.example.lj1_bd_2sem.model.common.Product;

import java.util.ArrayList;
import java.util.List;

public class Pharmacy extends Business {
    private List<Medicine> medicines = new ArrayList<>();

    public Pharmacy (String cnpj){
        super(cnpj);
    }

    public Medicine searchStorage(String name){
        for (Medicine medicine : medicines){
            if (medicine.getName().equals(name)){
                return medicine;
            }
        }
        return null;
    }
}

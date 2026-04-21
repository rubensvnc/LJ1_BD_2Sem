package com.example.lj1_bd_2sem.model.pharmacy;

import com.example.lj1_bd_2sem.model.common.Business;
import com.example.lj1_bd_2sem.model.common.Employee;
import com.example.lj1_bd_2sem.model.common.Product;

public class Pharmacist extends Employee {
    public Pharmacist(String cpf, Integer idade, String name){
        super(cpf, idade, name);
    }

    public Medicine searchMedicine(String name, Pharmacy pharmacy){
        Medicine medicine = pharmacy.searchStorage(name);
        if (medicine != null){
            return medicine;
        } else {
            return null;
        }
    }
}

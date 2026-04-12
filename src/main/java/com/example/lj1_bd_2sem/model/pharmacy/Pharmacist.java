package com.example.lj1_bd_2sem.model.pharmacy;

import com.example.lj1_bd_2sem.model.common.Customer;
import com.example.lj1_bd_2sem.model.common.Employee;
import com.example.lj1_bd_2sem.model.common.Product;

import java.awt.*;

public class Pharmacist extends Employee {
    public Pharmacist(String cpf, Integer idade, String name){
        super(cpf, idade, name);
    }

    public void searchMedicine(Pharmacy pharmacy, String name, Customer customer){
        Product product = pharmacy.searchMedicine(name, this);
        if (product != null){
            System.out.println("Found the medicine: "+product.getName());
        } else {
            System.out.println("Medicine "+name+"not found");
        }
    }

}

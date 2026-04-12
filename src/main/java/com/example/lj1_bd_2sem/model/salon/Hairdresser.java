package com.example.lj1_bd_2sem.model.salon;

import com.example.lj1_bd_2sem.model.common.Customer;
import com.example.lj1_bd_2sem.model.common.Employee;

public class Hairdresser extends Employee {
    public Hairdresser(String cpf, Integer idade, String name) {
        super(cpf, idade, name);
    }

    public void cutHair(Customer customer) {
        System.out.println(getName() + " is cutting " +customer.getName()+ "'s hair...");
    }

    public void washHair(Customer customer){
        System.out.println(getName() + " is washing " + customer.getName() + "'s hair...");
    }
}

package com.example.lj1_bd_2sem.model.salon;

import com.example.lj1_bd_2sem.model.common.Business;
import com.example.lj1_bd_2sem.model.common.Employee;

import java.util.List;

public class Salon extends Business {
    public Salon (List<Employee> employees, String cnpj){
        super(employees, cnpj);
    }
}

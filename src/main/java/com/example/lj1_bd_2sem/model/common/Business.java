package com.example.lj1_bd_2sem.model.common;

import java.util.List;

public abstract class Business {
    private List<Employee> employees;
    private String cnpj;

    public Business(List<Employee> employees, String cnpj){
        this.employees = employees;
        this.cnpj = cnpj;
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
    }

    public void releaseEmployee(Employee employee){
        employees.remove(employee);
    }

    public Employee searchEmployee(String name){
        for (Employee employee : employees){
            if (employee.getName().equals(name)){
                return employee;
            }
        }
        return null;
    }
}

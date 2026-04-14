package com.example.lj1_bd_2sem.model.common;

import jdk.internal.foreign.abi.fallback.FallbackLinker;

import java.util.List;

public abstract class Business {
    private List<Employee> employees;
    private List<Product> products;
    private String cnpj;
    private Double earnings;

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

    public void addProduct(Product product){
        products.add(product);
    }

    public boolean contains(Product product){
        if (products.contains(product)){
            return true;
        }
        return false;
    }

    public boolean processSale(Product product){
        if (!contains(product) || product.getQuantity() <= 0){
            return false;
        }

        product.reduceQuantity(1);
        earnings += product.getPrice();
        return true;
    }
}

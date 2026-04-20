package com.example.lj1_bd_2sem.model.common;

import java.util.ArrayList;
import java.util.List;

public abstract class Business {
    private List<Employee> employees = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private String cnpj;
    private Double earnings = 0.0;

    public Business(String cnpj){
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

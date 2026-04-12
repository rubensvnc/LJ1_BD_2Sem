package com.example.lj1_bd_2sem.model.pharmacy;

import com.example.lj1_bd_2sem.model.common.Employee;
import com.example.lj1_bd_2sem.model.common.Product;

import java.util.List;

public class Pharmacy {
    private List<Employee> employees;
    private List<Product> products;

    public void addEmployee(Employee employee){
        employees.add(employee);
    }
    public void removeEmployee(Employee employee){
        employees.remove(employee);
    }

    public Employee searchEmployee(String name){
        for (Employee employee : employees){
            if (employee.getName().equals(name)){
                System.out.println("Employee found!");;
                return employee;
            }
        }
        System.out.println("Employee not found.");
        return null;
    }

    public Product searchMedicine(String name, Employee employee){
        System.out.printf(employee.getName()+ "is in the storage...");
        for (Product product : products){
            if (product.getName().equals(name)){
                return product;
            }
        }
        return null;
    }
}

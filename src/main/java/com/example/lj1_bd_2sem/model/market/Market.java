package com.example.lj1_bd_2sem.model.market;

import com.example.lj1_bd_2sem.model.common.Employee;

import java.util.List;

public class Market {
    List<Employee> employees;

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
}

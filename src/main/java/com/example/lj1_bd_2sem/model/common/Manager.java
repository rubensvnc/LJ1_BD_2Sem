package com.example.lj1_bd_2sem.model.common;

public class Manager extends Employee{
    public Manager(String cpf, Integer idade, String name){
        super(cpf, idade, name);
    }

    public void hireStaff(Business workplace, Employee candidate) {
        workplace.addEmployee(candidate);
    }

    public void fireStaff(Business workplace, Employee candidate) {
        workplace.releaseEmployee(candidate);
    }

    public Employee findStaffMember(Business workplace, String name) {
        return workplace.searchEmployee(name);
    }
}

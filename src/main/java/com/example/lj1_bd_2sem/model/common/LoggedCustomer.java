package com.example.lj1_bd_2sem.model.common;

public class LoggedCustomer extends Customer {

    // This static variable holds the one and only logged-in customer
    private static Customer instance;

    public LoggedCustomer(int cpf, int age, String name, Double balance){
        super(cpf, age, name, balance);
    }

    // Method to set the customer (Login)
    public static void setLoggedCustomer(Customer customer) {
        instance = customer;
    }

    // Method to check who is logged in
    public static Customer getLoggedCustomer(){
        return instance;
    }

    // Method to logout
    public static void logout() {
        instance = null;
    }
}
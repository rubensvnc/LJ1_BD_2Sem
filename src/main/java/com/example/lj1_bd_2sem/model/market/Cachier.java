package com.example.lj1_bd_2sem.model.market;

import com.example.lj1_bd_2sem.model.common.Customer;
import com.example.lj1_bd_2sem.model.common.Employee;
import com.example.lj1_bd_2sem.model.common.Product;

import java.util.List;

public class Cachier extends Employee {
    public Cachier(String cpf, Integer idade, String name) {
        super(cpf, idade, name);
    }

    public Order processPurchase(Customer customer, List<Product> cartItems) {
        Order newOrder = new Order(customer);

        System.out.println("Processing purchase for: " + customer.getName());

        for (Product item : cartItems) {
            newOrder.addItem(item);
            System.out.println("Item: " + item.getName());
        }

        return newOrder;
    }
}

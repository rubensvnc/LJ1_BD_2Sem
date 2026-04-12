package com.example.lj1_bd_2sem.model.market;

import com.example.lj1_bd_2sem.model.common.Customer;
import com.example.lj1_bd_2sem.model.common.Product;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Customer customer;
    private List<Product> items;
    private Double total;

    public Order(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
        this.total = 0.0;
    }

    public void addItem(Product p) {
        items.add(p);
        total += p.getPrice();
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getItems() {
        return items;
    }

    public Double getTotal() {
        return total;
    }
}

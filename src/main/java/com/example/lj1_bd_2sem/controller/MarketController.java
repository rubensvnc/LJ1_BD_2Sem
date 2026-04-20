package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.model.common.*;
import com.example.lj1_bd_2sem.model.market.Cachier;
import com.example.lj1_bd_2sem.model.market.Food;
import com.example.lj1_bd_2sem.model.market.Market;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarketController {
    @FXML Button gotoHomeBtn;
    @FXML Button buyAppleBtn;
    @FXML Button buyGrapeBtn;
    @FXML Button buyBananaBtn;
    @FXML Button processPurchaseBtn;
    @FXML Button retrieveItemsBtn;

    @FXML Label cartItensQuantityLbl;
    @FXML Label balanceLbl;

    private List<Product> customerCart = new ArrayList<>();

    private Food banana = new Food("banana", 12.99, 4, "12/03/2027");
    private Food apple = new Food("apple", 1.99, 4, "22/05/2027");
    private Food grape = new Food("grape", 2.99, 4, "02/01/2027");

    private Market market = new Market("12h132n131");

    private Customer currentCustomer = LoggedCustomer.getLoggedCustomer();

    private Double customerBalance = currentCustomer.getBalance();

    @FXML
    public void initialize(){
        balanceLbl.setText(Double.toString(customerBalance));
        cartItensQuantityLbl.setText(Integer.toString(customerCart.size()));

        market.addProduct(banana);
        market.addProduct(apple);
        market.addProduct(grape);

    }

    public void buyFood(Food food){
        if (food.getQuantity() >= 0){
            if (customerBalance >= food.getPrice()){
                customerCart.add(food);
                customerBalance -= food.getPrice();
                updateBalance();
                updateCartQuantity();

                processPurchaseBtn.setDisable(false);
                retrieveItemsBtn.setDisable(false);
            } else {
                System.out.println("Not enough balance!");
            }
        } else {
            System.out.println("We are all out on "+food.getName()+", sorry!");
        }
    }

    public void buyApple(){
        buyFood(apple);
    }

    public void buyBanana(){
        buyFood(banana);
    }

    public void buyGrape(){
        buyFood(grape);
    }

    public void processPurchase(){
        Manager manager = new Manager("123.456.789.20", 42, "Roberta");
        Cachier cachier = new Cachier("123.456.789.10", 21, "Julio");

        manager.hireStaff(market, cachier);

        cachier.processPurchase(currentCustomer, customerCart);

        for (Product food : customerCart){
            currentCustomer.purchase(food, market);
            customerBalance = currentCustomer.getBalance();
        }

        customerCart.clear();

        updateBalance();
        updateCartQuantity();

        processPurchaseBtn.setDisable(true);
        retrieveItemsBtn.setDisable(true);
    }

    public void retrieveItems(){
        customerCart.clear();
        customerBalance = currentCustomer.getBalance();

        updateBalance();
        updateCartQuantity();

        retrieveItemsBtn.setDisable(true);
        processPurchaseBtn.setDisable(true);
    }

    public void updateBalance(){
        balanceLbl.setText(Double.toString(customerBalance));
    }

    public void updateCartQuantity(){
        cartItensQuantityLbl.setText(Integer.toString(customerCart.size()));
    }


    @FXML
    public void gotoHome(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/lj1_bd_2sem/controller/home.fxml"));
            gotoHomeBtn.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

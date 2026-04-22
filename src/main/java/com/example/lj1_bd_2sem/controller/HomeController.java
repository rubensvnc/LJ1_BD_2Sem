package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.model.common.Customer;
import com.example.lj1_bd_2sem.model.common.LoggedCustomer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HomeController {
    @FXML TextField nameTF;
    @FXML TextField ageTF;
    @FXML TextField cpfTF;
    @FXML TextField balanceTF;

    @FXML Button addCustomerBtn;
    @FXML Button replaceClienteBtn;
    @FXML Button gotoMarketBtn;
    @FXML Button gotoSalonBtn;
    @FXML Button gotoPharmacyBtn;

    @FXML
    public void initialize(){
        if (LoggedCustomer.getLoggedCustomer() != null){
            gotoMarketBtn.setDisable(false);
            gotoSalonBtn.setDisable(false);
            gotoPharmacyBtn.setDisable(false);

            nameTF.setText(LoggedCustomer.getLoggedCustomer().getName());
            ageTF.setText(Integer.toString(LoggedCustomer.getLoggedCustomer().getAge()));
            cpfTF.setText(Integer.toString(LoggedCustomer.getLoggedCustomer().getCpf()));
            balanceTF.setText(Double.toString(LoggedCustomer.getLoggedCustomer().getBalance()));

            nameTF.setDisable(true);
            ageTF.setDisable(true);
            cpfTF.setDisable(true);
            balanceTF.setDisable(true);
            addCustomerBtn.setDisable(true);
        } else {
            gotoMarketBtn.setDisable(true);
            gotoSalonBtn.setDisable(true);
            gotoPharmacyBtn.setDisable(true);

            nameTF.setText("");
            ageTF.setText("");
            cpfTF.setText("");
            balanceTF.setText("");
        }
    }

    public void addCustomer(){

        String name = nameTF.getText();
        int age;
        int cpf;
        double balance;

        if (!name.isEmpty()){
            if (!ageTF.getText().isEmpty() && !cpfTF.getText().isEmpty()){
                try {
                    age = Integer.parseInt(ageTF.getText());
                    cpf = Integer.parseInt(cpfTF.getText());

                    if (!balanceTF.getText().isEmpty()){
                        try {
                            balance = Double.parseDouble(balanceTF.getText());

                            Customer customer = new Customer(cpf, age, name, balance);
                            LoggedCustomer.setLoggedCustomer(customer);
                            initialize();
                            System.out.println("Customer added successfully!");
                        } catch (NumberFormatException e ){
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Balance cannot be empty!");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Age or CPF cannot be empty!");
            }
        } else {
            System.out.println("Name cannot be empty!");
        }

    }

    public void replaceClient(){
        LoggedCustomer.logout();
        addCustomerBtn.setDisable(false);

        nameTF.setDisable(false);
        ageTF.setDisable(false);
        cpfTF.setDisable(false);
        balanceTF.setDisable(false);
        addCustomerBtn.setDisable(false);

        initialize();
        System.out.println("Customer can be replaced now!");
    }

    @FXML
    public void gotoMarket(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/lj1_bd_2sem/controller/market.fxml"));
            gotoMarketBtn.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void gotoSalon(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/lj1_bd_2sem/controller/salon.fxml"));
            gotoSalonBtn.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void gotoPharmacy(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/lj1_bd_2sem/controller/pharmacy.fxml"));
            gotoPharmacyBtn.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.model.common.Customer;
import com.example.lj1_bd_2sem.model.common.LoggedCustomer;
import com.example.lj1_bd_2sem.model.common.Manager;
import com.example.lj1_bd_2sem.model.salon.Hairdresser;
import com.example.lj1_bd_2sem.model.salon.Style;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SalonController {
    @FXML Button gotoHomeBtn;
    @FXML Button cutHairBtn;
    @FXML Button washHairBtn;

    @FXML ComboBox<String> stylesCbox;

    @FXML Label balanceLbl;
    @FXML Label cutValueLbl;
    @FXML Label washValueLbl;

    private Customer loggedCustomer = LoggedCustomer.getLoggedCustomer();
    private List<Style> allStyles = new ArrayList<>();

    private Double washPrice = 3.58;

    public void initialize(){
        updateBalance();
        washValueLbl.setText(Double.toString(washPrice));

        ObservableList<String> styles = FXCollections.observableArrayList();
        Style cut1 = new Style("Down", 12.99);
        Style cut2 = new Style("Up", 22.99);
        Style cut3 = new Style("Left", 2.99);
        Style cut4 = new Style("Right", 2.99);

        allStyles.add(cut1);
        allStyles.add(cut2);
        allStyles.add(cut3);
        allStyles.add(cut4);

        styles.add(cut1.getName());
        styles.add(cut2.getName());
        styles.add(cut3.getName());
        styles.add(cut4.getName());
        stylesCbox.setItems(styles);
    }

    public void handleSelection(){
        if(stylesCbox.getValue() != null){
            cutHairBtn.setDisable(false);
            cutValueLbl.setText(Double.toString(getCutValue(stylesCbox.getValue())));
        } else {
            cutHairBtn.setDisable(true);
        }
    }

    public Double getCutValue(String name){
        for (Style style : allStyles){
            if (style.getName().equals(name)){
                return style.getPrice();
            }
        }
        return null;
    }

    public void cutHair(){
        Hairdresser pedro = new Hairdresser("213131", 22, "Pedro");
        if (loggedCustomer.getBalance() >= getCutValue(stylesCbox.getValue())){
            pedro.cutHair(loggedCustomer);
            loggedCustomer.purchase(getCutValue(stylesCbox.getValue()));
            updateBalance();
        } else {
            System.out.println("Not enough balance!");
        }

    }

    public void washHair(){
        Hairdresser julia = new Hairdresser("65432323", 20, "Julia");
        if (loggedCustomer.getBalance() >= washPrice){
            julia.washHair(loggedCustomer);
            loggedCustomer.purchase(washPrice);
            updateBalance();
        } else {
            System.out.println("Not enough balance!");
        }
    }

    public void updateBalance(){
        balanceLbl.setText(Double.toString(loggedCustomer.getBalance()));
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

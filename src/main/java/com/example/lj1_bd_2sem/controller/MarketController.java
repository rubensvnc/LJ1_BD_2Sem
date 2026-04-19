package com.example.lj1_bd_2sem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class MarketController {
    @FXML Button gotoHomeBtn;

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

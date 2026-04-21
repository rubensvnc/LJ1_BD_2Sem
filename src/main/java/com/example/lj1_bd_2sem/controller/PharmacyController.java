package com.example.lj1_bd_2sem.controller;

import com.example.lj1_bd_2sem.model.common.Customer;
import com.example.lj1_bd_2sem.model.common.LoggedCustomer;
import com.example.lj1_bd_2sem.model.pharmacy.Medicine;
import com.example.lj1_bd_2sem.model.salon.Style;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PharmacyController {
    @FXML Button gotoHomeBtn;
    @FXML Button buyMedicine;

    @FXML
    ComboBox<String> medicinesCbox;

    @FXML Label balanceLbl;
    @FXML Label medicinePriceLbl;
    @FXML Label medicineNameLbl;
    @FXML Label medicineExpirationDateLbl;
    @FXML Label medicineIndicatedUseLbl;
    @FXML Label medicinePrecautionsLbl;

    @FXML Spinner<Integer> quantitySpn;

    private Customer loggedCustomer = LoggedCustomer.getLoggedCustomer();

    private List<Medicine> allMedicines = new ArrayList<>();

    public void updateBalance(){
        balanceLbl.setText(Double.toString(loggedCustomer.getBalance()));
    }


    public void initialize(){
        updateBalance();

        ObservableList<String> medicinesList = FXCollections.observableArrayList();
        Medicine headacheM = new Medicine("HeaDX", 15.99, 3, "12/03/2027",
                "headaches", "Can cause stomach pain");
        Medicine stomachM = new Medicine("StomDX", 25.99, 2, "22/03/2027",
                "stomach pains", "Can cause headache");
        Medicine fluM = new Medicine("FluDX", 5.99, 25, "03/01/2027",
                "flue syntomns", "Can cause fever");

        allMedicines.add(headacheM);
        allMedicines.add(stomachM);
        allMedicines.add(fluM);

        medicinesList.add(headacheM.getName());
        medicinesList.add(stomachM.getName());
        medicinesList.add(fluM.getName());

        medicinesCbox.setItems(medicinesList);
    }

    public void handleSelectionCbox(){
        if (medicinesCbox.getValue() != null){
            Medicine medicine = getMedicine(medicinesCbox.getValue());

            SpinnerValueFactory<Integer> valueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, medicine.getQuantity(), 0);
            quantitySpn.setValueFactory(valueFactory);

            setMedicineInfo(medicine);

            buyMedicine.setDisable(false);
            quantitySpn.setDisable(false);
        } else {
            buyMedicine.setDisable(true);
            quantitySpn.setDisable(true);
        }
    }

    public void setMedicineInfo(Medicine medicine){
        medicinePriceLbl.setText(Double.toString(medicine.getPrice()));
        medicineNameLbl.setText(medicine.getName());
        medicineExpirationDateLbl.setText(medicine.getExpirationDate());
        medicineIndicatedUseLbl.setText(medicine.getIndicatedUse());
        medicinePrecautionsLbl.setText(medicine.getPrecautions());
    }

    public Medicine getMedicine(String name){
        for (Medicine medicine : allMedicines){
            if (medicine.getName().equals(name)){
                return medicine;
            }
        }
        return null;
    }

    public void buyMedicine(){
        Medicine medicine = getMedicine(medicinesCbox.getValue());
        Integer quantity = quantitySpn.getValue();

        if (medicine != null){
            if (loggedCustomer.getBalance() >= medicine.getPrice() * quantity){
                medicine.reduceQuantity(quantity);
                loggedCustomer.purchase(medicine.getPrice() * quantity);
                updateBalance();
            } else {
                System.out.println("Not enough balance!");
            }
        }
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

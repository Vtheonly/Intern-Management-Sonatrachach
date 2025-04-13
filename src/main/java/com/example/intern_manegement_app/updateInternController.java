package com.example.intern_manegement_app;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class updateInternController implements Initializable {

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField ageField;
    @FXML
    private ChoiceBox<String> themeChoiceBox;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField universityField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private ChoiceBox<String> internshipTypeChoiceBox;
    Map<String, String> parameters;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<String> themes = oracleConnector.getSelectableOptions("theme", "theme_name");
        themeChoiceBox.setItems(FXCollections.observableArrayList(themes));

        final String CURRENT_UPDATE_PARAM= insertionInternController.sendConstraint();
        parameters = toolkit.parseText( CURRENT_UPDATE_PARAM );

        fullNameField.setText(parameters.get("name"));
        emailField.setText(parameters.get("email"));
        ageField.setText(parameters.get("age"));
        phoneNumberField.setText(parameters.get("phone_number"));
        universityField.setText(parameters.get("university"));
        internshipTypeChoiceBox.setValue(parameters.get("IS_ACCEPTED"));
        themeChoiceBox.setValue(
                oracleConnector.getNameById( Integer.parseInt( parameters.get("theme_id")), "theme", "theme_id","theme_name")
        );

    }



    public void updateIntern(){
        Map<String, String> newUpdateParams = new HashMap<>();

        newUpdateParams.put("name", fullNameField.getText());
        newUpdateParams.put("email", emailField.getText());
        newUpdateParams.put("age", ageField.getText());
        newUpdateParams.put("phone_number", phoneNumberField.getText());
        newUpdateParams.put("university", universityField.getText());
        newUpdateParams.put("IS_ACCEPTED", internshipTypeChoiceBox.getValue());
        newUpdateParams.put("theme_id",
                String.valueOf(oracleConnector.getIdByName("theme", themeChoiceBox.getValue()))
        );

        Map<String, String> idNamePrimaryKey = new HashMap<>();
        idNamePrimaryKey.put("intern_id", parameters.get("intern_id"));
        idNamePrimaryKey.put("name", parameters.get("name"));

        oracleConnector.updateInter(idNamePrimaryKey,newUpdateParams);
    }


}
package com.example.intern_manegement_app;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
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
    Map<String, String> params;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oracleConnector Connection = new oracleConnector();


        final String CURRENT_UPDATE_PARAM=interInsertionController.sendConstraint();

        ArrayList<String> respos = Connection.getSelectableOptions("responsible", "name");
        ArrayList<String> themes = Connection.getSelectableOptions("theme", "theme_name");
        themeChoiceBox.setItems(FXCollections.observableArrayList(themes));


        params= parseText( CURRENT_UPDATE_PARAM );

        fullNameField.setText(params.get("name"));
        emailField.setText(params.get("email"));
        ageField.setText(params.get("age"));
        themeChoiceBox.setValue(
                oracleConnector.getNameById( Integer.parseInt( params.get("theme_id")), "theme", "theme_id","theme_name")
        );
        phoneNumberField.setText(params.get("phone_number"));
        universityField.setText(params.get("university"));
//         startDatePicker.setValue(LocalDate.parse(params.get("Start Date"))); // assuming you have a "Start Date" field in your HashMap
        internshipTypeChoiceBox.setValue(params.get("IS_ACCEPTED"));

    }



    public void updateIntern(){
        Map<String, String> newUpdateParams = new HashMap<>();

        newUpdateParams.put("name", fullNameField.getText());
        newUpdateParams.put("email", emailField.getText());
        newUpdateParams.put("age", ageField.getText());
        newUpdateParams.put("theme_id",

                String.valueOf(oracleConnector.getIdByName("theme",
                        themeChoiceBox.getValue()
                ))


        ); // Assuming themeChoiceBox is for theme_id
        newUpdateParams.put("phone_number", phoneNumberField.getText());
        newUpdateParams.put("university", universityField.getText());
        // newUpdateParams.put("start_date", startDatePicker.getValue().toString()); // Example for startDatePicker
        newUpdateParams.put("IS_ACCEPTED", internshipTypeChoiceBox.getValue()); // Assuming internshipTypeChoiceBox is for is_Accepted
        System.out.println(params);
        Map<String, String> idNamePK = new HashMap<>();
        idNamePK.put("intern_id", params.get("intern_id"));
        idNamePK.put("name", params.get("name"));

        oracleConnector.updateInter(idNamePK,newUpdateParams);
    }



    public static Map<String, String> parseText(String text) {
        Map<String, String> map = new HashMap<>();
        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                // Remove leading and trailing commas if present
                if (value.endsWith(",")) {
                    value = value.substring(0, value.length() - 1);
                }
                map.put(key, value);
            }
        }
        return map;
    }

}
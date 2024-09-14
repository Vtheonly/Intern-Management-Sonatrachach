package com.example.intern_manegement_app;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class workerUserUpdate implements Initializable {


    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private ChoiceBox<String> role;
    @FXML
    private ChoiceBox<String> department;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField faxNumber;
    @FXML
    private ChoiceBox<String> manager;
    @FXML
    private TextField fullName;
    @FXML
    private TextField age;
    @FXML
    private TextField email;
    final String CURRENT_UPDATE_PARAM = userInsertionController.sendConstraint();
    Map<String, String> params = updateInternController.parseText(CURRENT_UPDATE_PARAM);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oracleConnector Connection = new oracleConnector();



        System.out.println(params);
        ArrayList<String> roles = Connection.getSelectableOptions("role", "role_name");
        ArrayList<String> departments = Connection.getSelectableOptions("department", "department_name");
        ArrayList<String> managers = Connection.getSelectableOptions("worker_user", "full_name");



        role.setItems(FXCollections.observableArrayList(roles));
        department.setItems(FXCollections.observableArrayList(departments));
        manager.setItems(FXCollections.observableArrayList(managers));

        // Fill the fields and choice boxes with values from the params map
        fullName.setText(params.get("full_name"));
        email.setText(params.get("email_address"));
        phoneNumber.setText(params.get("phone_number"));
        faxNumber.setText(params.get("fax_number"));
        username.setText(params.get("username"));
        password.setText(params.get("password_hash")); // Uncomment if needed
        role.setValue(
                oracleConnector.getNameById(Integer.parseInt(params.get("role_id"))
                        ,"role"
                        ,"role_id",
                        "role_name"
                ));

        department.setValue(
                oracleConnector.getNameById(Integer.parseInt(params.get("department_id"))
                        ,"department"
                        ,"department_id"
                        ,"department_name"
                ));



        manager.setValue(
                oracleConnector.getNameById(Integer.parseInt(params.get("supervisor_id"))
                        ,"worker_user"
                        ,"supervisor_id"
                        ,"full_name"
                ));





    }

    public void updateWorker(){
        Map<String, String> paramsMap = new HashMap<>();
        Map<String, String> paramsNext =new HashMap<>();

        paramsMap.put("full_name", fullName.getText());
        paramsMap.put("email_address", email.getText());
        paramsMap.put("phone_number", phoneNumber.getText());
        paramsMap.put("fax_number", faxNumber.getText());
        paramsMap.put("username", username.getText());
        paramsMap.put("password_hash", password.getText()); // Uncomment if needed
        paramsMap.put("role_id",
                oracleConnector.getIdByName("role" ,role.getValue()).toString());
        paramsMap.put("department_id",
                oracleConnector.getIdByName("department" , department.getValue()).toString());
        paramsMap.put("supervisor_id",
                oracleConnector.getIdByName("worker_user" , manager.getValue()).toString());

        System.out.println(paramsMap);

        paramsNext.put("user_id",params.get("user_id"));
        paramsNext.put("full_name",params.get("full_name"));

        oracleConnector.updateWorkerUser(paramsNext,paramsMap);
    }





}

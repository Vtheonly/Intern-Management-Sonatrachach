package com.example.intern_manegement_app;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class DecisionIntern  implements Initializable {

    @FXML
    private TextField fullNameField;

    @FXML
    private ChoiceBox<String> themeChoiceBox;

    @FXML
    private VBox ResultPool;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oracleConnector Connection = new oracleConnector();
        ArrayList<String> themes = Connection.getSelectableOptions("theme", "theme_name");
        themeChoiceBox.setItems(FXCollections.observableArrayList(themes));

    }



    public void searchIntern() {

        ResultPool.getChildren().clear();

        Map<String, String> filters = new HashMap<>();

        if (!fullNameField.getText().isEmpty()) {
            filters.put("name", fullNameField.getText());
        }

        if (themeChoiceBox.getValue() != null && !themeChoiceBox.getValue().toString().isEmpty()) {
            Integer themeId = oracleConnector.getIdByName("theme",themeChoiceBox.getValue());
            filters.put("theme_id",Integer.toString(themeId));
        }


        List<Map<String, Object>> internData = oracleConnector.getInternRows(filters);


        for (Map<String, Object> intern : internData) {
            String name = intern.get("name") != null ? intern.get("name").toString() : "Unknown";
            String internString = intern.toString();
            String isAccepted = intern.get("is_Accepted") != null ? intern.get("is_Accepted").toString() : "Unknown";
            String theme = intern.get("theme_id") != null ? String.valueOf(oracleConnector.getThemeIdByName( String.valueOf( intern.get("theme_id")))) : "Unknown";
            addToPool(name, internString, isAccepted, theme);
        }


    }

    public void aacpetd_all(){
        oracleConnector.setAllInternsAccepted();
    }



    public void reject_all(){
        oracleConnector.setAllInternsRejected();
    }


    public void addToPool(String Title, String Info, String isAccepted, String Theme) {
        // Create a Label with formatted text and enable text wrapping
        Label innerLabel = new Label(interInsertionController.formatString(Info));
        innerLabel.setWrapText(true);

        String internID = updateInternController.parseText( innerLabel.getText()).get("intern_id");
        String InternName = updateInternController.parseText( innerLabel.getText()).get("name");
        // Create an HBox to hold the Accept and Reject buttons
        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(event -> {
            oracleConnector.internDecision(true,InternName,Integer.parseInt(internID));
        });

        Button rejectButton = new Button("Reject");
        rejectButton.setOnAction(event -> {
            oracleConnector.internDecision(false,InternName,Integer.parseInt(internID));
        });



        HBox buttonBox = new HBox(10, acceptButton, rejectButton); // Spacing between buttons
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // Create a VBox to hold the Label and the HBox with buttons
        VBox contentBox = new VBox(10, innerLabel, buttonBox); // Spacing between Label and buttons
        contentBox.setPadding(new Insets(10)); // Padding around the content
        contentBox.setAlignment(Pos.CENTER_LEFT);

        // Create an AnchorPane and add the VBox to it
        AnchorPane content = new AnchorPane();
        content.getChildren().add(contentBox);
        AnchorPane.setLeftAnchor(contentBox, 0.0);
        AnchorPane.setRightAnchor(contentBox, 0.0);

        // Create a TitledPane with the provided title and content
        TitledPane row = new TitledPane();
        row.setText(Title);
        row.setContent(content);
        row.setExpanded(false);

        // Add the TitledPane to the ResultPool
        ResultPool.getChildren().add(row);
    }



}

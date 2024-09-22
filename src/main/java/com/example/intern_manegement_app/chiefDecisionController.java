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
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.*;

import static com.example.intern_manegement_app.toolkit.formatString;
import static com.example.intern_manegement_app.toolkit.parseText;

public class chiefDecisionController implements Initializable {

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


        List<Map<String, Object>> internData = oracleConnector.searchIntern(filters);


        for (Map<String, Object> intern : internData) {
            String name = intern.get("name") != null ? intern.get("name").toString() : "Unknown";
            String internString = intern.toString();
            String isAccepted = intern.get("IS_ACCEPTED") != null ? intern.get("IS_ACCEPTED").toString() : "Unknown";



            String theme ;
            if (intern.get("theme_id") != null) {
                theme = oracleConnector.getNameById(
                        Integer.parseInt((String)intern.get("theme_id")),
                        "theme",
                        "theme_id",
                        "theme_name"
                );
            } else {
                theme = "UnknownTH";
            }


            addToPool(name, internString, isAccepted, theme);
        }

    }
    public void acceptedAll(){
        oracleConnector.setAllInternsAccepted();
    }
    public void rejectedAll(){
        oracleConnector.setAllInternsRejected();
    }
    public void addToPool(String Title, String Info, String isAccepted, String Theme) {
        // Create a Label with formatted text and enable text wrapping
        Label innerLabel = new Label(formatString(Info));
        innerLabel.setWrapText(true);

        String internID = parseText( innerLabel.getText()).get("intern_id");
        String InternName = parseText( innerLabel.getText()).get("name");

        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(event -> {
            oracleConnector.updateChiefDecision(true,InternName,Integer.parseInt(internID));
        });

        Button rejectButton = new Button("Reject");
        rejectButton.setOnAction(event -> {
            oracleConnector.updateChiefDecision(false,InternName,Integer.parseInt(internID));
        });

        HBox buttonBox = new HBox(10, acceptButton, rejectButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        VBox contentBox = new VBox(10, innerLabel, buttonBox);
        contentBox.setPadding(new Insets(10));
        contentBox.setAlignment(Pos.CENTER_LEFT);

        Label themeLabel = new Label(Theme);
        themeLabel.setContentDisplay(ContentDisplay.LEFT);
        themeLabel.setStyle("-fx-font-weight: bold; -fx-font-style: italic;");
        Label isAcceptedLabel = new Label(isAccepted);

        switch (isAccepted) {
            case "Accepted":
                isAcceptedLabel.setStyle("-fx-text-fill: green;");
                break;
            case "Rejected":
                isAcceptedLabel.setStyle("-fx-text-fill: red;");
                break;
            case "Hold":
                isAcceptedLabel.setStyle("-fx-text-fill: orange;");
                break;
            default:
                isAcceptedLabel.setStyle("-fx-text-fill: black;");
                break;
        }
        themeLabel.setGraphic(isAcceptedLabel);

        AnchorPane content = new AnchorPane();
        content.getChildren().add(contentBox);
        AnchorPane.setLeftAnchor(contentBox, 0.0);
        AnchorPane.setRightAnchor(contentBox, 0.0);

        TitledPane row = new TitledPane();
        row.setText(Title);
        row.setContent(content);
        row.setGraphic(themeLabel);
        row.setExpanded(false);
        row.setTextAlignment(TextAlignment.CENTER);
        row.setAlignment(Pos.CENTER);
        row.setContentDisplay(ContentDisplay.BOTTOM);
        ResultPool.getChildren().add(row);
    }

}

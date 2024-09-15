package com.example.intern_manegement_app;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.StringReader;

public class interInsertionController implements Initializable {
//  to exchange data in update and delete needs to be global
  static String labelText;

  oracleConnector Connection = new oracleConnector();
  @FXML private Tab manageTab;
  @FXML private Tab emailTab;
  @FXML private Tab reportTab;


  // insertion part
  @FXML private ChoiceBox<String> insert_intern_theme;
  @FXML private ChoiceBox<String> insert_intern_type;
  @FXML private TextField insert_intern_Name;
  @FXML private TextField insert_intern_Age;
  @FXML private TextField insert_intern_Email;
  @FXML private TextField insert_intern_University;
  @FXML private TextField insert_intern_Phone;
  @FXML private DatePicker insert_intern_StartDate;

  // Search_tab part
  @FXML private VBox ResultPool;
  @FXML private ChoiceBox<String> search_intern_theme;
  @FXML private ChoiceBox<String> searchInternType;
  @FXML private TextField search_intern_Name;
  @FXML private TextField search_intern_Age;
  @FXML private TextField search_intern_Email;
  @FXML private TextField search_intern_University;
  @FXML private TextField search_intern_Phone;
  @FXML private DatePicker search_intern_StartDate;

// Report_tab
  @FXML private HTMLEditor PDF_INPUT;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //   choice box filling
    String[] list = {"1 month ","2 months" ,"3 months"};
    ArrayList<String> internships_types = new ArrayList<String>(Arrays.asList(list));
    ArrayList<String> themes = Connection.getSelectableOptions("theme", "theme_name");
    search_intern_theme.setItems(FXCollections.observableArrayList(themes));
    insert_intern_theme.setItems(FXCollections.observableArrayList(themes));
    searchInternType.setItems(FXCollections.observableArrayList(internships_types));
    insert_intern_type.setItems(FXCollections.observableArrayList(internships_types));


  }
// clears all inputs and query
  @FXML
  public void clearInputs() {
    insert_intern_Name.clear();
    insert_intern_Age.clear();
    insert_intern_Email.clear();
    insert_intern_University.clear();
    insert_intern_Phone.clear();

    search_intern_Name.clear();
    search_intern_Age.clear();
    search_intern_Email.clear();
    search_intern_University.clear();
    search_intern_Phone.clear();

    insert_intern_theme.getSelectionModel().clearSelection();
    insert_intern_type.getSelectionModel().clearSelection();
    search_intern_theme.getSelectionModel().clearSelection();
    searchInternType.getSelectionModel().clearSelection();
  }

  public void setAccessLevel(int accessLevel) {
    // Disable all tabs initially
    manageTab.setDisable(true);
    emailTab.setDisable(true);
    reportTab.setDisable(true);

    // Enable tabs based on the access level
    switch (accessLevel) {
      case 1:
        manageTab.setDisable(false); // Access to manage tab only
        break;
      case 2:
        emailTab.setDisable(false); // Access to email tab only
        reportTab.setDisable(false); // Access to report tab only
        break;
      case 3:
        manageTab.setDisable(false); // has them all
        emailTab.setDisable(false);
        reportTab.setDisable(false);
        break;
      default:
        // No access to any tabs
        break;
    }
  }

// this func was AI generated
  public static String formatString(String Info) {
    // Remove the curly braces
    Info = Info.substring(1, Info.length() - 1);

    // Split the string by ", " to get key-value pairs
    String[] pairs = Info.split(", ");

    // Use a StringBuilder to construct the result string
    StringBuilder result = new StringBuilder();

    // Iterate over the pairs and format them
    for (String pair : pairs) {
      // Split each pair by "=" to get key and value
      String[] keyValue = pair.split("=");
      String key = keyValue[0];
      String value = keyValue[1];

      // Append formatted key-value to the result
      result.append(key).append(" : ").append(value).append(",\n");
    }

    // Remove the trailing comma and newline
    if (result.length() > 0) {
      result.setLength(result.length() - 2);
    }

    return result.toString();
  }

  @FXML
  public void insertInternController() {

    HashMap<String, Object> internData = new HashMap<>();
//    the autoincrement was unstable and required triggers
    int newInternId = oracleConnector.getMaxId("intern", "intern_id") + 1;

    internData.put("intern_id", newInternId);
    internData.put("name", insert_intern_Name.getText());
    internData.put("age",insert_intern_Age.getText());
    internData.put("email", insert_intern_Email.getText());
    internData.put("university", insert_intern_University.getText());
    internData.put("phone_number", insert_intern_Phone.getText());
    internData.put("IS_ACCEPTED", "hold");

//    handling null /empty input (for verification)
    if(insert_intern_StartDate.getValue()==null){
      internData.put("start_date", "");
    } else {
      internData.put("start_date", insert_intern_StartDate.getValue().toString());
    }
    if(insert_intern_type.getValue()==null){
      internData.put("end_date", "");
    }else {
      internData.put("end_date", insert_intern_type.getValue().toString());
    }
    String themeName = insert_intern_theme.getValue();
    if (themeName != null && !themeName.isEmpty()) {
    // gets it by the function because it doesn't understand text only the id
      Integer themeId = oracleConnector.getIdByName("theme", themeName);
      internData.put("theme_id", themeId);
    }


// verification for missing args
    String[] keys = internData.keySet().toArray(new String[0]);
    String value;
    for (String key : keys) {
    value =internData.get(key).toString();
      if(value=="" || value.isEmpty()){// missing arg empty
        JOptionPane.showMessageDialog(null, "Fill all the inputs fields", "Missing Parameters", JOptionPane.WARNING_MESSAGE);
        return; // the transaction doesn't happen
      }
    }


//    check if phone number and age are numbers
try {
      Integer age =  Integer.parseInt(String.valueOf(insert_intern_Age.getText()));
      Integer phone =  Integer.parseInt(String.valueOf(insert_intern_Phone.getText()));
      // if he inputs a mixed text the number cant be parsed there for invalid

}catch(Exception e){
  JOptionPane.showMessageDialog(null, "Fill fields with valid inputs", "invalid Parameters", JOptionPane.WARNING_MESSAGE);
  return;
}




    // TODO make a string to text-date javaFX to oracleSQL parsing function
    internData.put("start_date", null);
    internData.put("end_date", null);
    // TODO make a reference to the user id of the user
    internData.put("inserted_by", null); // fix null later just defaults
    oracleConnector.insertIntern(internData);
  }

  public void addToPool(String Title, String Info, String isAccepted, String Theme) {
    // Create the inner label for the info
    Label innerLabel = new Label(formatString(Info));
    innerLabel.setWrapText(true);
    AnchorPane.setLeftAnchor(innerLabel, 0.0);
    AnchorPane.setRightAnchor(innerLabel, 0.0);

    // Create an AnchorPane for the inner label
    AnchorPane content = new AnchorPane();
    innerLabel.prefWidthProperty().bind(content.widthProperty());
    content.getChildren().add(innerLabel);

    Button UpdateButton = new Button("Update");
    Button DeleteButton = new Button("Delete");
    Button PrintButton = new Button("Print");

    DeleteButton.setOnAction(event -> {
      // Confirmation dialog
      Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
      confirmationAlert.setTitle("Confirmation");
      confirmationAlert.setHeaderText(null);
      confirmationAlert.setContentText("Do you want to delete this item?");
      if (confirmationAlert.showAndWait().get() == javafx.scene.control.ButtonType.OK) {

        // parses the innerTEXT of the "DOM" of the inside of the pane
        labelText = ((Label) ((AnchorPane) ((VBox)  DeleteButton.getParent()).getChildren().get(0)).getChildren().get(0)).getText();
        Map<String, String> params=updateInternController.parseText(labelText);
        oracleConnector.deleteIntern(Integer.parseInt(params.get("intern_id")),params.get("name") );
      }
    });

    UpdateButton.setOnAction(event -> {
//      load the update form the has its own controller and can get the inner_label
      labelText = innerLabel.getText();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("update_intern.fxml"));
      Parent root;
      try {
        root = fxmlLoader.load();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();
    });

    //  TODO add a print functionality
//  PrintButton.setOnAction(event ->{});

    // Create a VBox to hold the content and the buttonbox
    VBox vbox = new VBox(10); // Set spacing between children

    HBox buttonBox = new HBox(10,  UpdateButton,DeleteButton,PrintButton); // Spacing between buttons
    buttonBox.setAlignment(Pos.CENTER_RIGHT);

    vbox.getChildren().addAll(content, buttonBox);
    vbox.setAlignment(Pos.CENTER); // Center alignment for the VBox

    // Create a nested label for the graphic
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

    // Create the TitledPane the whole wrapper
    TitledPane row = new TitledPane();
    row.setText(Title);
    row.setContent(vbox);
    row.setGraphic(themeLabel);
    row.setExpanded(false);
    row.setTextAlignment(TextAlignment.CENTER);
    row.setAlignment(Pos.CENTER);
    row.setContentDisplay(ContentDisplay.BOTTOM);
    ResultPool.getChildren().add(row);
  }

  public void makePDF() {
    // Create a new document
    Document document = new Document();
    try {
      // Get HTML content from HTMLEditor
      String htmlContent = PDF_INPUT.getHtmlText();

      // Initialize PdfWriter
      PdfWriter.getInstance(document, new FileOutputStream("document_now.pdf"));

      // Open the document
      document.open();

      // Parse HTML content and add to the document
      HTMLWorker htmlWorker = new HTMLWorker(document);
      htmlWorker.parse(new StringReader(htmlContent));

    } catch (DocumentException | IOException e) {
      e.printStackTrace();
    } finally {
      // Close the document
      document.close();
    }
  }

  public void searchIntern() {
    ResultPool.getChildren().clear();
    Map<String, String> filters = new HashMap<>();

    // Check and add the param and it's value
    if (!search_intern_Name.getText().isEmpty()) {
      filters.put("name", search_intern_Name.getText());
    }

    if (!search_intern_Age.getText().isEmpty()) {
      filters.put("age", search_intern_Age.getText());
    }

    if (!search_intern_Email.getText().isEmpty()) {
      filters.put("email", search_intern_Email.getText());
    }

//  translate to id first then add
    if (search_intern_theme.getValue()!= null) {
      filters.put("theme_id",String.valueOf(oracleConnector.getThemeIdByName(search_intern_theme.getValue())));
    }

    if (!search_intern_University.getText().isEmpty()) {
      filters.put("university", search_intern_University.getText());
    }
    if (!search_intern_Phone.getText().isEmpty()) {
      filters.put("phone_number", search_intern_Phone.getText());
    }

//    get the result according to the constraints
    List<Map<String, Object>> internData = oracleConnector.getInternRows(filters);

    for (Map<String, Object> intern : internData) {
      // Safely get values with null checks
      String name = intern.get("name") != null ? intern.get("name").toString() : "Unknown";
      String internString = intern.toString();

      // Check if "is_Accepted" is not null and assign its value to isAccepted, otherwise assign "Unknown"
      String isAccepted;
      if (intern.get("IS_ACCEPTED") != null) {
        isAccepted = intern.get("IS_ACCEPTED").toString();
      } else {
        isAccepted = "UnknownA";
      }

      String theme;
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

  public static String sendConstraint(){return labelText;}
}
package com.example.intern_manegement_app;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;

public class userInsertionController implements Initializable {
  oracleConnector Connection = new oracleConnector();

  static String labelText;
  // Search tab part
  @FXML private VBox ResultPool;
  @FXML private VBox ResultThemePool;
  @FXML private VBox ResultDepartmentPool;


  @FXML private ChoiceBox<String> Department;
  @FXML private ChoiceBox<String> supervisor;
  @FXML private ChoiceBox<String> role_type;
  @FXML private TextField full_name;
  @FXML private TextField age;
  @FXML private TextField username;
  @FXML private TextField email_address;
  @FXML private TextField phone_number;
  @FXML private TextField fax_number;
  @FXML private TextField password;
  @FXML private Button userSearch;
  @FXML private Button userInsert;
  // theme part
  @FXML private TextArea description;
  @FXML private TextField theme_name;
  @FXML private ChoiceBox<String> Theme_department;
  // department part


  @FXML private TextField location;
  @FXML private TextField department_name;
  @FXML private TextArea department_description;
  @FXML private TextField departemnt_fax;
  //  Statistics part
  @FXML private PieChart theme_chart;
  @FXML private PieChart intern_chart;
  @Override


  public void initialize(URL location, ResourceBundle resources) {
    //   choice box filling
    ArrayList<String> roles = Connection.getSelectableOptions("role", "role_name");
    ArrayList<String> Deps = Connection.getSelectableOptions("department", "department_name");
    ArrayList<String> SuperVisorS = Connection.getSelectableOptions("worker_user", "full_name");
    Department.setItems(FXCollections.observableArrayList(Deps));
    Theme_department.setItems(FXCollections.observableArrayList(Deps));
    supervisor.setItems(FXCollections.observableArrayList(SuperVisorS));
    role_type.setItems(FXCollections.observableArrayList(roles));
    // statics part
    ObservableList<PieChart.Data> pieChartDataThemes =
        FXCollections.observableArrayList(
            new PieChart.Data("Department 3", 33),
            new PieChart.Data("Department 2", 20),
            new PieChart.Data("Department 1", 17),
            new PieChart.Data("Department 1", 30));
    ObservableList<PieChart.Data> pieChartDataInterns =
            FXCollections.observableArrayList(
                    new PieChart.Data("Department 3", 33),
                    new PieChart.Data("Department 2", 20),
                    new PieChart.Data("Department 1", 17),
                    new PieChart.Data("Department 1", 30));
    theme_chart.setData(pieChartDataThemes);
    intern_chart.setData(pieChartDataInterns);
    theme_chart.setTitle("Themes by Departments");
    intern_chart.setTitle("Themes by Interns");

  }


  @FXML
  public void refresh() {
    // Clear text fields
    full_name.clear();
    age.clear();
    username.clear();
    email_address.clear();
    phone_number.clear();
    fax_number.clear();
    password.clear();
    description.clear();
    theme_name.clear();
    location.clear();
    department_name.clear();
    department_description.clear();
    departemnt_fax.clear();

    // Unselect choice boxes
    Department.getSelectionModel().clearSelection();
    supervisor.getSelectionModel().clearSelection();
    role_type.getSelectionModel().clearSelection();
    Theme_department.getSelectionModel().clearSelection();

    // Clear result pool (assuming ResultPool is a VBox)
    ResultPool.getChildren().clear();
  }


  @FXML
  public void themeSearch() {
    ResultThemePool.getChildren().clear();

    HashMap<String, Object> filters = new HashMap<>();

    // Add input values to filters
    if (theme_name.getText() != null && !theme_name.getText().isEmpty()) {
      filters.put("theme_name", theme_name.getText());
    }

    if (description.getText() != null && !description.getText().isEmpty()) {
      filters.put("description", description.getText());
    }

    if (Theme_department.getValue() != null) {
      filters.put("department_id", String.valueOf(oracleConnector.getIdByName("department", Theme_department.getValue())));
    }

    List<Map<String, Object>> themeData = oracleConnector.searchTheme(filters);

    for (Map<String, Object> theme : themeData) {
      // Safely get values with null checks
      String name = theme.get("theme_name") != null ? theme.get("theme_name").toString() : "Unknown";
      String themeString = theme.toString();
      addToThemePool(name, themeString);
    }
  }


  private void addToThemePool(String title, String info) {
    // The label containing the text
    Label innerLabel = new Label(interInsertionController.formatString(info));
    innerLabel.setWrapText(true);
    // to fit the width
    AnchorPane.setLeftAnchor(innerLabel, 0.0);
    AnchorPane.setRightAnchor(innerLabel, 0.0);

    Button buttonDel = new Button("Delete");


    buttonDel.setOnAction(event -> {
      labelText = ((Label) ((AnchorPane) ((VBox) buttonDel.getParent()).getChildren().get(0)).getChildren().get(0)).getText();
      Map<String, String> params = updateInternController.parseText(labelText);
      oracleConnector.deleteTheme(Integer.parseInt(params.get("theme_id")),params.get("theme_name"));
    });

      // Create the AnchorPane to hold the label
    AnchorPane content = new AnchorPane();
    innerLabel.prefWidthProperty().bind(content.widthProperty());
    content.getChildren().add(innerLabel);

    // Create a VBox to hold both the label and the button
    VBox vbox = new VBox(10); // Set spacing between children
    vbox.getChildren().addAll(content, buttonDel);
    vbox.setAlignment(Pos.CENTER); // Center alignment for the VBox

    // Create the TitledPane
    TitledPane row = new TitledPane();
    row.setText(title);
    row.setContent(vbox);
    row.setExpanded(false);

    // Add the TitledPane to the ResultThemePool
    ResultThemePool.getChildren().add(row);
  }


  public void addToPool(String Title, String Info) {

    // The label containing the text
    Label innerLabel = new Label(interInsertionController.formatString(Info));
    innerLabel.setWrapText(true);
    // to fit the width
    AnchorPane.setLeftAnchor(innerLabel, 0.0);
    AnchorPane.setRightAnchor(innerLabel, 0.0);

    Button button = new Button("Update");
    Button buttondel = new Button("Delete");



    buttondel.setOnAction(event -> {
      labelText = ((Label) ((AnchorPane) ((VBox)  buttondel.getParent()).getChildren().get(0)).getChildren().get(0)).getText();
      Map<String, String> params=updateInternController.parseText(labelText);
      oracleConnector.deleteWorkerUser(Integer.parseInt(params.get("user_id")),params.get("full_name") );

    });






    button.setOnAction(event -> {
      // Handle button click event here
      // Access the label's text
      labelText = ((Label) ((AnchorPane) ((VBox) button.getParent()).getChildren().get(0)).getChildren().get(0)).getText();
      // Print the label's text




      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("update_worker_user.fxml"));
      Parent root = null;
      try {
        root = fxmlLoader.load();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.show();
    });

    // Create the AnchorPane to hold the label
    AnchorPane content = new AnchorPane();
    innerLabel.prefWidthProperty().bind(content.widthProperty());
    content.getChildren().add(innerLabel);

// Create a VBox to hold both the label and the button
    VBox vbox = new VBox(10); // Set spacing between children
    vbox.getChildren().addAll(content, button,buttondel);
    vbox.setAlignment(Pos.CENTER); // Center alignment for the VBox


    // Create the TitledPane
    TitledPane row = new TitledPane();
    row.setText(Title);
    row.setContent(vbox);
    row.setExpanded(false);


    // Add the TitledPane to the ResultPool
    ResultPool.getChildren().add(row);
  }


  private void addToDepartmentPool(String title, String info) {
    Label innerLabel = new Label(interInsertionController.formatString(info));
    innerLabel.setWrapText(true);
    AnchorPane.setLeftAnchor(innerLabel, 0.0);
    AnchorPane.setRightAnchor(innerLabel, 0.0);

    Button buttonDel = new Button("Delete");

    AnchorPane content = new AnchorPane();
    innerLabel.prefWidthProperty().bind(content.widthProperty());
    content.getChildren().add(innerLabel);

    VBox vbox = new VBox(10);
    vbox.getChildren().addAll(content, buttonDel);
    vbox.setAlignment(Pos.CENTER);

    TitledPane row = new TitledPane();
    row.setText(title);
    row.setContent(vbox);
    row.setExpanded(false);

    ResultDepartmentPool.getChildren().add(row);
  }

  @FXML
  public void workerUserSearch() {
    ResultPool.getChildren().clear();

    Map<String, String> filters = new HashMap<>();

    // Check and add 'full_name'
    if (!full_name.getText().isEmpty()) {
      filters.put("full_name", full_name.getText());
    }

    // Check and add 'age'
    if (!age.getText().isEmpty()) {
      filters.put("age", age.getText());
    }

    // Check and add 'email_address'
    if (!email_address.getText().isEmpty()) {
      filters.put("email_address", email_address.getText());
    }

    // Check and add 'phone_number'
    if (!phone_number.getText().isEmpty()) {
      filters.put("phone_number", phone_number.getText());
    }

    // Check and add 'fax_number'
    if (!fax_number.getText().isEmpty()) {
      filters.put("fax_number", fax_number.getText());
    }

    // Check and add 'username'
    if (!username.getText().isEmpty()) {
      filters.put("username", username.getText());
    }

    // Check and add 'department_id' (assuming this is from Department)
    if (Department.getValue() != null) {
      filters.put("department_id", String.valueOf(oracleConnector.getIdByName("department", Department.getValue())));
    }

    // Check and add 'role_id' (assuming this is from role_type)
    if (role_type.getValue() != null) {
      filters.put("role_id", String.valueOf(oracleConnector.getIdByName("role", role_type.getValue())));
    }

    // Check and add 'supervisor_id' (assuming this is from supervisor)
    if (supervisor.getValue() != null) {
      filters.put("supervisor_id", String.valueOf(oracleConnector.getIdByName("worker_user", supervisor.getValue())));
    }

    List<Map<String, Object>> workerUserData = oracleConnector.getWorkerUserRows(filters);

    for (Map<String, Object> workerUser : workerUserData) {
      // Safely get values with null checks
      String name = workerUser.get("full_name") != null ? workerUser.get("full_name").toString() : "Unknown";
      String workerUserString = workerUser.toString();
      addToPool(name, workerUserString);
    }
  }

  @FXML
  public void workerUserInsert() throws SQLException, NoSuchAlgorithmException {


    Map<String, String> insertParameters = new HashMap<>();

    insertParameters.put("user_id", String.valueOf(oracleConnector.getMaxId("worker_user","user_id")+1));
    insertParameters.put("full_name", full_name.getText());
    insertParameters.put("username", username.getText());
    insertParameters.put("email_address", email_address.getText());
    insertParameters.put("phone_number", phone_number.getText());
    insertParameters.put("fax_number", fax_number.getText());
    insertParameters.put("password_hash", Hasher.hash_it(password.getText()));



if (role_type.getValue()!=null) {
  insertParameters.put("role_id",                                 // not null
          oracleConnector.getIdByName("role", role_type.getValue().toString()).toString()
  );
}else {insertParameters.put("role_id","");}



if ( Department.getValue()!=null) {
    insertParameters.put("department_id",
            oracleConnector.getIdByName("department" , Department.getValue().toString()).toString()
    );
} else{insertParameters.put("department","");}




if ( supervisor.getValue()!=null) {
    insertParameters.put("supervisor_id",
            oracleConnector.getIdByName("worker_user" , supervisor.getValue().toString()).toString()
    );
} else{insertParameters.put("supervisor_id","");}



    // verification for missing args
    String[] keys = insertParameters.keySet().toArray(new String[0]);
    String value;
    for (String key : keys) {

      if (insertParameters.get(key) != null){value =insertParameters.get(key).toString();}
      else {value="";}

      if(value=="" || value.isEmpty()){// missing arg
        JOptionPane.showMessageDialog(null, "Fill all the inputs fields", "Missing Parameters", JOptionPane.WARNING_MESSAGE);
        return;
      }
    }





    System.out.println(insertParameters);




    oracleConnector.insertWorkerUser(insertParameters);



  }

  private static final SecureRandom RANDOM = new SecureRandom();
  private static final int PASSWORD_LENGTH = 8;
  private static final int ASCII_START = 33;
  private static final int ASCII_END = 126;  //'!'  to '~' characters

  public static String generatePassword() {
    StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

    for (int i = 0; i < PASSWORD_LENGTH; i++) {
      int randomAscii = ASCII_START + RANDOM.nextInt(ASCII_END - ASCII_START + 1);
      password.append((char) randomAscii);
    }

    return password.toString();

  }

  public void make_password() {
    password.setText(generatePassword());
  }


  @FXML
  public void insert_theme_Controller() {
    HashMap<String, Object> themeData = new HashMap<>();

    int newThemeId = oracleConnector.getMaxId("theme", "theme_id") + 1;

    // Put the theme_id in the HashMap
    themeData.put("theme_id", newThemeId);

    // Get the input values from the UI components and put them in the HashMap
    themeData.put("theme_name", theme_name.getText());
    themeData.put("description", description.getText());

    // Retrieve the department ID based on the selected department name
    String departmentName = Theme_department.getValue();
    if (departmentName != null && !departmentName.isEmpty()) {
      Integer departmentId = oracleConnector.getIdByName("department", departmentName);
      themeData.put("department_id", departmentId);
    }
    oracleConnector.insertTheme(themeData);
  }

  @FXML
  public void departmentInsertController() {
    HashMap<String, Object> departmentData = new HashMap<>();
    int newDepartmentId = oracleConnector.getMaxId("department", "department_id") + 1;
    departmentData.put("department_id", newDepartmentId);
    departmentData.put("location", location.getText());
    departmentData.put("department_name", department_name.getText());
    departmentData.put("department_description", department_description.getText());
    departmentData.put("fax", departemnt_fax.getText());
    oracleConnector.insertDepartment(departmentData);
  }




  @FXML
  public void departmentSearchController() {
    ResultDepartmentPool.getChildren().clear();

    Map<String, String> filters = new HashMap<>();

    if (location.getText() != null && !location.getText().isEmpty()) {
      filters.put("location", location.getText());
    }

    if (department_name.getText() != null && !department_name.getText().isEmpty()) {
      filters.put("department_name", department_name.getText());
    }

    if (department_description.getText() != null && !department_description.getText().isEmpty()) {
      filters.put("department_description", department_description.getText());
    }

    if (departemnt_fax.getText() != null && !departemnt_fax.getText().isEmpty()) {
      filters.put("fax", departemnt_fax.getText());
    }

    List<Map<String, Object>> departmentData = oracleConnector.departmentSearch(filters);

    for (Map<String, Object> department : departmentData) {
      String name = department.get("department_name") != null ? department.get("department_name").toString() : "Unknown";
      String departmentString = department.toString();
      addToDepartmentPool(name, departmentString);
    }
  }


  public static String sendConstraint(){
    return labelText;
  }

}
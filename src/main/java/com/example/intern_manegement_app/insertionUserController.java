package com.example.intern_manegement_app;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;

import static com.example.intern_manegement_app.toolkit.*;

public class insertionUserController implements Initializable {

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
  @FXML private ChoiceBox<String> theme_responsible;
  // department part
  @FXML private TextField location;
  @FXML private TextField department_name;
  @FXML private TextArea department_description;
  @FXML private TextField department_fax;
  //  Statistics part
  @FXML private PieChart theme_chart;
  @FXML private PieChart intern_chart;
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    ArrayList<String> roles       = oracleConnector.getSelectableOptions("role", "role_name");
    ArrayList<String> departments = oracleConnector.getSelectableOptions("department", "department_name");
    ArrayList<String> supervisors = oracleConnector.getSelectableOptions("worker_user", "full_name");

          Department.setItems(FXCollections.observableArrayList(departments));
    Theme_department.setItems(FXCollections.observableArrayList(departments));
          supervisor.setItems(FXCollections.observableArrayList(supervisors));
           role_type.setItems(FXCollections.observableArrayList(roles));
           theme_responsible.setItems(FXCollections.observableArrayList(supervisors));

    // statics part pie charts
    ObservableList<PieChart.Data> pieChartDataThemes = FXCollections.observableArrayList(
            new PieChart.Data("Department 3", 33),
            new PieChart.Data("Department 2", 20),
            new PieChart.Data("Department 1", 17),
            new PieChart.Data("Department 1", 30));
    theme_chart.setData(pieChartDataThemes);
    theme_chart.setTitle("Themes by Departments");


    ObservableList<PieChart.Data> pieChartDataInterns = FXCollections.observableArrayList(
                    new PieChart.Data("Department 3", 33),
                    new PieChart.Data("Department 2", 20),
                    new PieChart.Data("Department 1", 17),
                    new PieChart.Data("Department 1", 30));
    intern_chart.setData(pieChartDataInterns);
    intern_chart.setTitle("Themes by Interns");

  }

  @FXML
  public void refresh() {
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
    department_fax.clear();
    Department.getSelectionModel().clearSelection();
    supervisor.getSelectionModel().clearSelection();
    role_type.getSelectionModel().clearSelection();
    Theme_department.getSelectionModel().clearSelection();
    ResultPool.getChildren().clear();
  }
  
//  todo : put "addtopools" this into a separate  class with the polls as argument
  @FXML
  public void addToThemePool(String title, String info) {
    Label innerLabel = new Label(
            preProcess(formatString(info))

    );
    innerLabel.setWrapText(true);
    AnchorPane.setLeftAnchor(innerLabel, 0.0);
    AnchorPane.setRightAnchor(innerLabel, 0.0);

    AnchorPane content = new AnchorPane();
    innerLabel.prefWidthProperty().bind(content.widthProperty());
    content.getChildren().add(innerLabel);


    Button buttonDelete = new Button("Delete");
    Button buttonUpdate = new Button("Update");

    buttonDelete.setOnAction(event -> {
      labelText = formatString(info);
      Map<String, String> params = toolkit.parseText(labelText);
      oracleConnector.deleteTheme(Integer.parseInt(params.get("theme_id")),params.get("theme_name"));
    });
    buttonUpdate.setOnAction(event -> {
      labelText = formatString(info);
      Map<String, String> params = toolkit.parseText(labelText);
//      oracleConnector.updateTheme(Integer.parseInt(params.get("theme_id")),params.get("theme_name"));
    });


    HBox buttonBox = new HBox(10, buttonDelete,buttonUpdate);
    buttonBox.setAlignment(Pos.CENTER_RIGHT);


    VBox vbox = new VBox(10); 
    vbox.getChildren().addAll(content, buttonBox);
    vbox.setAlignment(Pos.CENTER); 

    TitledPane row = new TitledPane();
    row.setText(title);
    row.setContent(vbox);
    row.setExpanded(false);

    ResultThemePool.getChildren().add(row);
  }
  
  @FXML
  public void addToWorkerUserPool(String Title, String Info) {


    Label innerLabel = new Label(preProcess(formatString(Info)));
    innerLabel.setWrapText(true);
    AnchorPane.setLeftAnchor(innerLabel, 0.0);
    AnchorPane.setRightAnchor(innerLabel, 0.0);

    Button updateButton = new Button("Update");
    Button deleteButton = new Button("Delete");

    deleteButton.setOnAction(event -> {
      Map<String, String> params= toolkit.parseText(formatString(Info));
      oracleConnector.deleteWorkerUser(Integer.parseInt(params.get("user_id")),params.get("full_name") );
    });


    updateButton.setOnAction(event -> {


      labelText = formatString(Info);
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

    AnchorPane content = new AnchorPane();
    innerLabel.prefWidthProperty().bind(content.widthProperty());
    content.getChildren().add(innerLabel);


    HBox buttonBox = new HBox(10, updateButton,deleteButton);
    buttonBox.setAlignment(Pos.CENTER_RIGHT);

    VBox vbox = new VBox(10);
    vbox.getChildren().addAll(content, buttonBox);
    vbox.setAlignment(Pos.CENTER);

    TitledPane row = new TitledPane();
    row.setText(Title);
    row.setContent(vbox);
    row.setExpanded(false);

    ResultPool.getChildren().add(row);
  }
  
  @FXML
  public void addToDepartmentPool(String title, String info) {
    Label innerLabel = new Label(preProcess(formatString(info)))
            ;
    innerLabel.setWrapText(true);
    AnchorPane.setLeftAnchor(innerLabel, 0.0);
    AnchorPane.setRightAnchor(innerLabel, 0.0);

    AnchorPane content = new AnchorPane();
    innerLabel.prefWidthProperty().bind(content.widthProperty());
    content.getChildren().add(innerLabel);



    Button deleteButton = new Button("Delete");
    Button updateButton = new Button("Update");

    deleteButton.setOnAction(event -> {

      labelText = formatString(info);
      Map<String, String> params= toolkit.parseText(labelText);
      oracleConnector.deleteDepartment(Integer.parseInt(params.get("department_id")),params.get("department_name") );
    });


    HBox buttonBox = new HBox(10, updateButton,deleteButton);
    buttonBox.setAlignment(Pos.CENTER_RIGHT);


    VBox vbox = new VBox(10);
    vbox.getChildren().addAll(content, buttonBox);
    vbox.setAlignment(Pos.CENTER);

    TitledPane row = new TitledPane();
    row.setText(title);
    row.setContent(vbox);
    row.setExpanded(false);
    ResultDepartmentPool.getChildren().add(row);
  }

  @FXML
  public void searchWorkerUserController() {
    ResultPool.getChildren().clear();

    Map<String, String> filters = new HashMap<>();
    if (!full_name.getText().isEmpty()) {
      filters.put("full_name", full_name.getText());
    }

    if (!age.getText().isEmpty()) {
      filters.put("age", age.getText());
    }

    if (!email_address.getText().isEmpty()) {
      filters.put("email_address", email_address.getText());
    }

    if (!phone_number.getText().isEmpty()) {
      filters.put("phone_number", phone_number.getText());
    }

    if (!fax_number.getText().isEmpty()) {
      filters.put("fax_number", fax_number.getText());
    }

    if (!username.getText().isEmpty()) {
      filters.put("username", username.getText());
    }

    if (Department.getValue() != null) {
      filters.put("department_id", String.valueOf(oracleConnector.getIdByName("department", Department.getValue())));
    }

    if (role_type.getValue() != null) {
      filters.put("role_id", String.valueOf(oracleConnector.getIdByName("role", role_type.getValue())));
    }

    if (supervisor.getValue() != null) {
      filters.put("supervisor_id", String.valueOf(oracleConnector.getIdByName("worker_user_super", supervisor.getValue())));
    }

    List<Map<String, Object>> workerUserData = oracleConnector.searchWorkerUser(filters);

    for (Map<String, Object> workerUser : workerUserData) {
      String name = workerUser.get("full_name") != null ? workerUser.get("full_name").toString() : "Unknown";
      String workerUserString = workerUser.toString();
      addToWorkerUserPool(name, workerUserString);
    }
  }

  @FXML
  public void searchDepartmentController() {
    ResultDepartmentPool.getChildren().clear();
    Map<String, String> filters = new HashMap<>();

    if (location.getText() != null && !location.getText().isEmpty()) {
      filters.put("location", location.getText());}
    if (department_name.getText() != null && !department_name.getText().isEmpty()) {
      filters.put("department_name", department_name.getText());}
    if (department_description.getText() != null && !department_description.getText().isEmpty()) {
      filters.put("department_description", department_description.getText());}
    if (department_fax.getText() != null && !department_fax.getText().isEmpty()) {
      filters.put("fax", department_fax.getText());}


    List<Map<String, Object>> departmentData = oracleConnector.searchDepartment(filters);

    for (Map<String, Object> department : departmentData) {
      String name = department.get("department_name") != null ? department.get("department_name").toString() : "Unknown";
      String departmentString = department.toString();
      addToDepartmentPool(name, departmentString);
    }
  }

  @FXML
  public void searchThemeController() {
    ResultThemePool.getChildren().clear();

    HashMap<String, Object> filters = new HashMap<>();

    if (theme_name.getText() != null && !theme_name.getText().isEmpty()) {
      filters.put("theme_name", theme_name.getText());}

    if (description.getText() != null && !description.getText().isEmpty()) {
      filters.put("description", description.getText());}

    if (Theme_department.getValue() != null) {
      filters.put("department_id", String.valueOf(oracleConnector.getIdByName("department", Theme_department.getValue())));}

    List<Map<String, Object>> themeData = oracleConnector.searchTheme(filters);

    for (Map<String, Object> theme : themeData) {
      String name = theme.get("theme_name") != null ? theme.get("theme_name").toString() : "Unknown";
      String themeString = theme.toString();
      addToThemePool(name, themeString);
    }
  }


  @FXML
  public void insertWorkerUserController() throws SQLException, NoSuchAlgorithmException {

    Map<String, String> insertParameters = new HashMap<>();

    insertParameters.put("user_id", String.valueOf(oracleConnector.getMaxId("worker_user","user_id")+1));
    insertParameters.put("full_name", full_name.getText());
    insertParameters.put("username", username.getText());
    insertParameters.put("email_address", email_address.getText());
    insertParameters.put("phone_number", phone_number.getText());
    insertParameters.put("fax_number", fax_number.getText());
    insertParameters.put("password_hash", hashIt(password.getText()));


if (role_type.getValue()!=null) {
  insertParameters.put("role_id",                                 // not null
          oracleConnector.getIdByName("role", role_type.getValue().toString()).toString()
  );
}else {insertParameters.put("role_id","");}

if ( Department.getValue()!=null) {
    insertParameters.put("department_id",
            oracleConnector.getIdByName("department" , Department.getValue().toString()).toString());
} else{insertParameters.put("department","");}


if ( supervisor.getValue()!=null) {
    insertParameters.put("supervisor_id",
            oracleConnector.getIdByName("worker_user_super" , supervisor.getValue().toString()).toString()
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

    oracleConnector.insertWorkerUser(insertParameters);
  }

  @FXML
  public void insertThemeController() {
    HashMap<String, Object> themeData = new HashMap<>();
    int newThemeId = oracleConnector.getMaxId("theme", "theme_id") + 1;
    themeData.put("theme_id", newThemeId);
    themeData.put("theme_name", theme_name.getText());
    themeData.put("description", description.getText());

    String responsibleName =theme_responsible.getValue();
    if(responsibleName != null && !responsibleName.isEmpty()){
      Integer responsibleID= oracleConnector.getIdByName("worker_user_user", responsibleName);
      themeData.put("responsible",responsibleID);}

    String departmentName = Theme_department.getValue();
    if (departmentName != null && !departmentName.isEmpty()) {
      Integer departmentId = oracleConnector.getIdByName("department", departmentName);
      themeData.put("department_id", departmentId);}

    oracleConnector.insertTheme(themeData);
  }

  @FXML
  public void insertDepartmentController() {
    HashMap<String, Object> departmentData = new HashMap<>();
    int newDepartmentId = oracleConnector.getMaxId("department", "department_id") + 1;
    departmentData.put("department_id", newDepartmentId);
    departmentData.put("location", location.getText());
    departmentData.put("department_name", department_name.getText());
    departmentData.put("department_description", department_description.getText());
    departmentData.put("fax", department_fax.getText());
    oracleConnector.insertDepartment(departmentData);
  }

  
  public static String sendConstraint(){return labelText;}
  public void makePassword() {password.setText(generatePassword());}

}
package com.example.intern_manegement_app;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class loginController {
  oracleConnector Connection = new oracleConnector();
  @FXML private TextField username;
  @FXML private PasswordField password;
  @FXML private AnchorPane loginP;
  Stage loginWindow ;



  public void onLoginClick() throws NoSuchAlgorithmException {
    loginWindow = (Stage) loginP.getScene().getWindow();

    if (Connection.login(username.getText(),


            Hasher.hash_it(
                    password.getText()
            )
     )


    ) {
      JOptionPane.showMessageDialog(null, "Access Granted. ", "Information", JOptionPane.INFORMATION_MESSAGE);

      //     admin
      if (Connection.isAdmin(username.getText(),  Hasher.hash_it(
              password.getText()
      ))) {

        try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user_insertion.fxml"));
          Parent root = fxmlLoader.load();
          Stage stage = new Stage();
          stage.setScene(new Scene(root));


          stage.show();
          System.out.println(Connection.isAdmin(username.getText(),  Hasher.hash_it(
                  password.getText()
          )));



          loginWindow.close();



        } catch (IOException e) {
          e.printStackTrace();

        }
      } else if (Connection.isChief(username.getText(),  Hasher.hash_it(
              password.getText()
      )) ){
        try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("decision_Intern.fxml"));
          Parent root = fxmlLoader.load();
          Stage stage = new Stage();
          stage.setTitle("The Chief of Department");
          stage.setScene(new Scene(root));
          stage.show();
          System.out.println(Connection.isAdmin(username.getText(),  Hasher.hash_it(
                  password.getText()
          )));

          loginWindow.close();


        } catch (IOException e) {
          e.printStackTrace();
        }

      } else {
        try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("intern_insertion.fxml"));
          Parent root = fxmlLoader.load();

          interInsertionController controller = fxmlLoader.getController();

          int accessLevel =oracleConnector.getWorkerUserId(username.getText(), Hasher.hash_it(
                  password.getText()
          ));
          System.out.println(accessLevel);

          controller.setAccessLevel(accessLevel);
          Stage stage = new Stage();
          stage.setScene(new Scene(root));
          stage.setTitle("The Clerk");
          stage.show();
          System.out.println(Connection.isAdmin(username.getText(),  Hasher.hash_it(
                  password.getText()
          )));



          loginWindow.close();


        } catch (IOException e) {
          e.printStackTrace();
          // Handle any potential IOException here
        }
      }
    }else{
      JOptionPane.showMessageDialog(null, "Invalid username or password. ", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
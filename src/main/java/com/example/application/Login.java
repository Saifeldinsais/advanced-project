package com.example.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Button button_signup , button_login;

    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField pf_password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String r =  DB.logInUser(event,tf_username.getText(),pf_password.getText());
                System.out.println(r);
                if (r.equals("1")) {
                    System.out.println("---------------------------------------------------------------");
                    DB.changeScene(event, "/com/example/User.fxml","Sign Up!");
                }
            }
        });
        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DB.changeScene(event, "/com/example/Signup.fxml","Sign Up!");
            }
        });
    }
}

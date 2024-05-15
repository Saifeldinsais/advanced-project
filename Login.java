package application;

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
                boolean loggedIn = DB.logInUser(event,tf_username.getText(),pf_password.getText());
                if(loggedIn) {
                	User current = DB.fetchUserInformation(tf_username.getText());
                	DB.setLoggedInUsername(tf_username.getText());
                	SessionManager.setCurrentUser(current);
                	DB.changeScene(event, "/User.fxml", "User Profile");
                }
            }
        });
        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DB.changeScene(event, "/Signup.fxml","Sign Up!");
            }
        });
    }
}

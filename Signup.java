package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Signup implements Initializable {
    @FXML
    private Button button_signup, button_login;
    @FXML
    private TextField tf_firstname, tf_lastname, tf_username;
    @FXML
    private PasswordField pf_password;
    @FXML
    private DatePicker date_birthdate;
    @FXML
    private RadioButton rb_male, rb_female;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_male.setToggleGroup(toggleGroup);
        rb_female.setToggleGroup(toggleGroup);
        rb_male.setSelected(true);

        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
                LocalDate birtdate = date_birthdate.getValue();
                if(!tf_firstname.getText().isEmpty() && !tf_lastname.getText().isEmpty() && !toggleName.isEmpty() && !tf_username.getText().isEmpty() && !pf_password.getText().isEmpty()){
                        DB.signUpUser(event, tf_firstname.getText(), tf_lastname.getText(), birtdate, toggleName, tf_username.getText(), pf_password.getText());
                        DB.changeScene(event, "/Login.fxml","Log In!");
                } else {
                    System.out.println("Please fill in all information!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();
                }
            }
        });

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DB.changeScene(event, "/Login.fxml","Log In!");
            }
        });
    }
}

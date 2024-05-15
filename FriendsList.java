package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FriendsList {

    @FXML
    private Label bio_label;

    @FXML
    private Button chat_B;

    @FXML
    private Button feed_B;

    @FXML
    private Button follow_B;

    @FXML
    private Button friends_B;

    @FXML
    private Label message_label;

    @FXML
    private Label name_label;

    @FXML
    private Label name_label1;

    @FXML
    private TextField search_TF;

    @FXML
    private Button sign_out_B;

    @FXML
    private Button user_B;

    @FXML
    private TextArea yourlist_TF;

    @FXML


    private String currentUserName;


    @FXML
    private void initialize() {
        currentUserName = SessionManager.getCurrentUser().getUsername();
        displayFriends();
    }


    @FXML
    private void Addfriend_button(ActionEvent event) {
        String username = search_TF.getText().trim();
        if (!username.isEmpty()) {
          String friend_username = DB.findUsername(username);
            if (!friend_username.equals("-1")) {
                if (DB.addFriend(currentUserName, friend_username)) {
                    message_label.setText("User has been added successfully.");
                    displayFriends();
                } else {
                    message_label.setText("User is already your friend.");
                }
            } else {
                message_label.setText("User not found.");
            }
        }
    }

    @FXML
    private void displayFriends() {
        List<String> friends = DB.getFriends(currentUserName);
        StringBuilder friendListText = new StringBuilder();

        for (String friend : friends) {
            friendListText.append(friend).append("\n");
        }
        yourlist_TF.setText(friendListText.toString());
    }


    @FXML
    private void viewUserProfile(ActionEvent event){
        System.out.println("*********************");
        DB.changeScene(event, "/User.fxml", "My Profile");
    }
    
    @FXML
    private void viewFeed(ActionEvent event){
        System.out.println("");
        DB.changeScene(event, "/Feed.fxml", "Your Friends");
    }
    
}
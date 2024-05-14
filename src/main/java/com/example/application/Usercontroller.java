package com.example.application;

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
import java.util.ResourceBundle;

public class Usercontroller implements Initializable {
    //@FXML
   // private Button myposts_B, feed_B, friends_B, sign_out_B, chat_B;
    @FXML
    private Button change_pfp_B, bio_B;
    @FXML
    private Label bio_label, name_label, username_label;
    @FXML
    private ImageView profilepic;
    @FXML
    private TextArea bio_TF;

    private String imagePath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User currentUser = SessionManager.getCurrentUser();

        if (currentUser != null) {
            name_label.setText(currentUser.getFirstName() + ' ' + currentUser.getLastName());
            username_label.setText(currentUser.getUsername()); 
            bio_label.setText(currentUser.getBio()); 
            imagePath = currentUser.getPfpPath();
            Image pfp = new Image(imagePath);
            profilepic.setImage(pfp);

        }
    }


    public void change_Bio(ActionEvent event) {
        bio_TF.setVisible(!bio_TF.isVisible()); //htwreena eltext area
        String newBio = bio_TF.getText();
        DB.save_bio(newBio); // y save elnew bio feldatabase
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            currentUser.setBio(newBio); //load new bio
        }
    }

     public void change_pfp(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif") //elmfrood y5tar file format mn dol
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            String pfpPath = selectedFile.getAbsolutePath().replace("\\", "/"); //htgeeb elpath bta3 elselected file
            DB.save_pfp_path(pfpPath);
            Image image = new Image(selectedFile.toURI().toString());
            profilepic.setImage(image); //ht3ml show lelimage elgdeeda
        }
    }
}

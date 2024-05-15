package com.example.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

public class Usercontroller implements Initializable {
    @FXML
    private Button user_B, feed_B, friends_B, sign_out_B, chat_B;
    @FXML
    public Button change_pfp_B, bio_B;
    @FXML
    private Label bio_label, name_label, username_label;
    @FXML
    private ImageView profilepic;
    @FXML
    private TextField bio_TF;

    private String imagePath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User currentUser = SessionManager.getCurrentUser();

        if (currentUser != null) {
            name_label.setText(currentUser.getFirstName() + ' ' + currentUser.getLastName());
            username_label.setText(currentUser.getUsername());
            System.out.println("THIS IS THE BIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
            System.out.println(currentUser.getBio());
            bio_label.setText(currentUser.getBio());

            try {
                System.out.println(SessionManager.getCurrentUser().getPfpPath());
                System.out.println("---------------------------------------------------------------");
                URL url = new URL("file:///" + SessionManager.getCurrentUser().getPfpPath().replace("\\", "/"));

                BufferedImage bufferedImage = ImageIO.read(url);

                if (bufferedImage != null) {
                    System.out.println("Image loaded successfully.");
                    InputStream is = url.openStream();
                    Image fxImage = new Image(is);
                    profilepic.setImage(fxImage);
                } else {
                    System.out.println("Failed to load image.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void change_Bio(ActionEvent event) {
        String newBio = bio_TF.getText();
        DB.save_bio(newBio); // y save elnew bio feldatabase
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            currentUser.setBio(newBio); // load new bio
            bio_label.setText(newBio); // Update the bio label in the GUI
            System.out.println("Bio updated in GUI and database."); // Debug statement
        }
    }

    @FXML
    public void change_pfp(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            DB.save_pfp(selectedFile);
            Image image = new Image(selectedFile.toURI().toString());
            profilepic.setImage(image); // Update the profile picture in the GUI
        }
    }

    @FXML
    private void viewfriendslist(ActionEvent event) {
        System.out.println("*********************");
        DB.changeScene(event, "/com/example/FriendsList.fxml", "Your Friends");
    }
}

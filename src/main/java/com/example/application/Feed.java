package com.example.application;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;



public class Feed implements Initializable{
	
	@FXML
	private Button button_addpost;
	@FXML
	private TextArea ta_postcontent;
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		button_addpost.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(!ta_postcontent.getText().isEmpty()) {
				DB.createPost(event, ta_postcontent.getText(), DB.getLoggedInUsername());
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Please enter content to create post!");
				}
				
			}
			
		});
	}

}

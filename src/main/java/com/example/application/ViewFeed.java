package com.example.application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewFeed implements Initializable {
    
    @FXML
    private TextArea ta_postcontent, ta_addcomment;
    @FXML
    private Label label_content, label_viewcomment, label_username, label_likecount;
    @FXML
    private Button button_addpost, button_next, button_back, button_like, button_addcomment;
    @FXML
    private ImageView iv_pfp;
    
    private List<Post> posts;
    private int currentPost;
    private int userId;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
		button_addpost.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(!ta_postcontent.getText().isEmpty()) {
				DB.createPost(event, ta_postcontent.getText(), DB.getLoggedInUsername());
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Please enter content to create post!");
                    System.out.println("+_+_+_++_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_");
				}
				
			}
			
		});
        userId = SessionManager.getUserId();
        posts = DB.getPosts();
        currentPost = 0;
        
        if (!posts.isEmpty()) {
            System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////////");
            System.out.println(posts.get(currentPost));
            display(posts.get(currentPost));
        }

        button_like.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent event) {
                Post currentPostObj = posts.get(currentPost);
                int postId = currentPostObj.getPostId();

                if (DB.isPostLiked(userId, postId)) {
                    DB.decreaseLike(postId);
                    DB.removeLike(userId, postId);
                    button_like.setText("Like");
                    button_like.setStyle("-fx-background-color: transparent;");
                } else {
                    DB.increaseLike(postId);
                    DB.addLike(userId, postId);
                    button_like.setText("Unlike");
                    button_like.setStyle("-fx-background-color: red;");
                }

                display(currentPostObj);
            }
        });
        
        button_next.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
		        if (currentPost < posts.size()) {
		            currentPost++;
		            display(posts.get(currentPost));
		        }
				
			} 	
        	
        });
        button_back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(currentPost >0) {
					currentPost--;
					display(posts.get(currentPost));
				}
				
			}
        		
        });
        
        button_addcomment.setOnAction(new EventHandler<ActionEvent>()  {

			@Override
			public void handle(ActionEvent arg0) {
				String comment = ta_addcomment.getText();
				if(!comment.isEmpty()) {
					Post currentPostObj = posts.get(currentPost);
					DB.addComment(userId, currentPostObj.getPostId(), comment);
					
					String Username = DB.getUserName(userId);
					
					ta_addcomment.appendText("@" + Username +" : " + comment + "\n");
					
		            display(posts.get(currentPost));
		            ta_addcomment.clear();
				}
				
			}

        });
    }

    
    private void display(Post post) {
        String userName = DB.getLoggedInUsername();
        System.out.println("Author: " + userName); // Debug statement
        // Image pfp = DB.getPfp(post.getUserId());
        //int likeCount = DB.getLikes(post.getPostId());

        label_username.setText(userName);
        label_content.setText(post.getContent());
        //iv_pfp.setImage(pfp);
        //label_likecount.setText(String.valueOf(likeCount));

        if (DB.isPostLiked(userId, post.getPostId())) {
            button_like.setText("Unlike");
            button_like.setStyle("-fx-background-color: red;");
        } else {
            button_like.setText("Like");
            button_like.setStyle("-fx-background-color: transparent;");
        }

        List<String> comments = DB.getComments(post.getPostId());
        ta_addcomment.clear();
        for (String comment : comments) {
            ta_addcomment.appendText(comment + "\n");
        }
    }

    @FXML
    private void viewUserProfile(ActionEvent event){
        System.out.println("*********************");
        DB.changeScene(event, "/com/example/User.fxml", "My Profile");
    }

    @FXML
    private void viewfriendslist(ActionEvent event) {
        System.out.println("*********************");
        DB.changeScene(event, "/com/example/FriendsList.fxml", "Your Friends");
    }

    @FXML
    private void viewlogin(ActionEvent event) {
        System.out.println("*********************");
        DB.changeScene(event, "/com/example/Login.fxml", "What's new");
    }
}

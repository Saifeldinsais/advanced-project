package com.example.application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;

public class DB {

    public static void signUpUser(ActionEvent event, String FirstName, String LastName, LocalDate BirthDate, String Gender, String UserName, String Password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckIfUserExists = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userinfo", "root", "Tnsmt#2004");
            psCheckIfUserExists = connection.prepareStatement("SELECT * FROM users WHERE UserName = ?");
            psCheckIfUserExists.setString(1, UserName);
            resultSet = psCheckIfUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("User with this username already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You can't use this username.");
                alert.show();
            } else{
                psInsert = connection.prepareStatement("INSERT INTO users (FirstName,LastName,BirthDate,Gender,UserName,Password) VALUES (?, ?, ?, ?, ?, ?)");
                psInsert.setString(1, FirstName);
                psInsert.setString(2, LastName);
                psInsert.setDate(3, Date.valueOf(BirthDate));
                psInsert.setString(4, Gender);
                psInsert.setString(5, UserName);
                psInsert.setString(6, Password);
                psInsert.executeUpdate();
                System.out.println("User Signed Up Successfully!");
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckIfUserExists != null){
                try {
                    psCheckIfUserExists.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try {
                    psInsert.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static String logInUser(ActionEvent event, String UserName, String Password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userinfo", "root", "Tnsmt#2004");
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE UserName = ?");
            preparedStatement.setString(1, UserName);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided user information is incorrect!");
                alert.show();
            } else{
                while(resultSet.next()) {
                    System.out.println("1");
                    String retrievedPassword = resultSet.getString("Password");
                    if (retrievedPassword.equals(Password)) {
                        System.out.println("2");
                        System.out.println("User logged in successfully!");
                        SessionManager.setCurrentUser(new User(resultSet.getString("Firstname"), resultSet.getString("LastName"), UserName));
                        //changeScene(event, "/com/example/User.fxml","User Profile!");
                        return "1";
                    }
                    else {
                        System.out.println("Password did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided user information is incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public static void changeScene (ActionEvent event, String fxmlfile, String title){
        Parent root = null;
        try{
            System.out.println(fxmlfile);
            System.out.println(DB.class.getResource(fxmlfile));
            root = FXMLLoader.load(DB.class.getResource(fxmlfile));

        } catch (IOException e){
        	e.printStackTrace();
        }
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        System.out.println("4");
        stage.setTitle(title);
        stage.setScene(new Scene(root, 800,600));
        stage.show();
    }


//saif's User database connection----------------------------------------------------------------------------------------------------------------------------

// public static void save_bio(String newbio) {
    
//     String url = "jdbc:mysql://localhost:3306/userinfo";
//     String username = "root";
//     String password = "Tnsmt#2004";
    

//     try {
        
//         Connection connection = DriverManager.getConnection(url, username, password);
//         String sql = "UPDATE users SET bio = ? WHERE UserName = ?";  // byktb elsql query
//         PreparedStatement preparedStatement = connection.prepareStatement(sql);
//         preparedStatement.setString(1, newbio);
//         preparedStatement.setString(2, SessionManager.getCurrentUser().getUsername()); // btet2kd mn elusername els7
//         preparedStatement.executeUpdate();
//         connection.close();
//     } catch (SQLException e) {
//         e.printStackTrace();
//     }
// }
    

public static void save_bio(String newBio) {
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username = "root";
    String password = "Tnsmt#2004";

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
        String sql = "UPDATE users SET bio = ? WHERE UserName = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, newBio);
        preparedStatement.setString(2, SessionManager.getCurrentUser().getUsername());
        int rowsUpdated = preparedStatement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Bio updated successfully in database.");
        } else {
            System.out.println("Failed to update bio in database.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



 public static void save_pfp(File pfpFile) {
        String url = "jdbc:mysql://localhost:3306/userinfo";
        String username = "root";
        String password = "Tnsmt#2004";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "UPDATE users SET pfp = ? WHERE UserName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            FileInputStream fis = new FileInputStream(pfpFile);
            preparedStatement.setBinaryStream(1, fis, (int) pfpFile.length());
            preparedStatement.setString(2, SessionManager.getCurrentUser().getUsername());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }


 public static Image getpfp() {
        String url = "jdbc:mysql://localhost:3306/userinfo";
        String username = "root";
        String password = "Tnsmt#2004";
        Image image = null;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT pfp FROM users WHERE UserName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, SessionManager.getCurrentUser().getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                InputStream is = resultSet.getBinaryStream("pfp");
                if (is != null) {
                    image = new Image(is);
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return image;
    }
}

package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;


// for database connections!!!

public class DB {

    public static void signUpUser(ActionEvent event, String FirstName, String LastName, LocalDate BirthDate, String Gender, String UserName, String Password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckIfUserExists = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userinfo", "root", "%(_=ZnL2!WdW");
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

    public static void logInUser(ActionEvent event, String UserName, String Password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userinfo", "root", "%(_=ZnL2!WdW");
            preparedStatement = connection.prepareStatement("SELECT Password FROM users WHERE UserName = ?");
            preparedStatement.setString(1, UserName);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided user information is incorrect!");
                alert.show();
            } else{
                while(resultSet.next()) {
                    String retrievedPassword = resultSet.getString("Password");
                    if (retrievedPassword.equals(Password)) {
                        System.out.println("User logged in successfully!");
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
    }

    public static void changeScene (ActionEvent event, String fxmlfile, String title){
        Parent root = null;
        try{
            root = FXMLLoader.load(DB.class.getResource(fxmlfile));

        } catch (IOException e){
        	e.printStackTrace();
        }
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 800,600));
        stage.show();
    }
}

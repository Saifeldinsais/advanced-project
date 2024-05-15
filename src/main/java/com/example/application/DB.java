package com.example.application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DB {
	
	 private static String loggedInUsername;

	 public static int signUpUser(ActionEvent event, String FirstName, String LastName, LocalDate BirthDate, String Gender, String UserName, String Password) {
		    Connection connection = null;
		    PreparedStatement psInsert = null;
		    PreparedStatement psCheckIfUserExists = null;
		    ResultSet resultSet = null;
		    int userID = -1;
		    
		    try {
		        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userinfo", "root", "Tnsmt#2004");
		        psCheckIfUserExists = connection.prepareStatement("SELECT * FROM users WHERE UserName = ?");
		        psCheckIfUserExists.setString(1, UserName);
		        resultSet = psCheckIfUserExists.executeQuery();

		        if (resultSet.isBeforeFirst()) {
		            System.out.println("User with this username already exists!");
		            Alert alert = new Alert(Alert.AlertType.ERROR);
		            alert.setContentText("You can't use this username.");
		            alert.show();
		        } else {
		            psInsert = connection.prepareStatement("INSERT INTO users (FirstName, LastName, BirthDate, Gender, UserName, Password) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		            psInsert.setString(1, FirstName);
		            psInsert.setString(2, LastName);
		            psInsert.setDate(3, Date.valueOf(BirthDate));
		            psInsert.setString(4, Gender);
		            psInsert.setString(5, UserName);
		            psInsert.setString(6, Password);
		            psInsert.executeUpdate();
		            
		            ResultSet generatedKeys = psInsert.getGeneratedKeys();
		            if (generatedKeys.next()) {
		                userID = generatedKeys.getInt(1);
		            }
		            
		            System.out.println("User Signed Up Successfully!");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        if (resultSet != null) {
		            try {
		                resultSet.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		        if (psCheckIfUserExists != null) {
		            try {
		                psCheckIfUserExists.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		        if (psInsert != null) {
		            try {
		                psInsert.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		        if (connection != null) {
		            try {
		                connection.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		    
		    return userID;
		}

    public static boolean logInUser(ActionEvent event, String UserName, String Password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int checker = 0;
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
                checker = -1;
            } else{
                while(resultSet.next()) {
                    String retrievedPassword = resultSet.getString("Password");
                    if (retrievedPassword.equals(Password)) {
                        System.out.println("User logged in successfully!");
                        User user = new User(resultSet.getString("Firstname"), resultSet.getString("LastName"), UserName);
                        user.setBio(resultSet.getString("bio"));
                        SessionManager.setCurrentUser(user);
                        SessionManager.getCurrentUser().setPfpPath(resultSet.getString("pfp"));
                        //changeScene(event, "com/example/User.fxml","User Profile!");
                        checker = 1;
                    }
                    else {
                        System.out.println("Password did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided user information is incorrect!");
                        alert.show();
                        checker = -1;
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
        if (checker == 1) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public static User fetchUserInformation(String username) {
        User user = null;
        String url = "jdbc:mysql://localhost:3306/userinfo";
        String username_ = "root";
        String password = "Tnsmt#2004";

        try (Connection connection = DriverManager.getConnection(url, username_, password)) {
            String sql = "SELECT * FROM users WHERE UserName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userID = resultSet.getInt("ID"); // Retrieve user_id from the result set
                String usernamee = resultSet.getString("UserName");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String bio = resultSet.getString("bio");
                String pfp = resultSet.getString("pfp");

                user = new User(userID,firstName, lastName, usernamee, bio,pfp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
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


//saif's User database connection----------------------------------------------------------------------------------------------------------------------------

    public static void save_bio(String newBio) {
        String url = "jdbc:mysql://localhost:3306/userinfo";
        String username_ = "root";
        String password = "Tnsmt#2004";

        try (Connection connection = DriverManager.getConnection(url, username_, password)) {
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
        String username_ = "root";
        String password = "Tnsmt#2004";

        try (Connection connection = DriverManager.getConnection(url, username_, password)) {
            String sql = "UPDATE users SET pfp = ? WHERE UserName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            FileInputStream fis = new FileInputStream(pfpFile);
            preparedStatement.setString(1, pfpFile.getAbsolutePath().replace("\\", "/"));
            preparedStatement.setString(2, SessionManager.getCurrentUser().getUsername());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static Image getpfp() {
        String url = "jdbc:mysql://localhost:3306/userinfo";
        String username_ = "root";
        String password = "Tnsmt#2004";
        Image image = null;

        try (Connection connection = DriverManager.getConnection(url, username_, password)) {
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
    
    public static String findUsername(String UserName){
        String url = "jdbc:mysql://localhost:3306/userinfo";
        String username_ = "root";
        String password = "Tnsmt#2004";
        try (Connection connection = DriverManager.getConnection(url, username_, password)) {
            String sql = "SELECT UserName FROM users WHERE UserName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, UserName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("UserName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "-1";
    }
    
    public static boolean addFriend(String UserName, String friendUserName){
        String url = "jdbc:mysql://localhost:3306/userinfo";
        String username_ = "root";
        String password = "Tnsmt#2004";

        if (isFriend(UserName, friendUserName)) {
            return false; // Already friends
        }

        try (Connection connection = DriverManager.getConnection(url, username_, password)) {
            String sql = "INSERT INTO friends (UserName, FriendUserName) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, UserName);
            preparedStatement.setString(2, friendUserName);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean isFriend(String UserName, String friendUserName) {
        String url = "jdbc:mysql://localhost:3306/userinfo";
        String username_ = "root";
        String password = "Tnsmt#2004";

        try (Connection connection = DriverManager.getConnection(url, username_, password)) {
            String sql = "SELECT * FROM friends WHERE UserName = ? AND FriendUserName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, UserName);
            preparedStatement.setString(2, friendUserName);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public static List<String> getFriends(String UserName) {
        List<String> friends = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/userinfo";
        String username_ = "root";
        String password = "Tnsmt#2004";


        try (Connection connection = DriverManager.getConnection(url, username_, password)) {
            String sql = "SELECT u.UserName FROM friends f JOIN users u ON f.FriendUserName = u.UserName WHERE f.UserName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, UserName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                friends.add(resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }

public static void createPost (ActionEvent event, String content, String username) {
	
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username_ = "root";
    String password = "Tnsmt#2004";
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    try {
    connection = DriverManager.getConnection(url, username_, password);
    preparedStatement = connection.prepareStatement("INSERT INTO posts (UserName,Content,PostDate) VALUES(?, ?, ?)");
    preparedStatement.setString(1, username);
    preparedStatement.setString(2, content);
    preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
    preparedStatement.executeUpdate();
    connection.close();
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setContentText("Your post has been created successfully!");
    }catch(SQLException e) {
    	e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error occured. Cannot create post!");
    }
}

public static List<Post> getPosts(){
	List<Post> posts = new ArrayList<>();
	
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username_ = "root";
    String password = "Tnsmt#2004";
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    try {
        connection = DriverManager.getConnection(url, username_, password);
        preparedStatement = connection.prepareStatement("SELECT ID, PostID, Content, Like_Count FROM posts");
        resultSet = preparedStatement.executeQuery();
        
        while (resultSet.next()) {
        	int ID = resultSet.getInt("ID");
        	int PostID = resultSet.getInt("PostID");
        	String content = resultSet.getString("Content");
        	int Like_Count = resultSet.getInt("Like_Count");
        	
        	Post post = new Post(PostID, ID, content, Like_Count);
        	posts.add(post);
        }
        connection.close();
        }catch(SQLException e) {
        	e.printStackTrace();
        }
    return posts;
}

public static int getLikes(int PostID) {
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username_ = "root";
    String password = "Tnsmt#2004";
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    int LikeCount = 0;
    
    try {
        connection = DriverManager.getConnection(url, username_, password);
        preparedStatement = connection.prepareStatement("SELECT Like_Count FROM posts WHERE PostID = ?");
        preparedStatement.setInt(1, PostID);
        resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next()) {
        	LikeCount = resultSet.getInt("Like_Count");
        	
        }
        connection.close();
        }catch(SQLException e) {
        	e.printStackTrace();
        }
    return LikeCount;
}

public static List<String> getComments(int PostID) {
    List<String> comments = new ArrayList<>();
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username_ = "root";
    String password = "Tnsmt#2004";
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
    	connection = DriverManager.getConnection(url, username_, password);
        preparedStatement = connection.prepareStatement("SELECT users.UserName, comments.Content FROM comments" + "JOIN users ON comments.ID = users.ID WHERE comments.PostID = ?");
        preparedStatement.setInt(1, PostID);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String UserName = resultSet.getString("UserName");
            String Content = resultSet.getString("Content");
            comments.add(UserName + ": " + Content);
            connection.close();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return comments;
}

public static void addComment(int ID, int PostID, String Content) {
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username_ = "root";
    String password = "Tnsmt#2004";
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    

    try {
    connection = DriverManager.getConnection(url, username_, password);
    preparedStatement = connection.prepareStatement("INSERT INTO comments (ID, PostID, Content) VALUES (?, ?, ?)");
        preparedStatement.setInt(1, ID);
        preparedStatement.setInt(2, PostID);
        preparedStatement.setString(3, Content);
        preparedStatement.executeUpdate();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static String getUserName(int ID) {
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username_ = "root";
    String password = "Tnsmt#2004";
    String authorName = "";

    try (Connection connection = DriverManager.getConnection(url, username_, password)) {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT UserName FROM users WHERE ID = ?");
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            authorName = resultSet.getString("UserName");
        } else {
            System.out.println("User not found with user id: " + ID);
        }
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return authorName;
}

public static Image getPfp(int ID) {
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username_ = "root";
    String password = "Tnsmt#2004";
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    Image pfp = null;

    try {
        connection = DriverManager.getConnection(url, username_, password);
        preparedStatement = connection.prepareStatement("SELECT pfp FROM users WHERE ID = ?");
        preparedStatement.setInt(1, ID);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            byte[] imagePath = resultSet.getBytes("pfp");
            if (imagePath != null) {
                pfp = new Image(new ByteArrayInputStream(imagePath));
            }
        }
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } if (pfp == null) {
        pfp = new Image("user_icon.png");
    }

    return pfp;
}

public static void addLike(int ID, int PostID) {
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username_ = "root";
    String password = "Tnsmt#2004";
    

    try {
    	 Connection connection = DriverManager.getConnection(url, username_, password);
         PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO likes (ID, PostID) VALUES (?, ?)");
        preparedStatement.setInt(1, ID);
        preparedStatement.setInt(2, PostID);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


public static void removeLike(int ID, int PostID) {
	
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username_ = "root";
    String password = "Tnsmt#2004";
    

    try {
   	 Connection connection = DriverManager.getConnection(url, username_, password);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM likes WHERE ID = ? AND PostID = ?");
       preparedStatement.setInt(1, ID);
       preparedStatement.setInt(2, PostID);
       preparedStatement.executeUpdate();
   } catch (SQLException e) {
       e.printStackTrace();
   }
}

public static boolean isPostLiked(int ID, int PostID) {
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username_ = "root";
    String password = "Tnsmt#2004";
    
    
    boolean isLiked = false;

    try (Connection connection = DriverManager.getConnection(url, username_, password)) {
        String sql = "SELECT * FROM user_likes WHERE user_id = ? AND post_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM likes WHERE ID = ? AND PostID = ?");
        preparedStatement.setInt(1, ID);
        preparedStatement.setInt(2, PostID);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            isLiked = true;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return isLiked;
}

public static void increaseLike(int PostID) {
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username_ = "root";
    String password = "Tnsmt#2004";
    
    try (Connection connection = DriverManager.getConnection(url, username_, password)) {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE posts SET Like_Count = Like_Count + 1 WHERE PostID = ?");
        preparedStatement.setInt(1, PostID);
        ResultSet resultSet = preparedStatement.executeQuery();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


public static void decreaseLike (int PostID) {
    String url = "jdbc:mysql://localhost:3306/userinfo";
    String username_ = "root";
    String password = "Tnsmt#2004";
    
    try (Connection connection = DriverManager.getConnection(url, username_, password)) {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE posts SET Like_Count = Like_Count - 1 WHERE PostID = ?");
        preparedStatement.setInt(1, PostID);
        ResultSet resultSet = preparedStatement.executeQuery();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}

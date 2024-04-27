import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Scanner;
public class Login_SignUp {

    static HashMap <String,String> userInfo = new HashMap<>();

public static void SignUp(Scanner in){

    System.out.println("Please enter your first name: ");
    String first = in.nextLine();
    System.out.println("Please enter your last name: ");
    String last = in.nextLine();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate birthDate = null;
    boolean valid = false;
    while(!valid){
        try{
            System.out.println("Please enter your birthdate (yyyy-MM-dd): ");
            String date = in.nextLine();
            birthDate = LocalDate.parse(date,formatter);
            valid = true;
        }
        catch (DateTimeParseException ex){
            System.out.println("Invalid birthdate format. ");
        }
    }
    System.out.println("Please enter your Gender (M/F): ");
    String gender = in.nextLine();
    while(!gender.equalsIgnoreCase("M") && !gender.equalsIgnoreCase("F")){
        System.out.println("Invalid gender. Please enter 'M' or 'F': ");
        gender = in.nextLine();
    }
    System.out.println("Enter your username: ");
    String username = in.nextLine();

    while(userInfo.containsKey(username)){
        System.out.println("A user with this username exists. Please enter another username: ");
        username = in.nextLine();
    }
    User user = new User();
    user.setUsername(username);

    System.out.println("Enter your password: ");
    String password = in.nextLine();
    try{
        File.writefile(first,last,birthDate,gender,username,password);
        System.out.println("Sign Up is Successful!");
        File.readFile();
    }
    catch(IOException e){
        System.out.println("Error writing into file." + e.getMessage());
    }
}

public static void LogIn(Scanner in){
        try{
            File.readFile();
        }
        catch (IOException e){
            System.out.println("Error reading from file." + e.getMessage());
        }
    System.out.println("Enter your username: ");
    String username = in.nextLine();
    System.out.println("Enter your password: ");
    String password = in.nextLine();
    if(!userInfo.containsKey(username)){
        System.out.println("Invalid username or password.");
    } else {
    if(userInfo.get(username).equals(password)){
        System.out.println("Login is Successful!");
    }
    else{
        System.out.println("Invalid username or password. ");
        }
    }
  }
}

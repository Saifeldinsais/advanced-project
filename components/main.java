import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner choiceScanner = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        int choice;
        boolean on = true;

        while (on) {
            System.out.println("Please choose an option: ");
            System.out.println("1. Sign Up");
            System.out.println("2. Login ");
            System.out.println("3. Exit");
            try {
                choice = choiceScanner.nextInt();
                switch (choice) {
                    case 1:
                        Login_SignUp.SignUp(in);
                        break;
                    case 2:
                        Login_SignUp.LogIn(in);
                        break;
                    case 3:
                        on = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose 1 or 2 or 3.");
                }
            }
            catch(InputMismatchException ex){
                System.out.println("Invalid choice. Please enter a number. ");
                choiceScanner.nextLine();
            }
        }
        choiceScanner.close();
        in.close();

    }
}

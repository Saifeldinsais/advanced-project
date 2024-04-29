
import java.util.Scanner;

public class Phase1 {

    public static void main(String[] args) {
        int ID;
        Scanner in = new Scanner(System.in);
        User user1 = new User("saif", "hi", "C:/Users/tamsa/Pictures/Screenshots/joe.png");
        User user2 = new User("selk", "hello", "C:/Users/tamsa/Pictures/Screenshots/joe.png");
        User user3 = new User("joe", "hey", "C:/Users/tamsa/Pictures/Screenshots/joe.png");
        User user4 = new User("awad", "ahlan w shlan", "C:/Users/tamsa/Pictures/Screenshots/joe.png");


        
        addFriend("saif", "joe", user1, user3);
        addFriend("saif", "selk", user1, user2);

        user1.displayUserProfile();
        user1.displayfriendslist();
        System.out.println("------------");

        user2.displayUserProfile();
        user2.displayfriendslist();
        System.out.println("------------");

        
        
        user3.displayUserProfile();
        user3.displayfriendslist();
        System.out.println("------------");
        
        
        
        user4.displayUserProfile();
        user4.displayfriendslist();
        System.out.println("------------");        
        
    }

    public static void addFriend(String arg1, String arg2, User user1, User user2) {
        user1.addfriend(arg2);
        user2.addfriend(arg1);
    }
}

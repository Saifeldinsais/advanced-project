
import java.util.ArrayList;

public class Likes {
    private int count;
    private ArrayList<String> users; 

    public Likes() {
        this.count = 0; 
        this.users = new ArrayList<>();
    }

    public void like(String username) {
        count++;
        users.add(username);
    }

    public void unLike(String username) {
        if (count > 0) {
            count--;
            users.remove(username);
        } else {
            return;
        }
    }

    public int getCount() {
        return count;
    }

    public ArrayList<String> getLikers() {
        return users;
    }
}

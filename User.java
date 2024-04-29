import java.util.ArrayList;



/**
 *
 * @author tamsa
 */
public class User {
    private String username;
    private String bio;
    private String pfpPath;
    private ArrayList<String> friendlist = new ArrayList<String>();


    public User(String username, String bio, String pfpPath) {
        this.username = username;
        this.bio = bio;
        this.pfpPath = pfpPath;
        this.friendlist = new ArrayList<>();
    }
    
    public User(String username){
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }

    public void editUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPfpPath() {
        return pfpPath;
    }

    public void setPfpPath(String pfpPtah) {
        this.pfpPath = pfpPath;
    }

   public void addfriend(String name){
       friendlist.add(name);
   }
   
   public void removefriend(String name){
       friendlist.remove(name);
   }

    
   public void displayfriendslist(){
       if(friendlist.isEmpty()){
           System.out.println("User has no friends yet!");
       }else{
            
                System.out.println(friendlist);
            
       }
   }
   
   public void displayUserProfile(){
       System.out.println("name: " + this.getUsername());
       System.out.println("bio: " + this.getBio());
       System.out.println(pfpPath);
       System.out.println("--------------------");
   
   }
}
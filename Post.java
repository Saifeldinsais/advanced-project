
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Post {
    private final User author;
    private Object content;
    private ArrayList <Comment> comments;
    private ArrayList <Likes> likes;
    
    public Post(User author, Object content){
        this.author= author;
        this.content = content;
        this.comments= new ArrayList<>();
        this.likes= new ArrayList<>();
    }
    
    public void setContent(Object content){
        this.content=content;
    }
    
    public void addImage(BufferedImage image){
        this.content=image;
    }
    
     public User getAuthor(){
         return author;
     }
             
     public void editContent(Object newContent){
         this.content= newContent;
     }
         
       public void deleteContent(){
           this.content= null;
           this.comments.clear();
           this.likes.clear();
       }   
     
     public ArrayList<Comment> getComments(){
         return comments;
     }
     
     public ArrayList<Likes> getLikes(){
         return likes;
     }
     
     public void addComment (Comment comment){
         comments.add(comment);
     }
     
      public void removeComment (Comment comment){
         comments.remove(comment);
     }
      
      public void addlike(Likes like){
          likes.add(like);
      }

      public void unLike(Likes like) {
          likes.remove(like);
      }
  
}

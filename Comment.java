
import java.util.ArrayList;


public class Comment {
    private User author;
    private String content;
    private ArrayList<Likes> likes; 
    
    public Comment(User author, String content){
        this.author = author;
        this.content = content;
    }
    
    public User getauthor(){
        return author;
    }
    
    public String getContent(){
        return content;
    }
    
    public void editContent(String newContent){
        this.content= newContent;
    }
    
    public void likeComment(Likes like){
          likes.add(like);
      }

    public void unLikeComment(Likes like) {
          likes.remove(like);
      }
}

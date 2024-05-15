package application;

public class Post {
    private int post_Id;
    private int user_id;
    private String content;
    private int like_Count;

    public Post(int post_Id, int user_id, String content, int like_Count) {
        this.post_Id = post_Id;
        this.user_id = user_id;
        this.content = content;
        this.like_Count = like_Count;
    }

    public int getPostId() {
        return post_Id;
    }

    public int getUserId() {
        return user_id;
    }

    public String getContent() {
        return content;
    }

    public void setPostId(int postId) {
        this.post_Id = postId;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public int getLikesCount() {
        return like_Count;
    }

    public void setLikesCount(int likesCount) {
        this.like_Count = likesCount;
    }
}

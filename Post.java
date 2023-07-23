import java.util.*;

public class Post {
    private static int lastAssignedId = 0;
    private int postId;
    private User user;
    private String content;
    private Date timestamp;
    private int upvotes;
    private int downvotes;
    private List<Comment> comments;

    public Post(User user, String content) {
        this.postId = ++lastAssignedId;
        this.user = user;
        this.content = content;
        this.timestamp = new Date();
        this.upvotes = 0;
        this.downvotes = 0;
        this.comments = new ArrayList<>();
    }

    // Getters and setters for post properties
    public int getPostId() {
        return postId;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(Comment comnt) {
        comments.add(comnt);
    }

    public Comment getCommentById(int commentId) {
        for (Comment comment : comments) {
            if (comment.getCommentId() == commentId) {
                return comment;
            }
        }
        return null; // Comment with the given ID not found
    }

}

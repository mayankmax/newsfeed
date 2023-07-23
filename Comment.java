import java.util.*;

public class Comment {
    private static int inc = 1;
    private int commentId;
    private User user;
    private String content;
    private Date timestamp;
    private int upvotes;
    private int downvotes;
    private List<Comment> replies;

    public Comment(User user, String content) {
        this.commentId = inc;
        inc++;
        this.user = user;
        this.content = content;
        this.timestamp = new Date();
        this.upvotes = 0;
        this.downvotes = 0;
        this.replies = new ArrayList<>();
    }

    // Getter methods
    public int getCommentId() {
        return commentId;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    // Method to upvote the comment
    public void upvote(int upvotes) {this.upvotes = upvotes;}


    // Method to downvote the comment
    public void downvote(int downvotes) {
        this.downvotes = downvotes;
    }

    // Method to add a reply to the comment
    public void addReply(Comment reply) {
        replies.add(reply);
    }
}

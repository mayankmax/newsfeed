import java.util.*;

public class User {
    private static int inc = 0;

    private int userId;
    private String username;
    private String email;
    private String password;
    private List<Session> sessions;
    private List<Post> posts;
    private List<User> followers;
    private List<User> following;
    private Set<Integer> upvotedPostIds;
    private Set<Integer> downvotedPostIds;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.sessions = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.upvotedPostIds = new HashSet<>();
        this.downvotedPostIds = new HashSet<>();
        this.userId = inc+1;
        inc++;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public void setUpvoted(int postId) {
        upvotedPostIds.add(postId);
    }

    public boolean hasUpvoted(int postId) {
        return upvotedPostIds.contains(postId);
    }

    public void setDownvoted(int postId) {
        downvotedPostIds.add(postId);
    }

    public boolean hasDownvoted(int postId) {
        return downvotedPostIds.contains(postId);


    }

    public boolean isFollowing(User userToFollow) {
        return following.contains(userToFollow);
    }

    // Method to follow another user
    public void addFollower(User follower) {
        if (follower == null) {
            System.out.println("Cannot add null follower.");
            return;
        }

        if (followers.contains(follower)) {
            System.out.println(follower.getUsername() + " is already following you.");
        } else {
            followers.add(follower);
            System.out.println(follower.getUsername() + " is now following you.");
        }
    }
    public void follow(User userToFollow) {
        if (userToFollow == null) {
            System.out.println("Cannot follow null user.");
            return;
        }

        if (this == userToFollow) {
            System.out.println("You cannot follow yourself.");
            return;
        }

        if (following.contains(userToFollow)) {
            System.out.println("You are already following " + userToFollow.getUsername());
        } else {
            following.add(userToFollow);
            userToFollow.addFollower(this);
            System.out.println("You are now following " + userToFollow.getUsername());
        }
    }
}

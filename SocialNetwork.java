import java.util.*;

public class SocialNetwork {
    private Map<String, User> users;
    private List<Session> sessions;
    private Map<Integer, Post> posts;
    private Map<Integer, Comment> comments;
    private Session currentSession; // Added currentSession variable

    public SocialNetwork() {
        users = new HashMap<>();
        sessions = new ArrayList<>();
        posts = new HashMap<>();
        comments = new HashMap<>();
    }

    // Command-line interface methods

    // Method to handle user sign-up
    public void signUp(String username, String email, String password) {
        // Check if the email is already taken
        for (User user : users.values()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Email '" + email + "' is already registered. Please use a different email.");
                return;
            }
        }

        // Create a new User object with the provided information
        User newUser = new User(username, email, password);

        // Add the new user to the users map
        users.put(email, newUser);

        System.out.println("Congratulations, " + username + "! You have successfully signed up.");
        System.out.println("Please login with your email and password.");
    }

    // Method to handle user login and create a new session
    public Session login(String email, String password) {
        User user = users.get(email.toLowerCase());

        if (user != null && user.getPassword().equals(password)) {
            currentSession = new Session(user);
            sessions.add(currentSession);
            System.out.println("Login Successful");
            System.out.println("Now you can create a new post by giving content to createPost(Your content)");
            return currentSession;
        } else {
            System.out.println("Login failed. Invalid email or password.");
            return null;
        }
    }

    // Method to handle user post creation
    public Post createPost(String content) {
        System.out.println("createPost(content: " + content + ")");

        if (currentSession != null) {
            User currentUser = currentSession.getUser();
            Post newPost = new Post(currentUser, content);
            posts.put(newPost.getPostId(), newPost);
            currentUser.addPost(newPost);
            System.out.println("Thank you for creating a Post");
            return newPost;
        } else {
            System.out.println("You must be logged in to create a new post. Please login.");
            return null;
        }
    }
    // Method to handle user following another user
    private User findUserById(int userId) {
        for (User user : users.values()) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    // Method to handle user following another user
    public void follow(int userId) {
        if (currentSession == null) {
            System.out.println("You need to login to follow a user.");
            return;
        }

        User follower = currentSession.getUser();
        User following = findUserById(userId);

        // Check if the follower is trying to follow themselves
        if (following == null) {
            System.out.println("User with ID " + userId + " not found. Please provide a valid user to follow.");
            return;
        }

        // Check if the follower is trying to follow themselves
        if (follower == following) {
            System.out.println("You cannot follow yourself.");
            return;
        }

        // Check if the follower is already following the target user
        if (follower.isFollowing(following)) {
            System.out.println("You are already following " + following.getUsername() + ".");
        } else {
            // Add the following user to the follower's following list
            follower.follow(following);
            System.out.println("You are now following " + following.getUsername() + ".");
        }
    }

    // Method to handle user commenting on a post
    public Comment reply(int postId, String content) {
       if(currentSession == null)
       {
           System.out.println("You need to login to comment on a post");
       }
       else{
           User user = currentSession.getUser();
           Post post = posts.get(postId);

           int userId = user.getUserId();

           Comment cmnt = new Comment(user,content);


           post.setComments(cmnt);
           comments.put(cmnt.getCommentId(),cmnt);

           System.out.println("You comment on post of" +user.getUserId());
           return cmnt;
       }
        return null;
    }

    // Method to handle user upvoting a post
    public void upvotePost(int postid) {

        if(currentSession == null)
        {
            System.out.println("please login to upvote a post");
        }
        else {

            Post p = posts.get(postid);
            if (p == null) {
                System.out.println("Post with ID " + postid + " not found.");
            } else {
                if(currentSession.getUser().hasUpvoted(postid) == false) {
                    int curr = p.getUpvotes() ;
                    p.setUpvotes(curr + 1);
                    currentSession.getUser().setUpvoted(p.getPostId());
                    System.out.println("Upvoting Done....");
                }else{
                    System.out.println("Already upvoted");
                }
            }
        }


    }

    // Method to handle user downvoting a post
    public void downvotePost(int postid) {
        if(currentSession == null)
        {
            System.out.println("please login to downvote a post");
        }
        else {

            Post p = posts.get(postid);
            if (p == null) {
                System.out.println("Post with ID " + postid + " not found.");
            } else {
                if(currentSession.getUser().hasDownvoted(p.getPostId()) == false) {
                    int curr = p.getDownvotes();
                    p.setDownvotes(curr - 1);
                    currentSession.getUser().setDownvoted(p.getPostId());
                    System.out.println("DownVoting Done....");
                }else{
                    System.out.println("Already Downvoted");
                }
            }
        }
    }

    // Method to handle displaying a user's news feed


    // Method to handle user commenting on a comment
    public Comment commentOnComment(int commentId, String content) {
        if (currentSession == null) {
            System.out.println("You need to login first to comment");
            return null;
        } else {
            User user = currentSession.getUser();
            Comment comment = comments.get(commentId);

            if (comment == null) {
                System.out.println("Can't find comment with id " + commentId);
                return null;
            } else {
                Comment newComment = new Comment(user, content);
                comment.addReply(newComment);
                comments.put(newComment.getCommentId(), newComment);
                System.out.println("Your reply on " + commentId + " has been added");
                return newComment;
            }
        }
    }

    // Method to handle user upvoting a comment
    public void upvoteComment(int commentId) {

        if(currentSession == null)
        {
            System.out.println("You need to login first to upvote a comment");

        }else {
            User user = currentSession.getUser();
            Comment comment = comments.get(commentId);

            if(comment == null)
            {
                System.out.println("Can't find comment with id" +commentId);
            }
            else{
                int curr = comment.getUpvotes();
                comment.upvote(curr + 1);
                System.out.println("Upvote has been recorded");
                return;
            }


        }

        // Implementation to increase the upvotes count of the comment
    }

    // Method to handle user downvoting a comment
    public void downvoteComment(int commentId) {

        if(currentSession == null)
        {
            System.out.println("You need to login first to downvote a comment");

        }else {
            User user = currentSession.getUser();
            Comment comment = comments.get(commentId);

            if(comment == null)
            {
                System.out.println("Can't find comment with id" +commentId);
            }
            else {
                int curr = comment.getDownvotes();
                comment.downvote(curr - 1);
                System.out.println("downvote has been recorded");
                return;
            }
        }
    }

    // Method to handle displaying all posts by the current user
    public void showAllMyPost() {
        if (currentSession == null) {
            System.out.println("Please login to view all your posts.");
        } else {
            User user = currentSession.getUser();
            System.out.println("Posts by user: " + user.getUsername() + " (Email: " + user.getEmail() + "):");

            List<Post> userPosts = user.getPosts();
            if (userPosts.isEmpty()) {
                System.out.println("No posts found. Create one");
            } else {
                for (Post post : userPosts) {
                    System.out.println("Post ID: " + post.getPostId());
                    System.out.println("Content: " + post.getContent());
                    System.out.println("Timestamp: " + post.getTimestamp());
                    System.out.println("Upvotes: " + post.getUpvotes());
                    System.out.println("Downvotes: " + post.getDownvotes());
                    System.out.println("Comments:");
                    List<Comment> comments = post.getComments();
                    if (comments.isEmpty()) {
                        System.out.println("No comments yet.");
                    } else {
                        for (Comment comment : comments) {
                            System.out.println("Comment ID: " + comment.getCommentId());
                            System.out.println("Comment Content: " + comment.getContent());
                            System.out.println("Comment Timestamp: " + comment.getTimestamp());
                            System.out.println("Comment Upvotes: " + comment.getUpvotes());
                            System.out.println("Comment Downvotes: " + comment.getDownvotes());
                        }
                    }
                    System.out.println("------------------------");
                }
            }
        }
    }

    // Method to handle displaying all posts with a specific sorting option
    public void showAllPost(SortOption sortBy) {
        if (currentSession == null) {
            System.out.println("Please login to view posts.");
        } else {
            System.out.println("All Posts:");

            List<Post> allPosts = new ArrayList<>(posts.values());

            switch (sortBy) {
                case FOLLOWED_USERS:
                    // Sort posts by followed users first
                    allPosts.sort((post1, post2) -> {
                        // Replace with your custom logic to compare and sort by followed users
                        // You may need to check if the post user is followed by the current user
                        // and handle the sorting accordingly.
                        return 0;
                    });
                    break;
                case SCORE:
                    // Sort posts by score (upvotes - downvotes)
                    allPosts.sort(Comparator.comparingInt(post -> post.getUpvotes() - post.getDownvotes()));
                    Collections.reverse(allPosts); // Reverse to get higher score first
                    break;
                case NUMBER_OF_COMMENTS:
                    // Sort posts by the number of comments
                    allPosts.sort(Comparator.comparingInt(post -> post.getComments().size()));
                    Collections.reverse(allPosts); // Reverse to get higher number of comments first
                    break;
                case TIMESTAMP:
                    // Sort posts by timestamp (most recent first)
                    allPosts.sort(Comparator.comparing(Post::getTimestamp).reversed()); // Sort in reverse order
                    break;
                default:
                    System.out.println("Invalid SortOption: " + sortBy);
                    return;
            }

            for (Post post : allPosts) {
                System.out.println("Post ID: " + post.getPostId());
                System.out.println("Content: " + post.getContent());
                System.out.println("Timestamp: " + post.getTimestamp());
                System.out.println("Upvotes: " + post.getUpvotes());
                System.out.println("Downvotes: " + post.getDownvotes());
                System.out.println("Comments:");
                List<Comment> comments = post.getComments();
                if (comments.isEmpty()) {
                    System.out.println("No comments yet.");
                } else {
                    for (Comment comment : comments) {
                        System.out.println("Comment ID: " + comment.getCommentId());
                        System.out.println("Comment Content: " + comment.getContent());
                        System.out.println("Comment Timestamp: " + comment.getTimestamp());
                        System.out.println("Comment Upvotes: " + comment.getUpvotes());
                        System.out.println("Comment Downvotes: " + comment.getDownvotes());
                    }
                }
                List<Comment> comme = post.getComments();
                displayComments(comme, 1);

                System.out.println("------------------------");
                System.out.println("------------------------");
            }
        }
    }

    private void displayComments(List<Comment> comments, int level) {
        for (Comment comment : comments) {
            StringBuilder indent = new StringBuilder();
            for (int i = 0; i < level; i++) {
                indent.append("  ");
            }
            // Display replies to the current comment (if any)
            List<Comment> replies = comment.getReplies();
            if (!replies.isEmpty()) {
                System.out.println(indent + "Replies:");
                displayComments(replies, level + 1); // Recursively display replies
            }
        }
    }

    // Methods for handling user interactions based on commands
    public void handleCommandLineInput(String input) {
        String[] tokens = input.split("\\s+");
        String command = tokens[0].toLowerCase();

        switch (command) {
            case "signup":
                if (tokens.length == 4) {
                    String username = tokens[1];
                    String email = tokens[2];
                    String password = tokens[3];
                    signUp(username, email, password);
                } else {
                    System.out.println("Invalid number of arguments for signup command.");
                    System.out.println("Command for signup be be like signup username example@gmail.com mypassword");
                }
                break;
            case "login":
                if (tokens.length == 3) {
                    String email = tokens[1];
                    String password = tokens[2];
                    login(email, password);
                } else {
                    System.out.println("Invalid number of arguments for login command.");
                    System.out.println("Command be like login john.doe@email.com mysecretpassword");
                }
                break;
            case "post":
                if (tokens.length >= 2) {
                    String content = input.substring(command.length() + 1);
                    createPost(content);
                } else {
                    System.out.println("Invalid number of arguments for post command.");
                    System.out.println("Command for post be be like post Hello, this is my first post!");
                }
                break;
            case "comment":
                if (tokens.length >= 3) {
                    int id = Integer.parseInt(tokens[1]);
                    String content = String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length));
                    reply(id, content);
                } else {
                    System.out.println("Invalid number of arguments for comment command.");
                }
                break;
            case "mypost":
                showAllMyPost();
                break;
            case "logout":
                logout();
                break;
            case "showallpost":
                if (tokens.length >= 2) {
                    String option = tokens[1];
                    SortOption sortBy = parseSortOption(option);
                    if (sortBy != null) {
                        showAllPost(sortBy);
                    } else {
                        System.out.println("Invalid SortOption: " + option);
                    }
                } else {
                    System.out.println("Invalid number of arguments for showallpost.");
                }
                break;
            case "upvote":
                if (tokens.length == 2) {
                    int postId = Integer.parseInt(tokens[1]);
                    upvotePost(postId);
                } else {
                    System.out.println("Invalid number of arguments for upvote command.");
                }
                break;
            case "downvote":
                if (tokens.length == 2) {
                    int postId = Integer.parseInt(tokens[1]);
                    downvotePost(postId);
                } else {
                    System.out.println("Invalid number of arguments for downvote command.");
                }
                break;
            case "commentoncomment":
                if (tokens.length >= 3) {
                    int commentId = Integer.parseInt(tokens[1]);
                    String content = String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length));
                    commentOnComment(commentId, content);
                } else {
                    System.out.println("Invalid number of arguments for commentoncomment command.");
                }
                break;
            case "upvotecomment":
                if (tokens.length == 2) {
                    int commentId = Integer.parseInt(tokens[1]);
                    upvoteComment(commentId);
                } else {
                    System.out.println("Invalid number of arguments for upvotecomment command.");
                }
                break;
            case "downvotecomment":
                if (tokens.length == 2) {
                    int commentId = Integer.parseInt(tokens[1]);
                    downvoteComment(commentId);
                } else {
                    System.out.println("Invalid number of arguments for downvotecomment command.");
                }
                break;
            case "follow":
                if (tokens.length == 2) {
                    int id = Integer.parseInt(tokens[1]);
                    follow(id);
                } else {
                    System.out.println("Invalid number of arguments for follow command.");
                }
                break;
            default:
                System.out.println("Invalid command: " + command);
        }
    }

    private SortOption parseSortOption(String option) {
        try {
            return SortOption.valueOf(option.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }


    public void logout() {
        System.out.println("See You Soon, We are waiting for you");
        if (currentSession != null) {
            currentSession = null;
        } else {
            System.out.println("No active session");
        }
    }

    public static void main(String[] args) {
        SocialNetwork socialNetwork = new SocialNetwork();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting social network simulation.");
                break;
            }

            socialNetwork.handleCommandLineInput(input);
        }

        scanner.close();
    }
}

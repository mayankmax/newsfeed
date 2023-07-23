

Signup Command:

Command: signup <username> <email> <password>
Description: Creates a new user account with the provided username, email, and password.
Login Command:

Command: login <email> <password>
Description: Logs in the user with the given email and password, creating a new session.
Logout Command:

Command: logout
Description: Logs out the current user, ending the current session.
Post Command:

Command: post <content>
Description: Creates a new post with the given content for the currently logged-in user.
Follow Command:

Command: follow <userId>
Description: Allows the logged-in user to follow another user specified by their user ID.
Comment Command:

Command: comment <postId> <content>
Description: Allows the logged-in user to add a comment to a post specified by its post ID.
Upvote Post Command:

Command: upvote <postId>
Description: Upvotes a post specified by its post ID.
Downvote Post Command:

Command: downvote <postId>
Description: Downvotes a post specified by its post ID.
Comment on Comment Command:

Command: commentoncomment <commentId> <content>
Description: Allows the logged-in user to add a comment as a reply to another comment specified by its comment ID.
Upvote Comment Command:

Command: upvotecomment <commentId>
Description: Upvotes a comment specified by its comment ID.
Downvote Comment Command:

Command: downvotecomment <commentId>
Description: Downvotes a comment specified by its comment ID.
Show All My Posts Command:

Command: mypost
Description: Displays all the posts created by the currently logged-in user.
Show All Posts Command:

Command: showallpost <sortOption>
Description: Displays all posts with a specific sorting option. <sortOption> can be one of the following:
FOLLOWED_USERS: Sorts posts by followed users first.
SCORE: Sorts posts by score (upvotes - downvotes).
NUMBER_OF_COMMENTS: Sorts posts by the number of comments.
TIMESTAMP: Sorts posts by timestamp (most recent first).
Exit Command:

Command: exit
Description: Exits the social network simulation and terminates the program.

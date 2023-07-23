import java.util.*;

public class Session {
    private static int sessionId = 0;
    private User user;
    private Date lastActiveTime;

    public Session(User user) {
        sessionId = sessionId + 1;
        this.user = user;
        this.lastActiveTime = new Date();
    }

    // Getters and setters for session properties
    public int getSessionId() {
        return sessionId;
    }

    public User getUser() {
        return user;
    }

    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }
}

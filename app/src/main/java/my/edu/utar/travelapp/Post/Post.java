package my.edu.utar.travelapp.Post;

import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

// Represents a user-generated post
public class Post {
    private final String text;
    private final Uri imageUri;
    private final Uri videoUri;
    private final String userName;
    private final Uri userProfileUri;
    private final String location;
    private final List<String> taggedPeople;
    private final Post sharedPost;
    private final String sharedBy;
    private final List<Comment> comments = new ArrayList<>();

    private int likeCount = 0;
    private int commentCount = 0;
    private boolean liked = false;

    // Constructor for shared post
    public Post(String text,
                Uri imageUri,
                Uri videoUri,
                String userName,
                Uri userProfileUri,
                String location,
                List<String> taggedPeople,
                Post sharedPost,
                String sharedBy) {
        this.text = text;
        this.imageUri = imageUri;
        this.videoUri = videoUri;
        this.userName = userName;
        this.userProfileUri = userProfileUri;
        this.location = location;
        this.taggedPeople = taggedPeople;
        this.sharedPost = sharedPost;
        this.sharedBy = sharedBy;
    }

    // Constructor for original post
    public Post(String text,
                Uri imageUri,
                Uri videoUri,
                String userName,
                Uri userProfileUri,
                String location,
                List<String> taggedPeople) {
        this(text, imageUri, videoUri, userName, userProfileUri, location, taggedPeople, null, null);
    }

    // Getters
    public String getText() { return text; }
    public Uri getImageUri() { return imageUri; }
    public Uri getVideoUri() { return videoUri; }
    public String getUserName() { return userName; }
    public Uri getUserProfileUri() { return userProfileUri; }
    public String getLocation() { return location; }
    public List<String> getTaggedPeople() { return taggedPeople; }
    public int getLikeCount() { return likeCount; }
    public int getCommentCount() { return commentCount; }
    public boolean isLiked() { return liked; }
    public Post getSharedPost() { return sharedPost; }
    public String getSharedBy() { return sharedBy; }
    public List<Comment> getComments() { return comments; }

    // Toggle like status and update count
    public void toggleLike() {
        liked = !liked;
        likeCount = liked ? likeCount + 1 : Math.max(0, likeCount - 1);
    }

    // Increment comment count
    public void comment() {
        commentCount++;
    }

    // Add a comment object
    public void addComment(Comment c) {
        comments.add(c);
        comment();
    }

    // Inner class representing a comment
    public static class Comment {
        private final String userName;
        private final Uri userProfileUri;
        private final String text;

        public Comment(String userName, Uri userProfileUri, String text) {
            this.userName = userName;
            this.userProfileUri = userProfileUri;
            this.text = text;
        }

        public String getUserName() { return userName; }
        public Uri getUserProfileUri() { return userProfileUri; }
        public String getText() { return text; }
    }
}

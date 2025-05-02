package my.edu.utar.travelapp.Post;

import android.net.Uri;
import java.util.List;

public class Post {
    private final String        text;
    private final Uri           imageUri;
    private final Uri           videoUri;
    private final String        userName;
    private final Uri           userProfileUri;
    private final String        location;
    private final List<String>  taggedPeople;

    private int     likeCount    = 0;
    private int     commentCount = 0;
    private boolean liked        = false;  // track current state

    public Post(String text,
                Uri imageUri,
                Uri videoUri,
                String userName,
                Uri userProfileUri,
                String location,
                List<String> taggedPeople) {
        this.text           = text;
        this.imageUri       = imageUri;
        this.videoUri       = videoUri;
        this.userName       = userName;
        this.userProfileUri = userProfileUri;
        this.location       = location;
        this.taggedPeople   = taggedPeople;
    }

    // Getters
    public String        getText()         { return text; }
    public Uri           getImageUri()     { return imageUri; }
    public Uri           getVideoUri()     { return videoUri; }
    public String        getUserName()     { return userName; }
    public Uri           getUserProfileUri(){return userProfileUri;}
    public String        getLocation()     { return location; }
    public List<String>  getTaggedPeople() { return taggedPeople; }
    public int           getLikeCount()    { return likeCount; }
    public int           getCommentCount() { return commentCount; }
    public boolean       isLiked()         { return liked; }

    // Called when user taps “Like”
    public void toggleLike() {
        if (liked) {
            liked = false;
            likeCount = Math.max(0, likeCount - 1);
        } else {
            liked = true;
            likeCount++;
        }
    }

    // Called when user taps “Comment” and confirms
    public void comment() {
        commentCount++;
    }
}

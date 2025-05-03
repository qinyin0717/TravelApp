package my.edu.utar.travelapp.Post;

import java.util.ArrayList;
import java.util.List;

// Static data repository to store posts in memory
public class DataRepository {
    private static final List<Post> POSTS = new ArrayList<>();

    // Return the list of all posts
    public static List<Post> getPosts() { return POSTS; }

    // Add a new post to the top of the list
    public static void addPost(Post p) { POSTS.add(0, p); }
}

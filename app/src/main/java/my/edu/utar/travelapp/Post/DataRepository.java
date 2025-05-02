package my.edu.utar.travelapp.Post;


import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private static final List<Post> POSTS = new ArrayList<>();
    public static List<Post> getPosts() { return POSTS; }
    public static void addPost(Post p) { POSTS.add(0, p); }
}

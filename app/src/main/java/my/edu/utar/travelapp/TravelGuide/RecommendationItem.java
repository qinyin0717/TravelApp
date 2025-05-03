package my.edu.utar.travelapp.TravelGuide;

public class RecommendationItem {
    public String title, description, updateNote;
    public double rating;
    public int imageRes;

    public RecommendationItem(String title, String description, double rating, int imageRes, String updateNote) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.imageRes = imageRes;
        this.updateNote = updateNote;
    }
}
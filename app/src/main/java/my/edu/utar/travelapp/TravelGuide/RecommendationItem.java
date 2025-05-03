package my.edu.utar.travelapp.TravelGuide;

public class RecommendationItem {
    public String title, description, updateNote, fullDescription;
    public double rating;
    public int imageRes;

    public RecommendationItem(String title, String description, double rating, int imageRes, String updateNote, String fullDescription) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.imageRes = imageRes;
        this.updateNote = updateNote;
        this.fullDescription = fullDescription;
    }
}
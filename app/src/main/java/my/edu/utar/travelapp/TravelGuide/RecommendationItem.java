package my.edu.utar.travelapp.TravelGuide;

public class RecommendationItem {
    public String title, description;
    public double rating;
    public int imageRes;

    public RecommendationItem(String title, String description, double rating, int imageRes) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.imageRes = imageRes;
    }
}

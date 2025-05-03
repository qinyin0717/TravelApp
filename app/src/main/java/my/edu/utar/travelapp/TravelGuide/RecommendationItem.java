package my.edu.utar.travelapp.TravelGuide;

public class RecommendationItem {

    private String title;
    private String shortDescription;
    private String fullDescription;
    private int imageResId;
    private double rating;
    private String status;

    public RecommendationItem(String title, String shortDescription, String fullDescription,
                              int imageResId, double rating, String status) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.imageResId = imageResId;
        this.rating = rating;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public int getImageResId() {
        return imageResId;
    }

    public double getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }
}

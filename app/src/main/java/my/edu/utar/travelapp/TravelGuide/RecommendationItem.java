package my.edu.utar.travelapp.TravelGuide;

// This class defines a data model for a single travel recommendation item
public class RecommendationItem {

    // Basic information fields
    public String title;             // Name of the place
    public String description;       // Short description
    public String updateNote;        // Optional update note
    public String fullDescription;   // Full detail description
    public double rating;            // Initial/default rating
    public int imageRes;             // Image resource ID

    // Constructor to initialize all properties
    public RecommendationItem(String title, String description, double rating,
                              int imageRes, String updateNote, String fullDescription) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.imageRes = imageRes;
        this.updateNote = updateNote;
        this.fullDescription = fullDescription;
    }
}

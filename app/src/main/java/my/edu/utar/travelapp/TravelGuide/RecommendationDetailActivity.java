package my.edu.utar.travelapp.TravelGuide;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import my.edu.utar.travelapp.R;

public class RecommendationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_detail);

        // Bind UI components
        ImageView image = findViewById(R.id.detail_image);
        TextView title = findViewById(R.id.detail_title);
        TextView desc = findViewById(R.id.detail_description);
        TextView rating = findViewById(R.id.detail_rating);
        TextView commentList = findViewById(R.id.comment_list);

        // Receive intent data passed from previous activity
        Intent intent = getIntent();
        String placeTitle = intent.getStringExtra("title");

        // Display title, description and image
        title.setText(placeTitle);
        desc.setText(intent.getStringExtra("fullDescription"));
        image.setImageResource(intent.getIntExtra("image", 0));

        // Get average rating from SQLite and display it
        CommentDatabaseHelper dbHelper = new CommentDatabaseHelper(this);
        double avgRating = dbHelper.getAverageRatingForPlace(placeTitle);
        rating.setText("⭐ " + String.format("%.1f", avgRating));

        // Query and display all comments with timestamp
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT user, text, timestamp FROM comments WHERE place = ? ORDER BY timestamp ASC",
                new String[]{placeTitle});

        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder builder = new StringBuilder();
            do {
                String user = cursor.getString(0);
                String text = cursor.getString(1);
                String timestamp = cursor.getString(2);
                builder.append("• ").append(user).append(": ")
                        .append(text).append(" [").append(timestamp).append("]\n");
            } while (cursor.moveToNext());
            cursor.close();
            commentList.setText(builder.toString()); // Display comments
        } else {
            commentList.setText("No comments yet.");
        }
    }
}

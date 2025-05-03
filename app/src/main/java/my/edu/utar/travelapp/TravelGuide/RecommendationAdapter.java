package my.edu.utar.travelapp.TravelGuide;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import my.edu.utar.travelapp.R;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    // List of recommendation data and app context
    private List<RecommendationItem> items;
    private Context context;
    private CommentDatabaseHelper dbHelper;

    // Constructor
    public RecommendationAdapter(List<RecommendationItem> items, Context context) {
        this.items = items;
        this.context = context;
        this.dbHelper = new CommentDatabaseHelper(context); // local SQLite database helper
    }

    // ViewHolder for recommendation card layout
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, update, rating, latestComment;
        ImageView image;
        Button detailBtn, rateBtn, commentBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.place_title);
            desc = itemView.findViewById(R.id.place_desc);
            update = itemView.findViewById(R.id.place_update);
            rating = itemView.findViewById(R.id.place_rating);
            latestComment = itemView.findViewById(R.id.latest_comment);
            image = itemView.findViewById(R.id.place_image);
            detailBtn = itemView.findViewById(R.id.detail_button);
            rateBtn = itemView.findViewById(R.id.rate_button);
            commentBtn = itemView.findViewById(R.id.comment_button);
        }
    }

    // Inflate the recommendation card layout
    @NonNull
    @Override
    public RecommendationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendation_card, parent, false);
        return new ViewHolder(view);
    }

    // Bind data to the recommendation card views
    @Override
    public void onBindViewHolder(@NonNull RecommendationAdapter.ViewHolder holder, int position) {
        RecommendationItem item = items.get(position);
        holder.title.setText(item.title);
        holder.desc.setText(item.description);
        holder.update.setText(item.updateNote.isEmpty() ? "" : item.updateNote);
        holder.image.setImageResource(item.imageRes);

        // Display average rating from database
        double average = dbHelper.getAverageRatingForPlace(item.title);
        holder.rating.setText("⭐ " + String.format("%.1f", average));

        // Display latest comment from database
        List<String> comments = dbHelper.getCommentsByPlace(item.title);
        if (comments != null && !comments.isEmpty()) {
            holder.latestComment.setText("💬 " + comments.get(comments.size() - 1));
        } else {
            holder.latestComment.setText("");
        }

        // Detail button opens detail screen
        holder.detailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecommendationDetailActivity.class);
            intent.putExtra("title", item.title);
            intent.putExtra("description", item.description);
            intent.putExtra("rating", average);
            intent.putExtra("image", item.imageRes);
            intent.putExtra("fullDescription", item.fullDescription);
            context.startActivity(intent);
        });

        // Rating button shows dialog and saves user rating
        holder.rateBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Rate this place");

            final String[] ratings = {"1", "2", "3", "4", "5"};
            builder.setItems(ratings, (dialog, which) -> {
                int newRating = Integer.parseInt(ratings[which]);

                SharedPreferences prefs = context.getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE);
                String currentUser = prefs.getString("user_name", "Anonymous");

                dbHelper.insertOrUpdateRating(item.title, currentUser, newRating);
                notifyItemChanged(position); // refresh rating display

                Toast.makeText(context, currentUser + " rated " + item.title + ": " + newRating + " stars", Toast.LENGTH_SHORT).show();
            });

            builder.show();
        });

        // Comment button shows input dialog and stores comment
        holder.commentBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Leave a Comment");

            final EditText input = new EditText(context);
            input.setHint("Enter your comment...");
            builder.setView(input);

            builder.setPositiveButton("Submit", (dialog, which) -> {
                String comment = input.getText().toString().trim();
                if (!comment.isEmpty()) {
                    SharedPreferences prefs = context.getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE);
                    String currentUser = prefs.getString("user_name", "Anonymous");
                    dbHelper.insertComment(item.title, currentUser, comment);
                    notifyItemChanged(position); // refresh latest comment
                    Toast.makeText(context, "Comment added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Comment is empty.", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel", null);
            builder.show();
        });
    }

    // Total number of recommendation items
    @Override
    public int getItemCount() {
        return items.size();
    }
}

package my.edu.utar.travelapp.TravelGuide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import my.edu.utar.travelapp.R;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private List<RecommendationItem> items;
    private Context context;

    public RecommendationAdapter(List<RecommendationItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, rating;
        ImageView image;
        Button detailBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.place_title);
            desc = itemView.findViewById(R.id.place_desc);
            rating = itemView.findViewById(R.id.place_rating);
            image = itemView.findViewById(R.id.place_image);
            detailBtn = itemView.findViewById(R.id.detail_button);
        }
    }

    @NonNull
    @Override
    public RecommendationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendation_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationAdapter.ViewHolder holder, int position) {
        RecommendationItem item = items.get(position);
        holder.title.setText(item.title);
        holder.desc.setText(item.description);
        holder.rating.setText("⭐ " + item.rating);
        holder.image.setImageResource(item.imageRes);
        holder.detailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecommendationDetailActivity.class);
            intent.putExtra("title", item.title);
            intent.putExtra("description", item.description);
            intent.putExtra("rating", item.rating);
            intent.putExtra("image", item.imageRes);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

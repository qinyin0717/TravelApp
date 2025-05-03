package my.edu.utar.travelapp.TravelGuide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import my.edu.utar.travelapp.R;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private List<RecommendationItem> itemList;
    private Context context;

    public RecommendationAdapter(List<RecommendationItem> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title, desc, rating, status;
        public Button detailButton;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.recommendation_image);
            title = view.findViewById(R.id.recommendation_title);
            desc = view.findViewById(R.id.recommendation_desc);
            rating = view.findViewById(R.id.recommendation_rating);
            status = view.findViewById(R.id.recommendation_status);
            detailButton = view.findViewById(R.id.detail_button);
        }
    }

    @Override
    public RecommendationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recommendation_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendationAdapter.ViewHolder holder, int position) {
        RecommendationItem item = itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.desc.setText(item.getShortDescription());
        holder.image.setImageResource(item.getImageResId());
        holder.rating.setText("⭐ " + item.getRating());
        holder.status.setText(item.getStatus());

        holder.detailButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecommendationDetailActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("description", item.getFullDescription());
            intent.putExtra("image", item.getImageResId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

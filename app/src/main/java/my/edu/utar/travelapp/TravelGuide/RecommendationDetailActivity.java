package my.edu.utar.travelapp.TravelGuide;

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

        // 获取视图组件
        TextView title = findViewById(R.id.detail_title);
        TextView desc = findViewById(R.id.detail_description);
        TextView rating = findViewById(R.id.detail_rating);
        ImageView image = findViewById(R.id.detail_image);

        // 获取从 Intent 传来的数据
        String placeTitle = getIntent().getStringExtra("title");
        String placeDesc = getIntent().getStringExtra("description");
        double placeRating = getIntent().getDoubleExtra("rating", 0);
        int placeImage = getIntent().getIntExtra("image", 0);

        // 设置界面内容
        title.setText(placeTitle);
        desc.setText(placeDesc);
        rating.setText("⭐ " + placeRating);
        image.setImageResource(placeImage);
    }
}

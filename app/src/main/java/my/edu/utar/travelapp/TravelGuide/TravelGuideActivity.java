package my.edu.utar.travelapp.TravelGuide;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import my.edu.utar.travelapp.R;

public class TravelGuideActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecommendationAdapter adapter;
    private List<RecommendationItem> recommendationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_guide);

        recyclerView = findViewById(R.id.recommendation_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recommendationList = new ArrayList<>();
        recommendationList.add(new RecommendationItem("Lost Forest Cafe", "A cozy cafe surrounded by lush greenery", 4.5, R.drawable.img1, "Now open daily until 10pm"));
        recommendationList.add(new RecommendationItem("Skyhill Viewpoint", "Breathtaking panoramic city view", 4.4, R.drawable.img2, "Open as usual"));
        recommendationList.add(new RecommendationItem("Ocean Breeze Market", "Local market with sea breeze and snacks", 4.4, R.drawable.img3, "Note: Some stalls closed on Monday"));
        recommendationList.add(new RecommendationItem("Sunset Cliff", "Perfect place to watch the sunset", 4.8, R.drawable.img4, "📢 Closed this weekend for maintenance"));
        recommendationList.add(new RecommendationItem("Old Town Alley", "Historic alley with local art and craft shops", 4.9, R.drawable.img5, "Expect weekend crowd"));
        recommendationList.add(new RecommendationItem("Mountain Spring Trail", "Hiking trail with natural spring water", 4.3, R.drawable.img6, "Trail muddy after rain - wear boots"));
        recommendationList.add(new RecommendationItem("Lantern Riverwalk", "Nighttime walk with glowing lanterns", 4.6, R.drawable.img7, "Festival lights on this week only"));
        recommendationList.add(new RecommendationItem("Secret Beach", "Quiet, clean beach not on maps", 4.7, R.drawable.img8, "Check tide before visiting"));
        recommendationList.add(new RecommendationItem("Hidden Temple", "Ancient temple hidden in the hills", 4.2, R.drawable.img9, "Limited access during restoration"));
        recommendationList.add(new RecommendationItem("Bamboo Garden", "Peaceful garden with bamboo groves", 4.5, R.drawable.img10, "Open 8am - 6pm daily"));


        adapter = new RecommendationAdapter(recommendationList, this);
        recyclerView.setAdapter(adapter);
    }
}

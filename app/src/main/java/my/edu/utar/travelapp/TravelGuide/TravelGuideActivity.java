// TravelGuideActivity.java
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
        recommendationList.add(new RecommendationItem("Lost Forest Cafe", "A cozy cafe surrounded by lush greenery",
                "A hidden gem nestled in the hills, this cafe offers a peaceful escape surrounded by nature. Perfect for brunch or a quiet read, it features wooden furniture, hanging plants, and organic tea options.",
                R.drawable.img1, 4.5, "Now open daily until 10pm"));

        recommendationList.add(new RecommendationItem("Skyhill Viewpoint", "Breathtaking panoramic city view",
                "Located at the summit of Skyhill, this viewpoint offers an unparalleled look at the city skyline, especially stunning at night. A popular spot for photographers and romantic evening strolls.",
                R.drawable.img2, 4.4, "Open as usual"));

        recommendationList.add(new RecommendationItem("Ocean Breeze Market", "Local market with sea breeze and snacks",
                "An open-air market by the sea known for fresh seafood, handmade souvenirs, and sunset street food. Families love the ocean breeze and relaxed coastal atmosphere.",
                R.drawable.img3, 4.4, "Note: Some stalls closed on Monday"));

        recommendationList.add(new RecommendationItem("Sunset Cliff", "Perfect place to watch the sunset",
                "A dramatic cliffside location that offers some of the best sunset views. Visitors gather to watch the golden hour and capture perfect photos. Bring a picnic mat and enjoy the sea breeze.",
                R.drawable.img4, 4.8, "📢 Closed this weekend for maintenance"));

        recommendationList.add(new RecommendationItem("Old Town Alley", "Historic alley with local art and craft shops",
                "This narrow alley is filled with vintage murals, quaint cafes, and traditional handicraft shops. A favorite among tourists for Instagram-worthy spots and handmade goods.",
                R.drawable.img5, 4.9, "Expect weekend crowd"));

        recommendationList.add(new RecommendationItem("Mountain Spring Trail", "Hiking trail with natural spring water",
                "A moderate hiking trail popular with locals and tourists alike. Highlights include a mid-way spring where you can refill your bottles and cool your feet.",
                R.drawable.img6, 4.3, "Trail muddy after rain - wear boots"));

        recommendationList.add(new RecommendationItem("Lantern Riverwalk", "Nighttime walk with glowing lanterns",
                "A romantic riverside walk lit by hundreds of colorful lanterns. Often hosts small music performances and food stalls along the way. Best visited after 7pm.",
                R.drawable.img7, 4.6, "Festival lights on this week only"));

        recommendationList.add(new RecommendationItem("Secret Beach", "Quiet, clean beach not on maps",
                "A secluded beach known only to locals. It offers white sands, clear waters, and peace away from the crowds. Bring your own snacks and check the tides before visiting.",
                R.drawable.img8, 4.7, "Check tide before visiting"));

        recommendationList.add(new RecommendationItem("Hidden Temple", "Ancient temple hidden in the hills",
                "This centuries-old temple is reachable only via a forest path. Surrounded by mist and mystery, it's a spiritual retreat for those seeking calm and solitude.",
                R.drawable.img9, 4.2, "Limited access during restoration"));

        recommendationList.add(new RecommendationItem("Bamboo Garden", "Peaceful garden with bamboo groves",
                "Stroll among tall bamboo groves, koi ponds, and shaded benches. The garden is designed for mindfulness and reflection, often visited by writers and meditators.",
                R.drawable.img10, 4.5, "Open 8am - 6pm daily"));

        adapter = new RecommendationAdapter(recommendationList, this);
        recyclerView.setAdapter(adapter);
    }
}

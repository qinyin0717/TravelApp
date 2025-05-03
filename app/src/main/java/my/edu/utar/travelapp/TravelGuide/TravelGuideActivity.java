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
        recommendationList.add(new RecommendationItem("Petronas Twin Towers", "Iconic skyscrapers offering panoramic city views from the Skybridge.", 4.5, R.drawable.img1, "\uD83D\uDCCD Location: Kuala Lumpur"));
        recommendationList.add(new RecommendationItem("Langkawi Sky Bridge", "A curved bridge suspended above lush rainforest peaks.", 4.4, R.drawable.img2, "\uD83D\uDCCD Location: Langkawi, Kedah"));
        recommendationList.add(new RecommendationItem("George Town", "A heritage city filled with street art, culture, and local delicacies.", 4.4, R.drawable.img3, "\uD83D\uDCCD Location: Penang"));
        recommendationList.add(new RecommendationItem("Mount Kinabalu", "Malaysia’s tallest peak, ideal for hiking and nature exploration.", 4.8, R.drawable.img4, "\uD83D\uDCCD Location: Sabah, Borneo"));
        recommendationList.add(new RecommendationItem("Batu Caves", " Hindu temple caves accessed by colorful 272-step stairs.", 4.9, R.drawable.img5, "\uD83D\uDCCD Location: Selangor"));
        recommendationList.add(new RecommendationItem("Cameron Highlands", "Cool hill retreat with tea farms and strawberry picking.", 4.3, R.drawable.img6, "\uD83D\uDCCD Location: Pahang"));
        recommendationList.add(new RecommendationItem("Kuala Lumpur Tower (KL Tower)", "Tall observation tower with sky deck and city views.", 4.6, R.drawable.img7, "\uD83D\uDCCD Location: Kuala Lumpur"));
        recommendationList.add(new RecommendationItem("Malacca City", "Historic town showcasing colonial architecture and river cruises.", 4.7, R.drawable.img8, "\uD83D\uDCCD Location: Melaka"));
        recommendationList.add(new RecommendationItem("Perhentian Islands", "Paradise islands known for snorkeling and white beaches.", 4.2, R.drawable.img9, "\uD83D\uDCCD Location: Terengganu"));
        recommendationList.add(new RecommendationItem("Legoland Malaysia", "A theme park full of LEGO adventures for families.", 4.5, R.drawable.img10, "\uD83D\uDCCD Location: Johor Bahru"));


        adapter = new RecommendationAdapter(recommendationList, this);
        recyclerView.setAdapter(adapter);
    }
}
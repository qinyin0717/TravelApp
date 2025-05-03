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
        recommendationList.add(new RecommendationItem("Petronas Twin Towers", "Iconic skyscrapers offering panoramic city views from the Skybridge.", 4.5, R.drawable.img1, "\uD83D\uDCCD Location: Kuala Lumpur","Once the tallest buildings in the world, the 88-storey Petronas Twin Towers in Kuala Lumpur are a symbol of Malaysia’s modern progress. Visitors can explore the Skybridge connecting the towers and ascend to the observation deck for breathtaking panoramic views. The towers are surrounded by shopping malls, fountains, and a beautiful urban park."));
        recommendationList.add(new RecommendationItem("Langkawi Sky Bridge", "A curved bridge suspended above lush rainforest peaks.", 4.4, R.drawable.img2, "\uD83D\uDCCD Location: Langkawi, Kedah", "The Langkawi Sky Bridge is a 125-meter curved pedestrian bridge suspended 660 meters above sea level on top of Gunung Mat Cincang. It offers incredible views of Langkawi’s lush rainforests and turquoise waters. The bridge is an architectural marvel supported by a single pylon and accessible via a thrilling cable car ride."));
        recommendationList.add(new RecommendationItem("George Town", "A heritage city filled with street art, culture, and local delicacies.", 4.4, R.drawable.img3, "\uD83D\uDCCD Location: Penang", "George Town, a UNESCO World Heritage Site, is famed for its rich multicultural history, colonial architecture, and vibrant street art. As you explore the town, you’ll find colorful murals, heritage buildings, local Chinese clan houses, and hawker stalls offering some of the best food in Malaysia."));
        recommendationList.add(new RecommendationItem("Mount Kinabalu", "Malaysia’s tallest peak, ideal for hiking and nature exploration.", 4.8, R.drawable.img4, "\uD83D\uDCCD Location: Sabah, Borneo", "Rising to 4,095 meters, Mount Kinabalu is the highest peak in Malaysia and a must-visit for adventure seekers and nature lovers. Located in Sabah’s Kinabalu Park, the mountain is surrounded by one of the world’s most diverse ecosystems and offers a challenging yet rewarding trek with mesmerizing sunrise views at the summit."));
        recommendationList.add(new RecommendationItem("Batu Caves", " Hindu temple caves accessed by colorful 272-step stairs.", 4.9, R.drawable.img5, "\uD83D\uDCCD Location: Selangor", "Batu Caves is a sacred Hindu site located just outside Kuala Lumpur. It features a massive golden statue of Lord Murugan and a series of caves and temples carved into limestone cliffs. Visitors climb 272 colorful steps to reach the main cave temple. During the annual Thaipusam festival, the caves become a pilgrimage site for thousands."));
        recommendationList.add(new RecommendationItem("Cameron Highlands", "Cool hill retreat with tea farms and strawberry picking.", 4.3, R.drawable.img6, "\uD83D\uDCCD Location: Pahang", "Known for its cool climate and scenic landscapes, Cameron Highlands is a highland resort area filled with tea plantations, strawberry farms, and flower gardens. Visitors can enjoy hiking through mossy forests, sipping fresh tea at BOH plantations, or picking strawberries directly from the farm."));
        recommendationList.add(new RecommendationItem("Kuala Lumpur Tower (KL Tower)", "Tall observation tower with sky deck and city views.", 4.6, R.drawable.img7, "\uD83D\uDCCD Location: Kuala Lumpur", "The KL Tower stands at 421 meters and offers a unique 360-degree view of Kuala Lumpur’s skyline. Its Sky Deck and transparent Sky Box provide an adrenaline-filled experience for brave visitors. The tower also features a revolving restaurant and cultural exhibits."));
        recommendationList.add(new RecommendationItem("Malacca City", "Historic town showcasing colonial architecture and river cruises.", 4.7, R.drawable.img8, "\uD83D\uDCCD Location: Melaka", "Malacca (Melaka) is a historic city with Portuguese, Dutch, and British influences. Visitors can walk through Jonker Street, visit the red Dutch Stadthuys, take a boat ride along the Malacca River, and explore museums that showcase Malaysia’s colonial and Peranakan past."));
        recommendationList.add(new RecommendationItem("Perhentian Islands", "Paradise islands known for snorkeling and white beaches.", 4.2, R.drawable.img9, "\uD83D\uDCCD Location: Terengganu", "Located off the coast of Terengganu, the Perhentian Islands are a tropical paradise with crystal-clear waters and powdery white beaches. Popular for snorkeling, diving, and relaxing, the islands have minimal development, providing a peaceful retreat with a strong focus on nature conservation."));
        recommendationList.add(new RecommendationItem("Legoland Malaysia", "A theme park full of LEGO adventures for families.", 4.5, R.drawable.img10, "\uD83D\uDCCD Location: Johor Bahru", "Located in Johor Bahru, Legoland Malaysia is Southeast Asia’s first LEGO-themed park. It features more than 40 interactive rides, shows, and attractions for children and families. The complex includes a water park, a SEA LIFE aquarium, and a LEGO-themed hotel, making it a full-day fun experience for all ages."));


        adapter = new RecommendationAdapter(recommendationList, this);
        recyclerView.setAdapter(adapter);
    }
}
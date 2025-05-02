package my.edu.utar.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import my.edu.utar.travelapp.Post.PostActivity;
import my.edu.utar.travelapp.Setting.ProfileActivity;
import my.edu.utar.travelapp.Setting.SettingActivity;
import my.edu.utar.travelapp.TravelGuide.RecommendationDetailActivity;
import my.edu.utar.travelapp.TravelGuide.TravelGuideActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnGoSettings, btnGoProfile, btnGoTravelGuide,btnGoPostPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGoSettings = findViewById(R.id.btn_go_settings);
        btnGoProfile = findViewById(R.id.btn_go_profile);
        btnGoTravelGuide = findViewById(R.id.btn_go_travel_guide); // 新增按钮
        btnGoPostPage = findViewById(R.id.btn_go_post_page);

        btnGoSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        });

        btnGoProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // 新增按钮的跳转逻辑
        btnGoTravelGuide.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TravelGuideActivity.class);
            startActivity(intent);
        });

        btnGoPostPage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PostActivity.class);
            startActivity(intent);
        });
    }
}

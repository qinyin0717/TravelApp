package my.edu.utar.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import my.edu.utar.travelapp.AI.ChatbotActivity;
import my.edu.utar.travelapp.Post.PostActivity;
import my.edu.utar.travelapp.Setting.ProfileActivity;
import my.edu.utar.travelapp.Setting.SettingActivity;
import my.edu.utar.travelapp.TravelGuide.TravelGuideActivity;

public class MainActivity extends AppCompatActivity {

    // Declare buttons for navigation
    private Button btnGoSettings, btnGoProfile, btnGoTravelGuide, btnGoPostPage, btnGoAIBox, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons by linking them to layout elements
        btnGoSettings = findViewById(R.id.btn_go_settings);
        btnGoProfile = findViewById(R.id.btn_go_profile);
        btnGoTravelGuide = findViewById(R.id.btn_go_travel_guide); // Travel Guide button
        btnGoPostPage = findViewById(R.id.btn_go_post_page);
        btnGoAIBox = findViewById(R.id.btn_go_AI_Box);
        btnExit = findViewById(R.id.btn_Exit);

        // Navigate to Settings screen
        btnGoSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        });

        // Navigate to Profile screen
        btnGoProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Navigate to Travel Guide screen
        btnGoTravelGuide.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TravelGuideActivity.class);
            startActivity(intent);
        });

        // Navigate to Post creation screen
        btnGoPostPage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PostActivity.class);
            startActivity(intent);
        });

        // Navigate to AI Chatbot screen
        btnGoAIBox.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatbotActivity.class);
            startActivity(intent);
        });

        // Exit the app completely
        btnExit.setOnClickListener(v -> {
            finishAffinity(); // Closes all open activities
            System.exit(0);   // Terminates the process
        });
    }
}

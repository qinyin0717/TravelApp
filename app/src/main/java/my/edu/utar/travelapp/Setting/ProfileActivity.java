package my.edu.utar.travelapp.Setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import my.edu.utar.travelapp.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView textName, textBio;
    private static final String PREFS_NAME = "AppSettingsPrefs";
    private static final String NAME_KEY = "user_name";
    private static final String BIO_KEY = "user_bio";
    private Button btnEditProfile;
    private static final String IMAGE_KEY = "profile_image";
    private ImageView imageProfileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        textName = findViewById(R.id.text_name);
        textBio = findViewById(R.id.text_bio);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        imageProfileView = findViewById(R.id.image_profile_view);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Load profile image from SharedPreferences
        String imageUri = prefs.getString(IMAGE_KEY, "");
        if (!imageUri.isEmpty()) {
            imageProfileView.setImageURI(Uri.parse(imageUri));
        }

        // Load name and bio
        loadProfileData();

        // Launch EditProfileActivity on button click
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });
    }

    // Read and display user name and bio from SharedPreferences
    private void loadProfileData() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = prefs.getString(NAME_KEY, "No name set");
        String bio = prefs.getString(BIO_KEY, "No bio set");

        textName.setText(name);
        textBio.setText(bio);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfileData(); // Reload profile data when returning from EditProfileActivity
    }
}

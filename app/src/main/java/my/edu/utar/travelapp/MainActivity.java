package my.edu.utar.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnGoSettings, btnGoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGoSettings = findViewById(R.id.btn_go_settings);
        btnGoProfile = findViewById(R.id.btn_go_profile);

        btnGoSettings.setOnClickListener(v ->
                startActivity(new Intent(this, SettingActivity.class)));

        btnGoProfile.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));
    }
}

package my.edu.utar.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar loadingBar;
    private TextView loadingText;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Get reference to the root layout for animations
        rootLayout = findViewById(R.id.root_layout);

        // Start fade-in animation on the entire splash layout
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        rootLayout.startAnimation(fadeIn);

        // Load GIF image into ImageView using Glide
        ImageView gifView = findViewById(R.id.splash_gif);
        Glide.with(this)
                .asGif()
                .load(R.drawable.gif)
                .into(gifView);

        // Initialize progress bar and loading text
        loadingBar = findViewById(R.id.loading_bar);
        loadingText = findViewById(R.id.loading_text);

        // Simulate a loading progress using a background thread
        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 1;

                // Update UI elements from background thread using handler
                handler.post(() -> {
                    loadingBar.setProgress(progressStatus);
                    loadingText.setText("Loading... " + progressStatus + "%");
                });

                try {
                    Thread.sleep(30); // Delay between each progress increment
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // After progress reaches 100%, run a fade-out and move to MainActivity
            handler.post(() -> {
                Animation fadeOut = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_out);
                rootLayout.startAnimation(fadeOut);

                new Handler().postDelayed(() -> {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }, 500); // Wait for fade-out to finish before switching activity
            });
        }).start();
    }
}

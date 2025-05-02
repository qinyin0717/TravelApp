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

        rootLayout = findViewById(R.id.root_layout);

        // 淡入动画
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        rootLayout.startAnimation(fadeIn);

        // 加载 gif
        ImageView gifView = findViewById(R.id.splash_gif);
        Glide.with(this)
                .asGif()
                .load(R.drawable.gif)
                .into(gifView);

        loadingBar = findViewById(R.id.loading_bar);
        loadingText = findViewById(R.id.loading_text);

        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 1;

                handler.post(() -> {
                    loadingBar.setProgress(progressStatus);
                    loadingText.setText("Loading... " + progressStatus + "%");
                });

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            handler.post(() -> {
                // 淡出动画
                Animation fadeOut = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_out);
                rootLayout.startAnimation(fadeOut);

                new Handler().postDelayed(() -> {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }, 500);
            });
        }).start();
    }
}

package my.edu.utar.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar loadingBar;
    private TextView loadingText;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 加载 gif 动画
        ImageView gifView = findViewById(R.id.splash_gif);
        Glide.with(this)
                .asGif()
                .load(R.drawable.gif) // 确保你的 gif.gif 放在 res/drawable/ 中
                .into(gifView);

        // 加载条 + 百分比
        loadingBar = findViewById(R.id.loading_bar);
        loadingText = findViewById(R.id.loading_text);

        // 模拟加载过程
        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 1;

                handler.post(() -> {
                    loadingBar.setProgress(progressStatus);
                    loadingText.setText("Loading... " + progressStatus + "%");
                });

                try {
                    Thread.sleep(30); // 控制进度速度
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 加载完成后跳转到主页
            handler.post(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }).start();
    }
}

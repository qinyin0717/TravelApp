package my.edu.utar.travelapp;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private Switch switchAlerts, switchLocation, switchOfflineMode;
    private Button btnLogout, btnTestAlert, btnViewProfile, btnFeedback, btnResetSettings;
    private RadioGroup radioTheme;

    private static final String PREFS_NAME = "AppSettingsPrefs";
    private static final String ALERTS_KEY = "alerts_enabled";
    private static final String LOCATION_KEY = "location_enabled";
    private static final String OFFLINE_MODE_KEY = "offline_mode_enabled";
    private static final String THEME_KEY = "app_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String savedTheme = prefs.getString(THEME_KEY, "system");
        switch (savedTheme) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }

        btnTestAlert = findViewById(R.id.btn_test_alert);
        switchAlerts = findViewById(R.id.switch_alerts);
        switchLocation = findViewById(R.id.switch_location);
        btnLogout = findViewById(R.id.btn_logout);
        radioTheme = findViewById(R.id.radio_theme);
        switchOfflineMode = findViewById(R.id.switch_offline_mode);
        btnViewProfile = findViewById(R.id.btn_view_profile);
        btnFeedback = findViewById(R.id.btn_feedback);
        btnResetSettings = findViewById(R.id.btn_reset_settings);

        switchAlerts.setChecked(prefs.getBoolean(ALERTS_KEY, true));
        switchLocation.setChecked(prefs.getBoolean(LOCATION_KEY, true));
        switchOfflineMode.setChecked(prefs.getBoolean(OFFLINE_MODE_KEY, false));

        switchAlerts.setOnCheckedChangeListener((v, checked) ->
                prefs.edit().putBoolean(ALERTS_KEY, checked).apply());

        switchLocation.setOnCheckedChangeListener((v, checked) ->
                prefs.edit().putBoolean(LOCATION_KEY, checked).apply());

        switchOfflineMode.setOnCheckedChangeListener((v, checked) ->
                prefs.edit().putBoolean(OFFLINE_MODE_KEY, checked).apply());

        switch (savedTheme) {
            case "light":
                radioTheme.check(R.id.radio_light);
                break;
            case "dark":
                radioTheme.check(R.id.radio_dark);
                break;
            default:
                radioTheme.check(R.id.radio_system);
                break;
        }

        radioTheme.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedTheme = "system";
            if (checkedId == R.id.radio_light) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                selectedTheme = "light";
            } else if (checkedId == R.id.radio_dark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                selectedTheme = "dark";
            } else if (checkedId == R.id.radio_system) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            }
            prefs.edit().putString(THEME_KEY, selectedTheme).apply();
        });

        btnTestAlert.setOnClickListener(v -> {
            if (prefs.getBoolean(ALERTS_KEY, true))
                Toast.makeText(this, "🚨 Simulated Alert: A new tip is nearby!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "⚠️ Alerts are off.", Toast.LENGTH_SHORT).show();
        });

        btnViewProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));

        btnFeedback.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Travel App");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi Team,\n\nI have some feedback...");
            try {
                startActivity(Intent.createChooser(emailIntent, "Send Email Using..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        });

        btnResetSettings.setOnClickListener(v -> new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Reset Settings")
                .setMessage("Are you sure you want to reset all settings?")
                .setPositiveButton("Yes", (d, w) -> {
                    prefs.edit().clear().apply();
                    Toast.makeText(this, "Settings reset.", Toast.LENGTH_SHORT).show();
                    recreate();
                })
                .setNegativeButton("Cancel", null)
                .show());

        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            Toast.makeText(this, "Logged out.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

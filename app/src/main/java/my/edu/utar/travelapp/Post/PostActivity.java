package my.edu.utar.travelapp.Post;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my.edu.utar.travelapp.R;

public class PostActivity extends AppCompatActivity {
    // UI components
    private LinearLayout headerCollapsed, composerExpanded, statusContainer;
    private ImageView imageViewCurrentProfile, imageViewComposerPic, imageStatusIcon;
    private TextView textViewComposerName, textStatusMessage;
    private EditText editTextPost;
    private Button buttonTagPeople, buttonSelectMedia, buttonAddLocation, buttonPost;
    private ProgressBar progressPosting;
    private RecyclerView recyclerView;
    private PostAdapter adapter;

    // Post state
    private boolean isShareMode = false;
    private Uri selectedImageUri, selectedVideoUri;
    private String selectedLocationText;
    private Post sharedPost = null;
    private List<String> taggedPeople = new ArrayList<>();
    private final Handler handler = new Handler();
    private ActivityResultLauncher<Intent> pickMediaLauncher;

    // Dummy data for tagging and locations
    private final String[] DUMMY_NAMES = {"Adeline", "Alice", "Bob", "Charlie", "David"};
    private final String[] STATES = {
            "Johor", "Kedah", "Kelantan", "Melaka", "Negeri Sembilan", "Pahang",
            "Penang", "Perak", "Perlis", "Sabah", "Sarawak", "Selangor",
            "Terengganu", "WP Kuala Lumpur", "WP Labuan", "WP Putrajaya"
    };

    private final Map<String, String[]> CITIES = new HashMap<String, String[]>() {{
        put("Johor", new String[]{"Johor Bahru", "Kota Tinggi", "Muar", "Kluang", "Batu Pahat"});
        put("Kedah", new String[]{"Alor Setar", "Sungai Petani", "Kulim", "Baling"});
        put("Kelantan", new String[]{"Kota Bharu", "Pasir Mas", "Machang", "Tumpat"});
        put("Melaka", new String[]{"Melaka City", "Alor Gajah", "Jasin"});
        put("Negeri Sembilan", new String[]{"Seremban", "Port Dickson", "Nilai"});
        put("Pahang", new String[]{"Kuantan", "Temerloh", "Raub", "Cameron Highlands"});
        put("Penang", new String[]{"George Town", "Seberang Perai", "Bayan Lepas"});
        put("Perak", new String[]{"Ipoh", "Taiping", "Teluk Intan", "Sitiawan"});
        put("Perlis", new String[]{"Kangar", "Arau"});
        put("Sabah", new String[]{"Kota Kinabalu", "Sandakan", "Tawau", "Keningau"});
        put("Sarawak", new String[]{"Kuching", "Miri", "Sibu", "Bintulu"});
        put("Selangor", new String[]{"Shah Alam", "Petaling Jaya", "Klang", "Subang Jaya", "Kajang"});
        put("Terengganu", new String[]{"Kuala Terengganu", "Dungun", "Kemaman"});
        put("WP Kuala Lumpur", new String[]{"Kuala Lumpur"});
        put("WP Labuan", new String[]{"Labuan"});
        put("WP Putrajaya", new String[]{"Putrajaya"});
    }};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Load shared preferences
        SharedPreferences prefs = getSharedPreferences("AppSettingsPrefs", MODE_PRIVATE);
        final String testUserName = prefs.getString("user_name", "Anonymous");
        String imageUriStr = prefs.getString("profile_image", "");
        Uri profileUri = imageUriStr.isEmpty()
                ? Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ic_profile_test)
                : Uri.parse(imageUriStr);

        // Bind UI
        headerCollapsed = findViewById(R.id.headerCollapsed);
        composerExpanded = findViewById(R.id.composerExpanded);
        imageViewCurrentProfile = findViewById(R.id.imageViewCurrentProfile);
        imageViewComposerPic = findViewById(R.id.imageViewComposerProfile);
        textViewComposerName = findViewById(R.id.textViewComposerName);
        editTextPost = findViewById(R.id.editTextPost);
        buttonTagPeople = findViewById(R.id.buttonTagPeople);
        buttonSelectMedia = findViewById(R.id.buttonSelectMedia);
        buttonAddLocation = findViewById(R.id.buttonAddLocation);
        buttonPost = findViewById(R.id.buttonPost);
        progressPosting = findViewById(R.id.progressPosting);
        statusContainer = findViewById(R.id.statusContainer);
        imageStatusIcon = findViewById(R.id.imageStatusIcon);
        textStatusMessage = findViewById(R.id.textStatusMessage);
        recyclerView = findViewById(R.id.recyclerViewPosts);
        Spinner spinnerState = findViewById(R.id.spinnerState);
        Spinner spinnerCity = findViewById(R.id.spinnerCity);
        LinearLayout locationContainer = findViewById(R.id.locationContainer);

        // Set profile picture and name
        imageViewCurrentProfile.setImageURI(profileUri);
        imageViewComposerPic.setImageURI(profileUri);
        textViewComposerName.setText(testUserName);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(DataRepository.getPosts());
        recyclerView.setAdapter(adapter);

        // Setup state spinner
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, STATES);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(stateAdapter);

        // State selection updates city spinner
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String state = STATES[pos];
                ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(PostActivity.this, android.R.layout.simple_spinner_item, CITIES.getOrDefault(state, new String[]{}));
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCity.setAdapter(cityAdapter);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // City selection saves selected location
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedLocationText = spinnerState.getSelectedItem() + ", " + spinnerCity.getSelectedItem();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Show location selection layout
        buttonAddLocation.setOnClickListener(v -> locationContainer.setVisibility(View.VISIBLE));

        // Media picker logic
        pickMediaLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri uri = result.getData().getData();
                String mime = getContentResolver().getType(uri);
                if (mime != null && mime.startsWith("image/")) {
                    selectedImageUri = uri;
                    selectedVideoUri = null;
                } else if (mime != null && mime.startsWith("video/")) {
                    selectedVideoUri = uri;
                    selectedImageUri = null;
                }
            }
        });

        // Tag people dialog
        buttonTagPeople.setOnClickListener(v -> {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_tag_people, null);
            MultiAutoCompleteTextView input = dialogView.findViewById(R.id.tagPeopleInput);
            input.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, DUMMY_NAMES));
            input.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

            new AlertDialog.Builder(this)
                    .setTitle("Tag People")
                    .setView(dialogView)
                    .setPositiveButton("OK", (dialog, which) -> {
                        taggedPeople.clear();
                        String[] names = input.getText().toString().split("\\s*,\\s*");
                        for (String name : names) if (!name.isEmpty()) taggedPeople.add(name);
                        TextView tv = findViewById(R.id.textViewTagged);
                        if (taggedPeople.isEmpty()) tv.setVisibility(View.GONE);
                        else {
                            tv.setText("Tagged " + taggedPeople.size() + " people");
                            tv.setVisibility(View.VISIBLE);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Expand post composer
        headerCollapsed.setOnClickListener(v -> {
            isShareMode = false;
            buttonPost.setText("POST");
            buttonSelectMedia.setVisibility(View.VISIBLE);
            buttonAddLocation.setVisibility(View.VISIBLE);
            headerCollapsed.setVisibility(View.GONE);
            composerExpanded.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        });

        // Post submission
        buttonPost.setOnClickListener(v -> {
            String text = editTextPost.getText().toString().trim();
            if (text.isEmpty() && selectedImageUri == null && selectedVideoUri == null && sharedPost == null) return;

            progressPosting.setVisibility(View.VISIBLE);
            statusContainer.setVisibility(View.GONE);
            buttonPost.setEnabled(false);

            handler.postDelayed(() -> {
                progressPosting.setVisibility(View.GONE);
                imageStatusIcon.setImageResource(android.R.drawable.checkbox_on_background);
                textStatusMessage.setText("Your post has been posted");
                statusContainer.setVisibility(View.VISIBLE);

                handler.postDelayed(() -> {
                    Post post = new Post(
                            text, selectedImageUri, selectedVideoUri,
                            testUserName, profileUri, selectedLocationText,
                            taggedPeople, sharedPost,
                            sharedPost != null ? testUserName : null
                    );

                    DataRepository.addPost(post);
                    adapter.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);

                    // Reset UI
                    editTextPost.setText("");
                    selectedImageUri = selectedVideoUri = null;
                    selectedLocationText = null;
                    sharedPost = null;

                    TextView tvTagged = findViewById(R.id.textViewTagged);
                    taggedPeople.clear();
                    spinnerState.setSelection(0);
                    spinnerCity.setSelection(0);
                    tvTagged.setVisibility(View.GONE);

                    buttonPost.setEnabled(true);
                    statusContainer.setVisibility(View.GONE);
                    composerExpanded.setVisibility(View.GONE);
                    headerCollapsed.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }, 1000);
            }, 2000);
        });
    }
}

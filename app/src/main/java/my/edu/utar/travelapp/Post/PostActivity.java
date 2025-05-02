package my.edu.utar.travelapp.Post;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

import my.edu.utar.travelapp.Post.R;

public class PostActivity extends AppCompatActivity {
    private LinearLayout headerCollapsed, composerExpanded, statusContainer;
    private ImageView imageViewCurrentProfile, imageViewComposerPic, imageStatusIcon;
    private TextView textViewComposerName, textStatusMessage;
    private EditText editTextPost;
    private Button buttonTagPeople, buttonSelectMedia, buttonAddLocation, buttonPost;
    private ProgressBar progressPosting;
    private RecyclerView recyclerView;
    private PostAdapter adapter;

    private Uri selectedImageUri, selectedVideoUri;
    private String selectedLocationText;
    private List<String> taggedPeople = new ArrayList<>();
    private final Handler handler = new Handler();

    private ActivityResultLauncher<Intent> pickMediaLauncher;

    private final String[] DUMMY_NAMES = {"Adeline","Alice","Bob","Charlie","David"};
    private final String[] STATES = {
            "Johor","Kedah","Kelantan","Melaka","Negeri Sembilan","Pahang","Penang",
            "Perak","Perlis","Sabah","Sarawak","Selangor","Terengganu",
            "WP Kuala Lumpur","WP Labuan","WP Putrajaya"
    };

    private final Map<String, String[]> CITIES = new HashMap<String, String[]>() {{
        put("Johor", new String[]{"Johor Bahru","Kota Tinggi","Muar","Kluang","Batu Pahat"});
        put("Kedah", new String[]{"Alor Setar","Sungai Petani","Kulim","Baling"});
        put("Kelantan", new String[]{"Kota Bharu","Pasir Mas","Machang","Tumpat"});
        put("Melaka", new String[]{"Melaka City","Alor Gajah","Jasin"});
        put("Negeri Sembilan", new String[]{"Seremban","Port Dickson","Nilai"});
        put("Pahang", new String[]{"Kuantan","Temerloh","Raub","Cameron Highlands"});
        put("Penang", new String[]{"George Town","Seberang Perai","Bayan Lepas"});
        put("Perak", new String[]{"Ipoh","Taiping","Teluk Intan","Sitiawan"});
        put("Perlis", new String[]{"Kangar","Arau"});
        put("Sabah", new String[]{"Kota Kinabalu","Sandakan","Tawau","Keningau"});
        put("Sarawak", new String[]{"Kuching","Miri","Sibu","Bintulu"});
        put("Selangor", new String[]{"Shah Alam","Petaling Jaya","Klang","Subang Jaya","Kajang"});
        put("Terengganu", new String[]{"Kuala Terengganu","Dungun","Kemaman"});
        put("WP Kuala Lumpur", new String[]{"Kuala Lumpur"});
        put("WP Labuan", new String[]{"Labuan"});
        put("WP Putrajaya", new String[]{"Putrajaya"});
    }};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind views
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

        // Static user data
        final String testUserName = "Test User";

        // Use direct resource instead of URI
        imageViewCurrentProfile.setImageResource(R.drawable.ic_profile_test);
        imageViewComposerPic.setImageResource(R.drawable.ic_profile_test);
        textViewComposerName.setText(testUserName);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(DataRepository.getPosts());
        recyclerView.setAdapter(adapter);

        // State dropdown
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, STATES);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(stateAdapter);

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String state = STATES[pos];
                ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(
                        PostActivity.this,
                        android.R.layout.simple_spinner_item,
                        CITIES.getOrDefault(state, new String[]{}));
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCity.setAdapter(cityAdapter);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedLocationText = spinnerState.getSelectedItem() + ", " + spinnerCity.getSelectedItem();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        buttonAddLocation.setOnClickListener(v -> locationContainer.setVisibility(View.VISIBLE));

        // Media picker
        pickMediaLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
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
                }
        );

        // Tag people
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
                        for (String name : names)
                            if (!name.isEmpty()) taggedPeople.add(name);

                        TextView tv = findViewById(R.id.textViewTagged);
                        if (taggedPeople.isEmpty()) {
                            tv.setVisibility(View.GONE);
                        } else {
                            tv.setText("Tagged " + taggedPeople.size() + " people");
                            tv.setVisibility(View.VISIBLE);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Expand composer
        headerCollapsed.setOnClickListener(v -> {
            headerCollapsed.setVisibility(View.GONE);
            composerExpanded.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        });

        // Post button logic
        buttonPost.setOnClickListener(v -> {
            try {
                String text = editTextPost.getText().toString().trim();
                if (text.isEmpty() && selectedImageUri == null && selectedVideoUri == null) return;

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
                                text,
                                selectedImageUri,
                                selectedVideoUri,
                                testUserName,
                                Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ic_profile_test),
                                selectedLocationText,
                                taggedPeople
                        );

                        DataRepository.addPost(post);
                        adapter.notifyItemInserted(0);
                        recyclerView.scrollToPosition(0);

                        editTextPost.setText("");
                        selectedImageUri = selectedVideoUri = null;
                        selectedLocationText = null;
                        buttonPost.setEnabled(true);
                        statusContainer.setVisibility(View.GONE);

                        composerExpanded.setVisibility(View.GONE);
                        headerCollapsed.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }, 1000);
                }, 2000);

            } catch (Exception e) {
                Log.e("POST_ERROR", "Post failed", e);
                Toast.makeText(this, "Post failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

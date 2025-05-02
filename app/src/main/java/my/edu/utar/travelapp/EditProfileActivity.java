package my.edu.utar.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editName, editBio;
    private Button btnSave;
    private static final String PREFS_NAME = "AppSettingsPrefs";
    private static final String NAME_KEY = "user_name";
    private static final String BIO_KEY = "user_bio";
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageProfile;
    private Button btnChangePhoto;
    private String imageUriString = "";
    private static final String IMAGE_KEY = "profile_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnSave = findViewById(R.id.btn_save);
        editName = findViewById(R.id.edit_name);
        editBio = findViewById(R.id.edit_bio);
        btnSave = findViewById(R.id.btn_save);


        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editName.setText(prefs.getString(NAME_KEY, ""));
        editBio.setText(prefs.getString(BIO_KEY, ""));

        btnSave.setEnabled(false);

        TextWatcher inputWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputFields(); // check both fields on any text change
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        editName.addTextChangedListener(inputWatcher);
        editBio.addTextChangedListener(inputWatcher);

        imageProfile = findViewById(R.id.image_profile);
        btnChangePhoto = findViewById(R.id.btn_change_photo);

// Load saved image
        String savedImageUri = prefs.getString(IMAGE_KEY, "");
        if (!savedImageUri.isEmpty()) {
            imageProfile.setImageURI(Uri.parse(savedImageUri));
            imageUriString = savedImageUri;
        }

// Handle image change
        btnChangePhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        prefs.edit()
                .putString(NAME_KEY, editName.getText().toString())
                .putString(BIO_KEY, editBio.getText().toString())
                .putString(IMAGE_KEY, imageUriString)
                .apply();

        btnSave.setOnClickListener(v -> {
            prefs.edit()
                    .putString(NAME_KEY, editName.getText().toString())
                    .putString(BIO_KEY, editBio.getText().toString())
                    .apply();

            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
            finish(); // close activity and return to previous
        });

    }

    private void checkInputFields() {
        String name = editName.getText().toString().trim();
        String bio = editBio.getText().toString().trim();

        btnSave.setEnabled(!name.isEmpty() && !bio.isEmpty());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageProfile.setImageURI(selectedImageUri);
            imageUriString = selectedImageUri.toString(); // store for saving
        }
    }

}

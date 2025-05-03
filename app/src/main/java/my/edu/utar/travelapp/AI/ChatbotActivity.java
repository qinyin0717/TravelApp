package my.edu.utar.travelapp.AI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import my.edu.utar.travelapp.R;

public class ChatbotActivity extends AppCompatActivity {

    private EditText messageInput;
    private Button sendButton;
    private LinearLayout chatContainer;
    private ScrollView scrollView;

    private String clientId;
    private String sid = null; // reserved for socket.io session ID
    private final String SERVER_URL = "https://mobile-travel-chatbot.onrender.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        // Initialize views
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);
        chatContainer = findViewById(R.id.chat_container);
        scrollView = findViewById(R.id.scroll_view);

        // Initial greeting
        appendMessage("Hello! I’m JovaBot. Ask me anything about places, tips, food, or transport!", false);

        // Load or generate client ID
        SharedPreferences prefs = getSharedPreferences("chatbot", MODE_PRIVATE);
        clientId = prefs.getString("client_id", UUID.randomUUID().toString());
        prefs.edit().putString("client_id", clientId).apply();

        // Handle send button
        sendButton.setOnClickListener(view -> sendMessage());
    }

    private void sendMessage() {
        String message = messageInput.getText().toString().trim();
        if (message.isEmpty()) return;

        // Add user message to chat
        appendMessage(message, true);
        messageInput.setText("");

        // Prepare JSON payload
        JSONObject json = new JSONObject();
        try {
            json.put("message", message);
            json.put("sid", sid);
            json.put("client_id", clientId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create HTTP request
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(
                json.toString(), MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(SERVER_URL + "/ask")
                .post(body)
                .build();

        // Send request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> appendMessage("Error: " + e.getMessage(), false));
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject res = new JSONObject(response.body().string());
                        String reply = res.getString("reply");
                        runOnUiThread(() -> appendMessage(reply, false));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(() -> appendMessage("Server error.", false));
                }
            }
        });
    }

    private void appendMessage(String message, Boolean isUser) {
        runOnUiThread(() -> {
            // Create message bubble using CardView
            androidx.cardview.widget.CardView card = new androidx.cardview.widget.CardView(this);
            card.setCardElevation(6); // shadow effect
            card.setRadius(12); // rounded corners
            card.setUseCompatPadding(true); // ensure compatibility

            // Set up message text view
            TextView messageView = new TextView(this);
            messageView.setText(message);
            messageView.setTextSize(16);
            messageView.setPadding(20, 10, 20, 10);

            // Layout and alignment
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            if (isUser) {
                // Style for user message
                card.setCardBackgroundColor(0xFF9FE2BF); // green
                messageView.setTextColor(0xFF000000);
                cardParams.setMargins(100, 10, 10, 10);
                cardParams.gravity = Gravity.END;
            } else {
                // Style for bot message
                card.setCardBackgroundColor(0xFFFFFFFF); // white
                messageView.setTextColor(0xFF000000);
                cardParams.setMargins(10, 10, 100, 10);
                cardParams.gravity = Gravity.START;
            }

            card.setLayoutParams(cardParams);
            card.addView(messageView);
            chatContainer.addView(card);

            // Auto-scroll to bottom
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
    }
}

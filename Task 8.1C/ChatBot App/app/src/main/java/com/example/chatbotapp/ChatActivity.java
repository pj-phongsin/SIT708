package com.example.chatbotapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.Message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private Button sendButton;
    private ChatAdapter chatAdapter;
    private ArrayList<Message> messageList = new ArrayList<>();
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true); // This scrolls to bottom automatically
        chatRecyclerView.setLayoutManager(layoutManager);

        chatAdapter = new ChatAdapter(messageList);
        chatRecyclerView.setAdapter(chatAdapter);

        // Get the username passed from LoginActivity
        username = getIntent().getStringExtra("USERNAME");

        // Handle send button click
        sendButton.setOnClickListener(v -> {
            String userMessage = messageInput.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                addMessage(userMessage, "user");
                messageInput.setText("");

                // Send the message to the Flask backend in a background thread
                new Thread(() -> {
                    String botResponse = sendMessageToServer(userMessage);
                    runOnUiThread(() -> addMessage(botResponse, "bot"));
                }).start();
            }
        });
    }

    private void addMessage(String text, String sender) {
        messageList.add(new Message(text, sender));
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecyclerView.scrollToPosition(messageList.size() - 1);
    }

    private String sendMessageToServer(String userMessage) {
        String result = "";
        HttpURLConnection connection = null;

        try {
            // 👇 Update IP if needed (emulator = 10.0.2.2, real device = local IP like 192.168.x.x)
            URL url = new URL("http://10.0.2.2:5002/chat");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

            // Write user message
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = userMessage.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read response
            int responseCode = connection.getResponseCode();
            InputStream inputStream = (responseCode == 200)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line).append("\n");
            }

            result = responseBuilder.toString().trim();

        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }
}
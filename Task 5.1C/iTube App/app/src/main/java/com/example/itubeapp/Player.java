package com.example.itubeapp;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Player extends AppCompatActivity {

    WebView youtubeWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        youtubeWebView = findViewById(R.id.youtubeWebView);

        // Get the URL from intent
        String videoUrl = getIntent().getStringExtra("video_url");

        // Convert it to embed URL if needed
        String embedUrl = convertToEmbedUrl(videoUrl);

        // WebView setup
        WebSettings webSettings = youtubeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // YouTube needs JS
        youtubeWebView.setWebViewClient(new WebViewClient());

        // Load video
        youtubeWebView.loadData("<iframe width=\"100%\" height=\"100%\" src=\"" + embedUrl +
                        "\" frameborder=\"0\" allowfullscreen></iframe>",
                "text/html", "utf-8");
    }

    private String convertToEmbedUrl(String url) {
        if (url.contains("watch?v=")) {
            return url.replace("watch?v=", "embed/");
        } else {
            return url;
        }
    }
}
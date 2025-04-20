package com.example.newsapp;

public class NewsData {
    private String title;
    private int imageResId;
    private String description;
    private String summary;

    // Constructor
    public NewsData(String title, int imageResId, String description) {
        this.title = title;
        this.imageResId = imageResId;
        this.description = description;
        this.summary = "";
    }

    // Getters
    public String getTitle() { return title; }
    public int getImageResId() { return imageResId; }
    public String getDescription() { return description; }
    public String getSummary() { return summary; }
}
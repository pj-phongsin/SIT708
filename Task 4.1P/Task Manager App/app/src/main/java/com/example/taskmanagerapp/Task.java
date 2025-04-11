package com.example.taskmanagerapp;

import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    private String title;
    private String description;
    private String dueDate;
    private boolean isDone;

    // Constructor with ID (used for loading from database)
    public Task(int id, String title, String description, String dueDate, boolean isDone) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    // Constructor without ID (used when creating new task before saving to DB)
    public Task(String title, String description, String dueDate, boolean isDone) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    // Getters and Setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getDueDate() { return dueDate; }

    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public boolean isDone() { return isDone; }

    public void setDone(boolean done) { isDone = done; }
}
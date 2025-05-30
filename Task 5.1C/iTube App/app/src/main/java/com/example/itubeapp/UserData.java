package com.example.itubeapp;

import java.io.Serializable;

public class UserData implements Serializable {
    private int id;
    private String username;
    private String password;

    // Constructure to load the data from database
    public UserData(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

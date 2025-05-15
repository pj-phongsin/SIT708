package com.example.chatbotapp;

public class Message {
    private String text;
    private String sender; // "user" or "bot"

    public Message(String text, String sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }
}
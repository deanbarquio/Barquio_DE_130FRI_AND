package com.capstone.newsapplication.DataModels;

public class News {
    private String title;
    private String details;
    private int imageResId;

    public News(String title, String details, int imageResId) {
        this.title = title;
        this.details = details;
        this.imageResId = imageResId;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public int getImageResId() {
        return imageResId;
    }
}


package com.crisispulse.model;

public class Crisis {
    public String type;
    public String description;
    public double latitude;
    public double longitude;
    public String status;
    public int final_confidence_score;

    public Crisis() {} // Required for Firebase

    public Crisis(String type, String description, double latitude, double longitude, String status, int score) {
        this.type = type;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.final_confidence_score = score;
    }
}

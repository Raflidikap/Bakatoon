package com.example.bakatoon.models;

public class User {
    private String id;
    private String name;
    private String email;
    private String mbti;
    private String imageprofileUrl;

    public User(String id, String name, String email, String mbti) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mbti = mbti;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public String getImageprofileUrl() {
        return imageprofileUrl;
    }

    public void setImageprofileUrl(String imageprofileUrl) {
        this.imageprofileUrl = imageprofileUrl;
    }
}

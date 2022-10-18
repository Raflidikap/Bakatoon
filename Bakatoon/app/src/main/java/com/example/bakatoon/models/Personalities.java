package com.example.bakatoon.models;

public class Personalities {
    private String id, desc_1, desc_2, img_url, mbti, sub_mbti;

    public Personalities(String id, String desc_1, String desc_2, String img_url, String mbti, String sub_mbti) {
        this.id = id;
        this.desc_1 = desc_1;
        this.desc_2 = desc_2;
        this.img_url = img_url;
        this.mbti = mbti;
        this.sub_mbti = sub_mbti;
    }
    public Personalities(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc_1() {
        return desc_1;
    }

    public void setDesc_1(String desc_1) {
        this.desc_1 = desc_1;
    }

    public String getDesc_2() {
        return desc_2;
    }

    public void setDesc_2(String desc_2) {
        this.desc_2 = desc_2;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public String getSub_mbti() {
        return sub_mbti;
    }

    public void setSub_mbti(String sub_mbti) {
        this.sub_mbti = sub_mbti;
    }
}

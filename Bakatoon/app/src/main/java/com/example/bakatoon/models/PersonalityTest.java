package com.example.bakatoon.models;

public class PersonalityTest {
    private String question;
    private String option1;
    private String option2;
    private Integer mbti1;
    private Integer mbti2;

    public PersonalityTest(String question, String option1, String option2, Integer mbti1, Integer mbti2) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.mbti1 = mbti1;
        this.mbti2 = mbti2;
    }

    public Integer getMbti1() {
        return mbti1;
    }

    public void setMbti1(Integer mbti1) {
        this.mbti1 = mbti1;
    }

    public Integer getMbti2() {
        return mbti2;
    }

    public void setMbti2(Integer mbti2) {
        this.mbti2 = mbti2;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }
}

package com.meass.universityclass;

public class AnswerModel {
    String name,answer,time,date,email,question;

    public AnswerModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AnswerModel(String name, String answer, String time, String date, String email, String question) {
        this.name = name;
        this.answer = answer;
        this.time = time;
        this.date = date;
        this.email = email;
        this.question = question;
    }
}

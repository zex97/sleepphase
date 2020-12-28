package com.mse.group1.sleepphase.data;

import com.mse.group1.sleepphase.data.quiz_components.Question;

import java.util.ArrayList;

public class Quiz {

    private String description;
    private ArrayList<Question> questions;

    public Quiz () {
        this.description = "";
        this.questions = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}

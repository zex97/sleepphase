package com.mse.group1.sleepphase.data.quiz_components;

import com.google.common.base.Objects;

import java.util.ArrayList;

public class Question {

    private String question;
    private ArrayList<Answer> answers;

    public Question () {
        this.question = "";
        this.answers = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}

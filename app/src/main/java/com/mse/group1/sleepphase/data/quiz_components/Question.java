package com.mse.group1.sleepphase.data.quiz_components;

import com.google.common.base.Objects;
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;

import java.util.ArrayList;

public class Question {

    private String question;
    private AlarmType indicates;

    public Question () {
        this.question = "";
        this.indicates = AlarmType.REGULAR;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AlarmType getIndicates() {
        return indicates;
    }

    public void setIndicates(AlarmType indicates) {
        this.indicates = indicates;
    }
}

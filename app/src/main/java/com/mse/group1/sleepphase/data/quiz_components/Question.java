package com.mse.group1.sleepphase.data.quiz_components;

import com.google.common.base.Objects;
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;

import java.util.ArrayList;

public class Question {

    private String question;
    private AlarmType indicates;
    private boolean selected = false;

    public Question () {
        this.question = "";
        this.indicates = AlarmType.REGULAR;
    }

    public Question(String question, AlarmType indicates) {
        this.question = question;
        this.indicates = indicates;
    }

    public static Question fromString(String string) {
        String[] split = string.split(" --- ");
        String question = split[0];
        AlarmType alarmType = AlarmType.valueOf(split[1]);
        boolean selected = false;
        if (split[2].equals("true")) {
            selected = true;
        }
        return new Question(question, alarmType);
    }

    @Override
    public String toString() {
        return question + " --- " + indicates;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

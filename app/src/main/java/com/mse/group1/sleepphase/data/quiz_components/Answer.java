package com.mse.group1.sleepphase.data.quiz_components;

import com.mse.group1.sleepphase.data.alarm_components.AlarmType;

public class Answer {

    private String answer;
    private AlarmType indicates;

    public Answer() {
        this.answer = "";
        this.indicates = AlarmType.REGULAR;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public AlarmType getIndicates() {
        return indicates;
    }

    public void setIndicates(AlarmType indicates) {
        this.indicates = indicates;
    }
}

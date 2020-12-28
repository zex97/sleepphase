package com.mse.group1.sleepphase.data.alarm_components;

import androidx.annotation.NonNull;

import java.util.Objects;

public class TurningOffAlarm {

    private TurningOffTypes types;
    private Integer difficulty;
    private Integer amount;

    public TurningOffAlarm () {

    }

    public TurningOffAlarm (TurningOffTypes types, Integer difficulty, Integer amount) {
        this.types = types;
        this.difficulty = difficulty;
        this.amount = amount;
    }

    public TurningOffTypes getTypes() {
        return types;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setTypes(TurningOffTypes types) {
        this.types = types;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @NonNull
    @Override
    public String toString() {
        return "TurningOffAlarm: " + types + ", " + difficulty + ", " + amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TurningOffAlarm turningOffAlarm = (TurningOffAlarm) o;

        if (!Objects.equals(types, turningOffAlarm.types)) return false;
        if (!Objects.equals(difficulty, turningOffAlarm.difficulty)) return false;
        return Objects.equals(amount, turningOffAlarm.amount);
    }


    @Override
    public int hashCode() {
        int result = types != null ? types.hashCode() : 0;
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}

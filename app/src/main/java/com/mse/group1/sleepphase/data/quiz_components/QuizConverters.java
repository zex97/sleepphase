package com.mse.group1.sleepphase.data.quiz_components;

import androidx.room.TypeConverter;
import com.mse.group1.sleepphase.data.alarm_components.ChecklistBedtimeWakeup;

import java.util.ArrayList;

public class QuizConverters {

    @TypeConverter
    public ArrayList<Question> toArrayListQuestion(String value) {
        ArrayList<Question> list = new ArrayList<>();
        String[] strings = value.split(",");
        for (int i = 0; i < strings.length; i++) {
            list.add(Question.fromString(strings[i]));
        }
        return list;
    }

    @TypeConverter
    public String fromArrayListQuestion(ArrayList<Question> value) {
        String string = "";
        for (Question item : value) {
            string += item.toString() + ",";
        }
        return string;
    }

}

package com.mse.group1.sleepphase.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.mse.group1.sleepphase.data.alarm_components.AlarmConverters;
import com.mse.group1.sleepphase.data.quiz_components.Question;
import com.mse.group1.sleepphase.data.quiz_components.QuizConverters;

import java.util.ArrayList;

@Entity(tableName = "quiz")
@TypeConverters(QuizConverters.class)
public final class Quiz {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "quizId")
    private final String id;

    @ColumnInfo(name = "quizDescription")
    private String description;

    @ColumnInfo(name = "quizQuestions")
    private ArrayList<Question> questions;

    public Quiz (String id) {
        this.id = id;
        this.description = "";
        this.questions = new ArrayList<>();
    }

    @NonNull
    public String getId() {
        return id;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(@Nullable ArrayList<Question> questions) {
        this.questions = questions;
    }
}




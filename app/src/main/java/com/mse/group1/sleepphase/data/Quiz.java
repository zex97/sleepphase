package com.mse.group1.sleepphase.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.mse.group1.sleepphase.data.alarm_components.AlarmConverters;
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;
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

    public Quiz(@NonNull String id, String description, ArrayList<Question> questions) {
        this.id = id;
        this.description = description;
        this.questions = questions;
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

    public static Quiz getDemoQuiz() {
        ArrayList<Question> questions = new ArrayList<>();
        Question q1 = new Question("This indicates the regular alarm.", AlarmType.REGULAR);
        Question q2 = new Question("This indicates the regular alarm.", AlarmType.REGULAR);
        Question q3 = new Question("This indicates the skip a night alarm.", AlarmType.SKIP_A_NIGHT);
        Question q4 = new Question("This indicates the skip a night alarm.", AlarmType.SKIP_A_NIGHT);
        Question q5 = new Question("This indicates the step by step alarm.", AlarmType.STEP_BY_STEP);
        Question q6 = new Question("This indicates the step by step alarm.", AlarmType.STEP_BY_STEP);
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);
        Quiz ret = new Quiz("demoQuiz", "Please answer those demo questions", questions);
        return ret;
    }
}




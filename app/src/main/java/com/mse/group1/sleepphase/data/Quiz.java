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

    @ColumnInfo(name = "completed")
    private Boolean completed;

    public Quiz(@NonNull String id, String description, ArrayList<Question> questions) {
        this.id = id;
        this.description = description;
        this.questions = questions;
        this.completed = false;
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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public static Quiz getQuiz() {
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("I have trouble staying up all night.", AlarmType.STEP_BY_STEP));
        questions.add(new Question("I like staying up all night.", AlarmType.SKIP_A_NIGHT));
        questions.add(new Question("I need to fix my sleep schedule quickly.", AlarmType.SKIP_A_NIGHT));
        questions.add(new Question("I like gradual change.", AlarmType.STEP_BY_STEP));
        questions.add(new Question("I have a few weeks (or more) time to fix my sleep schedule.", AlarmType.STEP_BY_STEP));
        questions.add(new Question("I like getting things done quickly.", AlarmType.SKIP_A_NIGHT));
        Quiz ret = new Quiz("quiz", "Please answer those questions to get a recommendation of which alarm to use.", questions);
        return ret;
    }
}




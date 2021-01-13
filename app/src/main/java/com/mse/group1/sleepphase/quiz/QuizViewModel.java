package com.mse.group1.sleepphase.quiz;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.mse.group1.sleepphase.data.Quiz;
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;
import com.mse.group1.sleepphase.data.quiz_components.Question;
import java.util.List;

public class QuizViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Question>> questions = new MutableLiveData<>();
    private Quiz quiz = null;

    public QuizViewModel(@NonNull Application application) {
        super(application);
        loadQuestions();
    }

    public void loadQuestions() {
        if (quiz == null) {
            quiz = Quiz.getQuiz();
        }
        questions.setValue(quiz.getQuestions());
    }

    public LiveData<List<Question>> getItems() {
        return questions;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public AlarmType findIndicatedAlarmType() {
        int skip = 0;
        int step = 0;
        for (Question q : questions.getValue()) {
            if (q.isSelected()) {
                if (q.getIndicates() == AlarmType.SKIP_A_NIGHT) {
                    skip ++;
                } else if (q.getIndicates() == AlarmType.STEP_BY_STEP) {
                    step ++;
                }
            }
        }
        if (skip > step) {
            return AlarmType.SKIP_A_NIGHT;
        }
        return AlarmType.STEP_BY_STEP;
    }
}

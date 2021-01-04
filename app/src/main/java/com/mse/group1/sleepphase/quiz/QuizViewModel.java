package com.mse.group1.sleepphase.quiz;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.mse.group1.sleepphase.data.Quiz;

public class QuizViewModel extends AndroidViewModel {

    // TODO add datasource

    private final MutableLiveData<Quiz> quiz = new MutableLiveData<>();

    public QuizViewModel(@NonNull Application application) {
        super(application);
        // TODO set datasource
    }

    public void loadQuiz() {
        quiz.setValue(Quiz.getDemoQuiz());
        // TODO get from datasource
    }

    public MutableLiveData<Quiz> getQuiz() {
        return quiz;
    }
}

package com.mse.group1.sleepphase.quiz;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.mse.group1.sleepphase.data.Quiz;
import com.mse.group1.sleepphase.data.quiz_components.Question;
import java.util.List;

public class QuizViewModel extends AndroidViewModel {

    // TODO add datasource

    private final MutableLiveData<List<Question>> questions = new MutableLiveData<>();
    private Quiz quiz = null;

    public QuizViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadQuestions() {
        // TODO get quiz from datasource
        if (quiz == null) {
            quiz = Quiz.getDemoQuiz();
        }
        questions.setValue(quiz.getQuestions());
    }

    public LiveData<List<Question>> getItems() {
        return questions;
    }
}

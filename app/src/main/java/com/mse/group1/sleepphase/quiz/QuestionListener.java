package com.mse.group1.sleepphase.quiz;

import android.view.View;
import com.mse.group1.sleepphase.data.quiz_components.Question;

public interface QuestionListener {

    void onRadioButtonChange(Question question, View view);
}

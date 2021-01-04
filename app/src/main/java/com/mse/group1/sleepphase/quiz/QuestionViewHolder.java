package com.mse.group1.sleepphase.quiz;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.data.quiz_components.Question;

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    private TextView questionText;
    private RadioButton selected;

    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);

        questionText = itemView.findViewById(R.id.quizQuestionView);
        selected = itemView.findViewById(R.id.quizQuestionRadio);

        // TODO changelistener
    }

    public void bind(final Question question) {
        questionText.setText(question.getQuestion());
    }

    public RadioButton getSelected() {
        return selected;
    }

}

package com.mse.group1.sleepphase.quiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.alarms.SwitchChangeListener;
import com.mse.group1.sleepphase.data.Quiz;
import com.mse.group1.sleepphase.data.quiz_components.Question;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<QuestionViewHolder> {

    private List<Question> questions;
    private RadioButtonChangeListener changeListener;

    public RecycleViewAdapter(RadioButtonChangeListener listener) {
        this.questions = new ArrayList<>();
        this.changeListener = listener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        if (position < questions.size()) {
            Question question = questions.get(position);
            holder.bind(question);
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

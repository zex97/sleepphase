package com.mse.group1.sleepphase.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.alarms.AlarmsViewModel;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.data.Quiz;
import com.mse.group1.sleepphase.data.quiz_components.Question;

import java.util.List;

public class QuizFragment extends Fragment implements RadioButtonChangeListener {

    private RecycleViewAdapter adapter;
    private RecyclerView quizRecyclerView;
    private QuizViewModel viewModel;
    private List<Question> questions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecycleViewAdapter(this);
        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        questions = Quiz.getDemoQuiz().getQuestions().subList(0, 3); // TODO - set questions from view model - on change with observer next/back
        adapter.setQuestions(questions);
    }

    @Override
    public void onRadioButtonChange(Question question) {
        // TODO implement checking question
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_fragment, container, false);
        quizRecyclerView = view.findViewById(R.id.frag_quiz_recycle_view);
        quizRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        quizRecyclerView.setAdapter(adapter);
        return view;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

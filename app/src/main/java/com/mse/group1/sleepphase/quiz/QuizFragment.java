package com.mse.group1.sleepphase.quiz;

import android.os.Bundle;
import android.view.*;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.data.quiz_components.Question;
import com.mse.group1.sleepphase.databinding.QuizFragmentBinding;
import java.util.ArrayList;

public class QuizFragment extends Fragment {

    private QuizViewModel viewModel;

    private QuizAdapter adapter;

    private QuizFragmentBinding binding;

    public QuizFragment () {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupListAdapter();
    }

    private void setupListAdapter() {
        ListView listView =  binding.listQuiz;
        QuestionListener listener = new QuestionListener() {
            @Override
            public void onRadioButtonChange(Question question, View view) {
                // TODO - change backgroundcolor question
            }
        };

        adapter = new QuizAdapter(viewModel, listener, getActivity(), new ArrayList<Question>(0));
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadQuestions();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = QuizFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(QuizViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getActivity());
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

}

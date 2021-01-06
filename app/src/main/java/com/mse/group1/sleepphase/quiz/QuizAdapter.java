package com.mse.group1.sleepphase.quiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import com.mse.group1.sleepphase.data.quiz_components.Question;
import com.mse.group1.sleepphase.databinding.QuizItemBinding;
import java.util.List;

public class QuizAdapter extends BaseAdapter {

    private final QuizViewModel viewModel;
    private List<Question> questions;
    private QuestionListener listener;
    private LifecycleOwner lifecycleOwner;

    public QuizAdapter(QuizViewModel viewModel, QuestionListener listener, LifecycleOwner activity, List<Question> questions) {
        this.viewModel = viewModel;
        this.listener = listener;
        this.lifecycleOwner = activity;
        this.questions = questions;
    }

    @Override
    public int getCount() {
        if (questions == null) {
            return 0;
        }
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void replaceData(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, final View view, final ViewGroup viewGroup) {
        QuizItemBinding binding;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            binding = QuizItemBinding.inflate(inflater, viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(view);
        }

        binding.setQuestion(questions.get(position));
        binding.setLifecycleOwner(lifecycleOwner);
        binding.setListener(listener);
        binding.executePendingBindings();
        return binding.getRoot();
    }
}

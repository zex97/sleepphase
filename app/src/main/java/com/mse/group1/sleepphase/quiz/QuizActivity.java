package com.mse.group1.sleepphase.quiz;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.alarms.AlarmsFragment;
import com.mse.group1.sleepphase.alarms.AlarmsViewModel;

public class QuizActivity  extends AppCompatActivity {

    private QuizViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        QuizFragment quizFragment = (QuizFragment) getSupportFragmentManager().findFragmentById(R.id.quiz_fragment);
        if (quizFragment == null) {
            quizFragment = new QuizFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.quiz_fragment, quizFragment);
            transaction.commit();
        }

        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        // TODO onclicklisteners for buttons
    }

}

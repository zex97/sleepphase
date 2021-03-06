package com.mse.group1.sleepphase.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.addeditalarm.AddEditAlarmActivity;
import com.mse.group1.sleepphase.alarms.AlarmsActivity;
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;

public class QuizActivity  extends AppCompatActivity {

    private QuizViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        QuizFragment quizFragment = (QuizFragment) getSupportFragmentManager().findFragmentById(R.id.container_for_quiz_fragment);
        if (quizFragment == null) {
            quizFragment = new QuizFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_for_quiz_fragment, quizFragment);
            transaction.commit();
        }

        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        Button skip = findViewById(R.id.quizSkipButton);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipCklick();
            }
        });

        Button finish = findViewById(R.id.quizFinishButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishClick();
            }
        });
    }

    public void skipCklick() {
        Intent goToAlarms = new Intent(this, AlarmsActivity.class);
        finish();
        startActivity(goToAlarms);
    }

    public void finishClick() {
        Intent goToAddAlarms = new Intent(this, AddEditAlarmActivity.class);
        AlarmType type = viewModel.findIndicatedAlarmType();
        int t = 0;
        if (type == AlarmType.STEP_BY_STEP) {
            t = 1;
        } else if (type == AlarmType.SKIP_A_NIGHT) {
            t = 2;
        }
        Bundle b = new Bundle();
        b.putInt("type", t);
        goToAddAlarms.putExtras(b);
        //finish();
        startActivity(goToAddAlarms);
    }

}

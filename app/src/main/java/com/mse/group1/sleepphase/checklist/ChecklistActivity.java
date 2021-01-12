package com.mse.group1.sleepphase.checklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.alarms.AlarmsActivity;

public class ChecklistActivity extends AppCompatActivity  {

    private ChecklistViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ChecklistFragment checklistFragment = (ChecklistFragment) getSupportFragmentManager().findFragmentById(R.id.container_for_checklist_fragment);
        if (checklistFragment == null) {
            checklistFragment = new ChecklistFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_for_checklist_fragment, checklistFragment);
            transaction.commit();
        }
        viewModel = new ViewModelProvider(this).get(ChecklistViewModel.class);

        Button finish = findViewById(R.id.checklistFinishButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishClick();
            }
        });
    }

    public void finishClick() {
        Intent goToAlarms = new Intent(this, AlarmsActivity.class);
        startActivity(goToAlarms);
    }
}

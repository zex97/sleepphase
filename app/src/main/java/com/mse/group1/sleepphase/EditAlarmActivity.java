package com.mse.group1.sleepphase;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EditAlarmActivity extends AppCompatActivity {

    public static final String ALARM_ID_KEY = "alarmId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        Intent intent = getIntent();
        int alarmId = -2;
        if (intent != null) {
            alarmId = intent.getIntExtra(ALARM_ID_KEY, -1);
        }
    }
}

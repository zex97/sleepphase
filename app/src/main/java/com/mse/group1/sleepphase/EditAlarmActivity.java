package com.mse.group1.sleepphase;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.mse.group1.sleepphase.models.AlarmModel;
import com.mse.group1.sleepphase.models.AlarmType;


import java.util.ArrayList;
import java.util.List;

public class EditAlarmActivity extends AppCompatActivity {

    public static final String ALARM_ID_KEY = "alarmId";

    private AlarmModel alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\">" + "Edit alarm" + "</font>", Html.FROM_HTML_MODE_LEGACY));

        Intent intent = getIntent();
        int alarmId = -2;
        if (intent != null) {
            alarmId = intent.getIntExtra(ALARM_ID_KEY, -1);

            // TODO in exercise 3, get alarm from database
            alarm = new AlarmModel();

            Spinner spinner = (Spinner) findViewById(R.id.spinner_edit_alarm_type);
            changeSpinnerColors(spinner);
            spinner.setSelection(alarm.getType() == AlarmType.REGULAR ? 0 : (alarm.getType() == AlarmType.STEP_BY_STEP ? 1 : 2));

            TimePicker timePicker = findViewById(R.id.timePickerGoalEditAlarm);
            TextView textView = findViewById(R.id.goalTextViewEditAlarm);
//            if (alarm.getType() == AlarmType.REGULAR) {
//                editText.setVisibility(View.INVISIBLE);
//                textView.setVisibility(View.INVISIBLE);
//            }
            spinner.setOnItemSelectedListener(OnCatSpinnerCL);
        }
    }

    private void changeSpinnerColors(Spinner spinner) {
        List<String> list = new ArrayList<>();
        list.add("Regular");
        list.add("Step By Step");
        list.add("Skip A Night");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_entry,list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(OnCatSpinnerCL);
    }


    private OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            alarm.setType(pos == 0 ? AlarmType.REGULAR : (pos == 1 ? AlarmType.STEP_BY_STEP : AlarmType.SKIP_A_NIGHT));
//            ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            TimePicker timePicker = findViewById(R.id.timePickerGoalEditAlarm);
            TextView textView = findViewById(R.id.goalTextViewEditAlarm);
            if (alarm.getType() == AlarmType.REGULAR) {
                timePicker.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
            } else {
                timePicker.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}

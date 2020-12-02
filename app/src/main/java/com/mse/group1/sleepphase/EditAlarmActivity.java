package com.mse.group1.sleepphase;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.mse.group1.sleepphase.fragments.MyAlarmsFragment;
import com.mse.group1.sleepphase.models.AlarmModel;
import com.mse.group1.sleepphase.models.AlarmType;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditAlarmActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static final String ALARM_ID_KEY = "alarmId";

    private AlarmModel alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_alarm);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\">" + "New alarm" + "</font>", Html.FROM_HTML_MODE_LEGACY));

        Intent intent = getIntent();
        int alarmId = -2;
        if (intent != null) {
            alarmId = intent.getIntExtra(ALARM_ID_KEY, -1);

            if (alarmId != -1) {
                actionBar.setTitle(Html.fromHtml("<font color=\"#ffffff\">" + "Edit alarm" + "</font>", Html.FROM_HTML_MODE_LEGACY));
            }

            // TODO in exercise 3, get alarm from database
            alarm = new AlarmModel();

            Spinner spinnerAlarmType = findViewById(R.id.spinner_edit_alarm_type);
            changeSpinnerColors(spinnerAlarmType,0);
            spinnerAlarmType.setSelection(alarm.getType() == AlarmType.REGULAR ? 0 : (alarm.getType() == AlarmType.STEP_BY_STEP ? 1 : 2));

            TimePicker timePicker = findViewById(R.id.timePickerGoalEditAlarm);
            timePicker.setIs24HourView(true);
            EditText editTextSkip = findViewById(R.id.skip_night_edit_text);
            spinnerAlarmType.setOnItemSelectedListener(alarmTypeSpinnerListener);

            Button skipNightButton = findViewById(R.id.skip_night_button);
            skipNightButton.setOnClickListener(datePickerOnClickListener);
            editTextSkip.setOnClickListener(datePickerOnClickListener);

            changeSpinnerColors((Spinner) findViewById(R.id.spinner_melodies), 1);
            changeSpinnerColors((Spinner) findViewById(R.id.spinner_turning_of_type), 2);
            changeSpinnerColors((Spinner) findViewById(R.id.spinner_turning_of_difficulty), 3);
            changeSpinnerColors((Spinner) findViewById(R.id.spinner_turning_of_amount), 4);

            CheckBox checkBoxBedtime = findViewById(R.id.checklist_bedtime_checkbox);
            checkBoxBedtime.setOnCheckedChangeListener(bedtimeCheckboxListener);
        }
        Button okButton = findViewById(R.id.buttonOk);

        okButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*MyAlarmsFragment firstFrag = new MyAlarmsFragment();
                        this.getFragmentManager().beginTransaction()
                                .replace(R.id.layout_container, firstFrag, TAG_FRAGMENT)
                                .addToBackStack(null)
                                .commit();*/
                    }
                }
        );
    }

    private void changeSpinnerColors(Spinner spinner, int type) {
        List<String> list;
        if (type == 0) {
            list = Arrays.asList(getResources().getStringArray(R.array.spinner_options));
        } else if (type == 1){
            list = Arrays.asList(getResources().getStringArray(R.array.melodies));
        } else if (type == 2){
            list = Arrays.asList(getResources().getStringArray(R.array.turning_off_types));
        } else if (type == 3){
            list = Arrays.asList(getResources().getStringArray(R.array.turning_off_difficulty));
        } else {
            list = Arrays.asList(getResources().getStringArray(R.array.turning_off_amount));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_entry, list);
        spinner.setAdapter(adapter);
    }

    private OnCheckedChangeListener bedtimeCheckboxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
            ConstraintLayout bedtimeConstraintLayout = findViewById(R.id.bedtime_constr_layout);
            if (isChecked) {
                bedtimeConstraintLayout.setVisibility(View.VISIBLE);
            } else {
                bedtimeConstraintLayout.setVisibility(View.GONE);
            }
        }
    };

    private OnClickListener datePickerOnClickListener = new AdapterView.OnClickListener() {
        @Override
        public void onClick(View v) {
            LocalDate now = LocalDate.now();
            DatePickerDialog datePickerDialog = new DatePickerDialog(EditAlarmActivity.this, EditAlarmActivity.this, now.getYear(), now.getMonth().getValue()-1, now.getDayOfMonth());
            datePickerDialog.show();
        }
    };

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        EditText editText = findViewById(R.id.skip_night_edit_text);
        editText.setText(dayOfMonth + "/" + month + "/" + year);
        alarm.setSkip(LocalDate.of(year, month, dayOfMonth));
    }

    private OnItemSelectedListener alarmTypeSpinnerListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            alarm.setType(pos == 0 ? AlarmType.REGULAR : (pos == 1 ? AlarmType.STEP_BY_STEP : AlarmType.SKIP_A_NIGHT));
//            ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            ConstraintLayout goal = findViewById(R.id.goal_elements_container);
            ConstraintLayout skip = findViewById(R.id.skip_night_element_constainer);
            ConstraintLayout step = findViewById(R.id.step_by_step_elements_container);
            if (alarm.getType() == AlarmType.REGULAR) {
                goal.setVisibility(View.GONE);
                skip.setVisibility(View.GONE);
                step.setVisibility(View.GONE);
            } else {
                goal.setVisibility(View.VISIBLE);
                if (alarm.getType() == AlarmType.SKIP_A_NIGHT) {
                    skip.setVisibility(View.VISIBLE);
                    step.setVisibility(View.GONE);
                } else {
                    step.setVisibility(View.VISIBLE);
                    skip.setVisibility(View.GONE);
                }
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

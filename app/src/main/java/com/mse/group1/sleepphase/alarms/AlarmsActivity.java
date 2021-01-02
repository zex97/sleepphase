package com.mse.group1.sleepphase.alarms;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.addeditalarm.AddEditAlarmActivity;
import com.mse.group1.sleepphase.addeditalarm.AddEditAlarmFragment;
import com.mse.group1.sleepphase.addeditalarm.AddEditViewModel;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;
import com.mse.group1.sleepphase.data.alarm_components.ChecklistBedtimeWakeup;
import com.mse.group1.sleepphase.data.alarm_components.TurningOffAlarm;
import com.mse.group1.sleepphase.data.alarm_components.TurningOffTypes;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;

public class AlarmsActivity extends AppCompatActivity {

    private AlarmsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AlarmsFragment alarmsFragment = (AlarmsFragment) getSupportFragmentManager().findFragmentById(R.id.container_for_alarms_fragment);
        if (alarmsFragment == null) {
            alarmsFragment = new AlarmsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_for_alarms_fragment, alarmsFragment);
            transaction.commit();
        }

        viewModel = new ViewModelProvider(this).get(AlarmsViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addNewAlarmObservable();
            }
        });

        viewModel.getCreateAlarmObservable().observe(this, new Observer<Event<Object>>() {
            @Override
            public void onChanged(Event<Object> taskIdEvent) {
                if (taskIdEvent.getContentIfNotHandled() != null) {
                    createNewAlarm();
                }
            }
        });

        viewModel.getEditAlarmObservable().observe(this, new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> alarmIdEvent) {
                System.out.println("BBBBBBBBBB  ");
                String alarmId = alarmIdEvent.getContentIfNotHandled();
                if (alarmId != null) {
                    editAlarm(alarmId);
                }

            }
        });

    }

    public void createNewAlarm () {

        Intent intent = new Intent(this, AddEditAlarmActivity.class);
        startActivityForResult(intent, AddEditAlarmActivity.REQUEST_CODE);

//        AddEditViewModel aa = new ViewModelProvider(this).get(AddEditViewModel.class);
//        Alarm alarm = new Alarm();
//
//        alarm.setActive(true);
//        alarm.setType(AlarmType.SKIP_A_NIGHT);
//        alarm.setName("Name");
//        alarm.setRingAt(LocalTime.now());
//        alarm.setGoal(LocalTime.now());
//        alarm.setDays(new ArrayList<String>(){{add("Mo"); add("We");}});
//        alarm.setSkip(LocalDate.now());
//        alarm.setChangeBy(5);
//        alarm.setEveryDays(3);
//        alarm.setSound("Song");
//        alarm.setVibrate(true);
//        alarm.setSnooze_enabled(true);
//        alarm.setSnooze_every_min(3);
//        alarm.setSnooze_times(1);
//        alarm.setTurning_off_alarm(new TurningOffAlarm(TurningOffTypes.MATH_EQUATION, 1, 2));
//        alarm.setChecklist_bedtime(new ArrayList<ChecklistBedtimeWakeup>(){{add(new ChecklistBedtimeWakeup());}});
//        alarm.setChecklist_wakeup(new ArrayList<ChecklistBedtimeWakeup>(){{add(new ChecklistBedtimeWakeup());}});
//
//        aa.alarmObservable.setValue(alarm);
//        aa.saveAlarm();
    }

    public void editAlarm (String alarmId) {
        System.out.println("EDIIIIIT");
        Intent intent = new Intent(this, AddEditAlarmActivity.class);
        intent.putExtra(AddEditAlarmFragment.EDIT_TASK_ID, alarmId);
        startActivityForResult(intent, AddEditAlarmActivity.REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

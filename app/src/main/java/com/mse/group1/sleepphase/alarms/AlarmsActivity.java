package com.mse.group1.sleepphase.alarms;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.addeditalarm.AddEditAlarmActivity;
import com.mse.group1.sleepphase.addeditalarm.AddEditAlarmFragment;

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
            public void onChanged(Event<Object> alarmsIdEvent) {
                if (alarmsIdEvent.getContentIfNotHandled() != null) {
                    createNewAlarm();
                }
            }
        });

        viewModel.getEditAlarmObservable().observe(this, new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> alarmIdEvent) {
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
    }

    public void editAlarm (String alarmId) {
        Intent intent = new Intent(this, AddEditAlarmActivity.class);
        intent.putExtra(AddEditAlarmFragment.EDIT_ALARM_CODE, alarmId);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewModel.handleActivityResult(requestCode, resultCode);
    }
}

package com.mse.group1.sleepphase.addeditalarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.alarms.AlarmsActivity;
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;


public class AddEditAlarmActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    public static final int ADD_EDIT_OK = 2;

    private AddEditViewModel viewModel;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        AddEditAlarmFragment fragment = (AddEditAlarmFragment) getSupportFragmentManager().findFragmentById(R.id.container_for_add_edit_fragment);
        if (fragment == null) {
            fragment = new AddEditAlarmFragment();

            Bundle bundle = new Bundle();
            bundle.putString(AddEditAlarmFragment.EDIT_ALARM_CODE, getIntent().getStringExtra(AddEditAlarmFragment.EDIT_ALARM_CODE));
            fragment.setArguments(bundle);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_for_add_edit_fragment, fragment);
        transaction.commit();

        viewModel = new ViewModelProvider(this).get(AddEditViewModel.class);
        viewModel.getAlarmUpdatedObservable().observe(this, new Observer<Event<Object>>() {
            @Override
            public void onChanged(Event<Object> alarmIdEvent) {
                if (alarmIdEvent.getContentIfNotHandled() != null) {
                    onAlarmSavedSuccessfully();
                }
            }
        });

        viewModel.getInformationDialog().observe(this, new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> typeDialogEvent) {
                String typeDialog = typeDialogEvent.getContentIfNotHandled();
                if (typeDialog != null) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(AddEditAlarmActivity.this).create();
                    alertDialog.setTitle("Info Dialog");
                    if (typeDialog.equals("reminder")) {
                        alertDialog.setMessage("Reminds you when you should go to bed.");
                    } else if (typeDialog.equals("alarm_type")) {
                        alertDialog.setMessage("Alarm types are..."); //TODO
                    } else if (typeDialog.equals("turning_off_type")) {
                        alertDialog.setMessage("Choose between turning the alarm off with a swipe, by solving math equations or by shaking your phone!");
                    } else if (typeDialog.equals("checklist_bedtime")) {
                        alertDialog.setMessage("Things you have to do before you go to sleep!");
                    } else {
                        alertDialog.setMessage("Invalid dialog code, something went wrong.");
                    }
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.show();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
    }

    public void onAlarmSavedSuccessfully() {
        setResult(ADD_EDIT_OK);
        finish();
    }
}

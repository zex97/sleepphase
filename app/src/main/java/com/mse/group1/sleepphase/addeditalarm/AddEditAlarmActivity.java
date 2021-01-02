package com.mse.group1.sleepphase.addeditalarm;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.R;


public class AddEditAlarmActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    public static final int ADD_EDIT_OK = 1;

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
            bundle.putString(AddEditAlarmFragment.EDIT_TASK_ID, getIntent().getStringExtra(AddEditAlarmFragment.EDIT_TASK_ID));
            fragment.setArguments(bundle);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_for_add_edit_fragment, fragment);
        transaction.commit();

        viewModel = new ViewModelProvider(this).get(AddEditViewModel.class);
        viewModel.getAlarmUpdatedObservable().observe(this, new Observer<Event<Object>>() {
            @Override
            public void onChanged(Event<Object> taskIdEvent) {
                if (taskIdEvent.getContentIfNotHandled() != null) {
                    onAlarmSavedSuccessfully();
                }
            }
        });
    }

    public void onAlarmSavedSuccessfully() {
        setResult(ADD_EDIT_OK);
        finish();
    }
}

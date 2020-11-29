package com.mse.group1.sleepphase;

import android.content.Intent;
import android.text.Html;
import android.view.MenuItem;
import android.view.Window;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.core.view.WindowCompat;

public class EditAlarmActivity extends AppCompatActivity {

    public static final String ALARM_ID_KEY = "alarmId";

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
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}

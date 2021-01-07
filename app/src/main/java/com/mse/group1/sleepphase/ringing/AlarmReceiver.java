package com.mse.group1.sleepphase.ringing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String ALARM_ID = "ALARM_ID";
    public static final String DAYS = "DAYS";
    public static final String TITLE = "TITLE";
    public static final String SONG = "SONG";
    public static final String IS_RECURRING = "IS_RECURRING";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(context, "Initiating alarm reboot.", Toast.LENGTH_SHORT).show();
            Intent rescheduleIntent = new Intent(context, RescheduleAlarmsService.class);
            context.startForegroundService(rescheduleIntent);
        }
        else {
            if (!intent.getBooleanExtra(IS_RECURRING, false)) {
                startAlarmService(context, intent);
            } {
                if (alarmIsToday(intent)) {
                    startAlarmService(context, intent);
                }
            }
        }
    }

    private boolean alarmIsToday(Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        String days = intent.getStringExtra(DAYS);
        boolean result = false;
        result = result || (today == 2 && days.contains("Mo"));
        result = result || (today == 3 && days.contains("Tu"));
        result = result || (today == 4 && days.contains("We"));
        result = result || (today == 5 && days.contains("Th"));
        result = result || (today == 6 && days.contains("Fr"));
        result = result || (today == 7 && days.contains("Sa"));
        result = result || (today == 1 && days.contains("Su"));

        return result;
    }

    private void startAlarmService(Context context, Intent intent) {
        Intent intentAlarmService = new Intent(context, AlarmService.class);
        intentAlarmService.putExtra(ALARM_ID, intent.getStringExtra(ALARM_ID));
        intentAlarmService.putExtra(TITLE, intent.getStringExtra(TITLE));
        intentAlarmService.putExtra(SONG, intent.getStringExtra(SONG));
        context.startForegroundService(intentAlarmService);
    }
}

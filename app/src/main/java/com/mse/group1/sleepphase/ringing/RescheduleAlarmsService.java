package com.mse.group1.sleepphase.ringing;

import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;
import com.mse.group1.sleepphase.data.source.AlarmsDataSource;
import com.mse.group1.sleepphase.data.source.SimpleAlarmsDataSource;
import com.mse.group1.sleepphase.util.AppExecutors;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RescheduleAlarmsService extends LifecycleService {

    final MutableLiveData<List<Alarm>> items = new MutableLiveData<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        SimpleAlarmsDataSource dataSource = SimpleAlarmsDataSource.getInstance(new AppExecutors(), getApplication());
        dataSource.getAlarms(new AlarmsDataSource.LoadAlarmsCallback() {
            @Override
            public void onAlarmsLoaded(List<Alarm> alarms) {
                items.setValue(alarms);
            }

            @Override
            public void onDataNotAvailable() {
                items.setValue(new ArrayList<Alarm>());
            }
        });
        items.observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                for (Alarm a : alarms) {
                    if (a.getActive()) {
                        a.activateAlarm(getApplicationContext());
                    }
                }
            }
        });

        return START_STICKY;
    }

    public void rescheduleAlarms() {
        if (items.getValue() != null) {
            for (Alarm alarm : items.getValue()) {
                if (alarm.getType().equals(AlarmType.SKIP_A_NIGHT)) {
                    if (alarm.getSkip() != null && (LocalDate.now().isAfter(alarm.getSkip()) || LocalDate.now().isEqual(alarm.getSkip())) && alarm.getGoal() != null) {
                        alarm.setRingAt(alarm.getGoal());
                    }
                } else if (alarm.getType().equals(AlarmType.STEP_BY_STEP)) {
                    if (alarm.getLastChange() != null && LocalDate.now().isEqual(alarm.getLastChange().plusDays(alarm.getEveryDays())) && alarm.getGoal() != null && !alarmGoalReached(alarm)) {
                        LocalTime curr = alarm.getRingAt();
                        curr = curr.minusMinutes(alarm.getChangeBy());
                        if (curr.isBefore(alarm.getGoal())) {
                            alarm.setRingAt(alarm.getGoal());
                        } else {
                            alarm.setRingAt(curr);
                        }
                    }
                }
            }
        }
    }

    public boolean alarmGoalReached(Alarm alarm) {
        return alarm.getRingAt().isEqual(alarm.getGoal());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }
}

package com.mse.group1.sleepphase.data.source;

import androidx.lifecycle.LiveData;
import com.mse.group1.sleepphase.data.Alarm;

import java.util.List;

public interface AlarmsDataSource {

    Alarm getAlarm(String alarmId);

    LiveData<List<Alarm>> getAlarms();

    void saveAlarm(Alarm alarm);

    void deleteAlarm(String alarmId);

    void updateAlarm(Alarm alarm);
}

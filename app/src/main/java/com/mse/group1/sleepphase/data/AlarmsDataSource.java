package com.mse.group1.sleepphase.data;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface AlarmsDataSource {

    Alarm getAlarm(String alarmId);

    LiveData<List<Alarm>> getAlarms();

    void saveAlarm(Alarm alarm);

    void deleteAlarm(String alarmId);

    void updateAlarm(Alarm alarm);
}

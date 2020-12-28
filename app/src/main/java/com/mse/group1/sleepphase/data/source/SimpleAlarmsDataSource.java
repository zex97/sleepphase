package com.mse.group1.sleepphase.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.data.AlarmsDataSource;

import java.util.List;

public class SimpleAlarmsDataSource implements AlarmsDataSource {

    private AlarmDAO alarmDao;
    private LiveData<List<Alarm>> alarmsLiveData;

    public SimpleAlarmsDataSource (@NonNull AlarmDAO alarmDao) {
        this.alarmDao = alarmDao;
        this.alarmsLiveData = alarmDao.getAlarms();
    }

    @Override
    public Alarm getAlarm(String alarmId) {
        return alarmDao.getAlarmById(alarmId);
    }

    @Override
    public LiveData<List<Alarm>> getAlarms() {
        return alarmsLiveData;
    }

    @Override
    public void saveAlarm(@NonNull final Alarm alarm) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                alarmDao.insertAlarm(alarm);
            }
        };
        LocalDatabase.databaseExecutors.execute(runnable);
    }

    @Override
    public void deleteAlarm(final String alarmId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                alarmDao.deleteAlarmById(alarmId);
            }
        };
        LocalDatabase.databaseExecutors.execute(runnable);
    }

    @Override
    public void updateAlarm(final Alarm alarm) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                alarmDao.updateAlarm(alarm);
            }
        };
        LocalDatabase.databaseExecutors.execute(runnable);
    }
}

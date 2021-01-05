package com.mse.group1.sleepphase.data.source;

import androidx.annotation.NonNull;
import com.mse.group1.sleepphase.data.Alarm;

import java.util.List;

public interface AlarmsDataSource {

    interface LoadAlarmsCallback {

        void onAlarmsLoaded(List<Alarm> alarms);

        void onDataNotAvailable();
    }

    interface GetAlarmsCallback {

        void onAlarmLoaded(Alarm alarm);

        void onDataNotAvailable();
    }

    interface GetAfterDeleteCallback {

        void onAlarmDeleted();

    }

    void getAlarm(@NonNull String taskId, @NonNull GetAlarmsCallback callback);

    void getAlarms(@NonNull LoadAlarmsCallback callback);

    void saveAlarm(Alarm alarm);

    void changeActiveStatusAlarm(@NonNull String alarmId);

    void deleteAlarm(String alarmId, @NonNull GetAfterDeleteCallback callback);

    void updateAlarm(Alarm alarm);
}

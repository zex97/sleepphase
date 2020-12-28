package com.mse.group1.sleepphase.data.source;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.mse.group1.sleepphase.data.Alarm;

import java.util.List;

@Dao
public interface AlarmDAO {

    @Query("SELECT * FROM alarms WHERE alarmId = :alarmId")
    Alarm getAlarmById(String alarmId);

    @Query("SELECT * FROM alarms ORDER BY name ASC")
    LiveData<List<Alarm>> getAlarms();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAlarm(Alarm alarm);

    @Query("DELETE FROM alarms WHERE alarmId = :alarmId")
    void deleteAlarmById(String alarmId);

    @Update
    void updateAlarm(Alarm alarm);
}

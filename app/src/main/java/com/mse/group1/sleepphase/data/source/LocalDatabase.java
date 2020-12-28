package com.mse.group1.sleepphase.data.source;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.mse.group1.sleepphase.data.Alarm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main database which stores all tables.
 */
@Database(entities = {Alarm.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase INSTANCE;

    public abstract AlarmDAO alarmDAO();

    static final ExecutorService databaseExecutors = Executors.newFixedThreadPool(3);

    private static final Object lock = new Object();

    public static LocalDatabase getInstance(Context context) {
        synchronized (lock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "SleepPhase.db").build();
            }
            return INSTANCE;
        }
    }

}

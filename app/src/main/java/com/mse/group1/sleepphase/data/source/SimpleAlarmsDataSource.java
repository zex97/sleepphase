package com.mse.group1.sleepphase.data.source;

import android.app.Application;
import androidx.annotation.NonNull;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.util.AppExecutors;

import java.util.List;

public class SimpleAlarmsDataSource implements AlarmsDataSource {

    private static volatile SimpleAlarmsDataSource INSTANCE;

    private AppExecutors appExecutors;

    private AlarmDAO alarmDao;

    private SimpleAlarmsDataSource (@NonNull AppExecutors appExecutors, Application application) {
        LocalDatabase database = LocalDatabase.getInstance(application);
        this.alarmDao = database.alarmDAO();
        this.appExecutors = appExecutors;
    }

    public static SimpleAlarmsDataSource getInstance(@NonNull AppExecutors appExecutors, Application application) {
        if (INSTANCE == null) {
            synchronized (SimpleAlarmsDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SimpleAlarmsDataSource(appExecutors, application);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void changeActiveStatusAlarm(@NonNull final String alarmId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                alarmDao.invertActive(alarmId);
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getAlarm(@NonNull final String alarmId, @NonNull final GetAlarmsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Alarm alarm = alarmDao.getAlarmById(alarmId);

                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (alarm == null) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onAlarmLoaded(alarm);
                        }
                    }
                });
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getAlarms(@NonNull final LoadAlarmsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Alarm> alarms = alarmDao.getAlarms();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (alarms.size() != 0) {
                            callback.onAlarmsLoaded(alarms);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };
        LocalDatabase.databaseExecutors.execute(runnable);
    }

    @Override
    public void saveAlarm(@NonNull final Alarm alarm) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                alarmDao.insertAlarm(alarm);
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void updateAlarm(final Alarm alarm) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                alarmDao.updateAlarm(alarm);
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAlarm(@NonNull final String alarmId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                alarmDao.deleteAlarmById(alarmId);
            }
        };

        appExecutors.diskIO().execute(deleteRunnable);
    }


}

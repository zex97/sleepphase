package com.mse.group1.sleepphase.addeditalarm;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.data.source.AlarmsDataSource;
import com.mse.group1.sleepphase.data.source.SimpleAlarmsDataSource;
import com.mse.group1.sleepphase.util.AppExecutors;


public class AddEditViewModel extends AndroidViewModel {

    private AlarmsDataSource alarmsDataSource;

    public final MutableLiveData<Alarm> alarmObservable = new MutableLiveData<>();

    private final MutableLiveData<Event<Object>> alarmUpdated = new MutableLiveData<>();

    private final MutableLiveData<Event<String>> toastText = new MutableLiveData<>();

    private String alarmId;

    private boolean isNewAlarm;

    //true after first load
    private boolean mIsDataLoaded = false;

    private boolean alarmActive = true;

    public AddEditViewModel (@NonNull Application application) {
        super(application);
        alarmsDataSource = SimpleAlarmsDataSource.getInstance(new AppExecutors(), application);
    }

    public void start(String alarmId) {
        this.alarmId = alarmId;
        if (alarmId == null) {
            // new alarm creation
            isNewAlarm = true;
            return;
        }
        if (mIsDataLoaded) {
            return;
        }
        isNewAlarm = false;

        alarmsDataSource.getAlarm(alarmId, new AlarmsDataSource.GetAlarmsCallback() {
            @Override
            public void onAlarmLoaded(Alarm alarm) {
                alarmObservable.setValue(alarm);
                alarmActive = alarm.getActive();
                mIsDataLoaded = true;
            }

            @Override
            public void onDataNotAvailable() {
                //TODO error handling
            }
        });
    }

    // Called after click on last button
    public void saveAlarm() {
        Alarm alarm = alarmObservable.getValue();
        if (alarm.isInvalid()) {
            toastText.setValue(new Event<>("Required field is null or empty."));
            return;
        }
        if (isNewAlarm() || this.alarmId == null) {
            alarmsDataSource.saveAlarm(alarm);
            alarmUpdated.setValue(new Event<>(new Object()));
        } else {
            alarm.setId(this.alarmId);
            alarm.setActive(alarmActive);
            if (isNewAlarm()) {
                throw new RuntimeException("updateAlarm() was called but alarm is new.");
            }
            alarmsDataSource.saveAlarm(alarm);
            alarmUpdated.setValue(new Event<>(new Object()));
        }
    }

    public LiveData<Event<Object>> getAlarmUpdatedObservable() {
        return alarmUpdated;
    }

    public boolean isNewAlarm() {
        return isNewAlarm;
    }
}

package com.mse.group1.sleepphase.alarms;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.*;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.addeditalarm.AddEditAlarmActivity;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.data.source.AlarmsDataSource;
import com.mse.group1.sleepphase.data.source.SimpleAlarmsDataSource;
import com.mse.group1.sleepphase.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class AlarmsViewModel extends AndroidViewModel {

    private AlarmsDataSource alarmsDataSource;

    private final MutableLiveData<List<Alarm>> items = new MutableLiveData<>();

    private final MutableLiveData<Event<String>> toastText = new MutableLiveData<>();

    private MutableLiveData<Event<Object>> createAlarmObservable = new MutableLiveData<>();

    private MutableLiveData<Event<String>> editAlarmObservable = new MutableLiveData<>();

    private LiveData<Boolean> isAlarmsEmpty = Transformations.map(items, new Function<List<Alarm>, Boolean>() {
        @Override
        public Boolean apply(List<Alarm> input) {
            return input.size() == 0;
        }
    });

    public AlarmsViewModel (@NonNull Application application) {
        super(application);
        alarmsDataSource = SimpleAlarmsDataSource.getInstance(new AppExecutors(), application);
    }

    public void loadAlarms() {
        alarmsDataSource.getAlarms(new AlarmsDataSource.LoadAlarmsCallback() {
            @Override
            public void onAlarmsLoaded(List<Alarm> alarms) {
                items.setValue(alarms);
            }

            @Override
            public void onDataNotAvailable() {
                items.setValue(new ArrayList<Alarm>());
            }
        });
    }

    public void changeActiveStatus(String alarmId) {
        alarmsDataSource.changeActiveStatusAlarm(alarmId);
    }

    private void showToastMessage(String message) {
        toastText.setValue(new Event<>(message));
    }

    public void updateAlarm(Alarm alarm) {
        alarmsDataSource.updateAlarm(alarm);
    }

    public void editAlarmObservable(String alarmId) {
        editAlarmObservable.setValue(new Event<>(alarmId));
    }

    public void addNewAlarmObservable() {
        createAlarmObservable.setValue(new Event<>(new Object()));
    }

    public LiveData<Event<String>> getToastText() {
        return toastText;
    }

    public LiveData<List<Alarm>> getItems() {
        return items;
    }

    public LiveData<Boolean> getIsEmpty() {
        return isAlarmsEmpty;
    }

    public LiveData<Boolean> getIsAlarmsEmpty() {
        return isAlarmsEmpty;
    }

    public MutableLiveData<Event<Object>> getCreateAlarmObservable() {
        return createAlarmObservable;
    }

    public MutableLiveData<Event<String>> getEditAlarmObservable() {
        return editAlarmObservable;
    }

    void handleActivityResult(int requestCode, int resultCode) {
        if (AddEditAlarmActivity.REQUEST_CODE == requestCode) {
            if (AddEditAlarmActivity.ADD_EDIT_OK == resultCode) {
                toastText.setValue(new Event<String>("Alarm saved."));
            }
        }
    }

    public void deleteAlarm(final Alarm alarm) {
        alarmsDataSource.deleteAlarm(alarm.getId(), new AlarmsDataSource.GetAfterDeleteCallback() {
            @Override
            public void onAlarmDeleted() {
                alarm.deactivateAlarm(getApplication().getApplicationContext());
                loadAlarms();
            }
        });
    }
}

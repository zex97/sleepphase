package com.mse.group1.sleepphase.checklist;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.data.alarm_components.ChecklistBedtime;
import com.mse.group1.sleepphase.data.source.AlarmsDataSource;
import com.mse.group1.sleepphase.data.source.SimpleAlarmsDataSource;
import com.mse.group1.sleepphase.util.AppExecutors;

import java.util.List;

public class ChecklistViewModel extends AndroidViewModel {

    private AlarmsDataSource alarmsDataSource;
    private String alarmId;

    private final MutableLiveData<List<ChecklistBedtime>> items = new MutableLiveData<>();

    public ChecklistViewModel(@NonNull Application application) {
        super(application);
        alarmsDataSource = SimpleAlarmsDataSource.getInstance(new AppExecutors(), application);
    }

    public void start(final String alarmId) {
        this.alarmId = alarmId;
        alarmsDataSource.getAlarm(alarmId, new AlarmsDataSource.GetAlarmsCallback() {
            @Override
            public void onAlarmLoaded(Alarm alarm) {
                items.setValue(alarm.getChecklist_bedtime());
            }

            @Override
            public void onDataNotAvailable() {
                // TODO - what to do? jump to my alarms??
            }
        });
    }

    public MutableLiveData<List<ChecklistBedtime>> getItems() {
        return items;
    }
}

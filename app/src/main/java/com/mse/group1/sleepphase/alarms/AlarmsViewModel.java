package com.mse.group1.sleepphase.alarms;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.data.source.AlarmsDataSource;

import java.util.List;

public class AlarmsViewModel extends ViewModel {

    private AlarmsDataSource alarmsDataSource;

    private LiveData<List<Alarm>> alarms;

    private LiveData<Boolean> isEmpty = Transformations.map(alarms, new Function<List<Alarm>, Boolean>() {
        @Override
        public Boolean apply(List<Alarm> input) {
            return input.size() == 0;
        }
    });

    private LiveData<String> toastText = new MutableLiveData<>();

    public AlarmsViewModel (AlarmsDataSource alarmsDataSource) {
        this.alarmsDataSource = alarmsDataSource;
        this.alarms = alarmsDataSource.getAlarms();
    }

    public LiveData<List<Alarm>> getAlarms() {
        return alarms;
    }

    public LiveData<Boolean> getIsEmpty() {
        return isEmpty;
    }

    public LiveData<String> getToastText() {
        return toastText;
    }

    public void updateAlarm(Alarm alarm) {
        alarmsDataSource.updateAlarm(alarm);
    }
}

package com.mse.group1.sleepphase.ringing;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.data.source.AlarmsDataSource;
import com.mse.group1.sleepphase.data.source.SimpleAlarmsDataSource;
import com.mse.group1.sleepphase.util.AppExecutors;
import com.mse.group1.sleepphase.util.DateUtils;
import org.joda.time.LocalTime;

import java.util.Calendar;

public class RingingViewModel extends AndroidViewModel {

    private AlarmsDataSource alarmsDataSource;

    private final MutableLiveData<Alarm> alarm = new MutableLiveData<>();

    private final MutableLiveData<String> name = new MutableLiveData<>();

    private final MutableLiveData<String> ringAtString = new MutableLiveData<>();

    private final MutableLiveData<String> dateString = new MutableLiveData<>();

    public MutableLiveData<Boolean> getSnoozeEnabled() {
        return snoozeEnabled;
    }

    private final MutableLiveData<Boolean> snoozeEnabled = new MutableLiveData<>();

    public void start(final String alarmId) {
        alarmsDataSource.getAlarm(alarmId, new AlarmsDataSource.GetAlarmsCallback() {
            @Override
            public void onAlarmLoaded(Alarm alarm) {
                RingingViewModel.this.alarm.setValue(alarm);
                name.setValue(alarm.getName());
                LocalTime ringTime = alarm.getRingAt();
                ringAtString.setValue(ringTime.getHourOfDay() + ":" + ringTime.getMinuteOfHour());
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int today = calendar.get(Calendar.DAY_OF_WEEK);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                dateString.setValue(DateUtils.getDayOfWeekString(today) + " " + DateUtils.getMonthOfYearString(month) + " " + dayOfMonth);
                snoozeEnabled.setValue(alarm.getSnooze_enabled());
            }

            @Override
            public void onDataNotAvailable() {
                System.out.println("RINGING VIEW MODEL: DATA NOT AVAILABLE.");
            }
        });
    }

    public RingingViewModel(@NonNull Application application) {
        super(application);
        alarmsDataSource = SimpleAlarmsDataSource.getInstance(new AppExecutors(), application);
    }

    public MutableLiveData<Alarm> getAlarm() {
        return alarm;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<String> getRingAtString() {
        return ringAtString;
    }

    public MutableLiveData<String> getDateString() {
        return dateString;
    }
}

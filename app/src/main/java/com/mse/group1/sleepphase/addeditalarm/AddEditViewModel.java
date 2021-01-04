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
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;
import com.mse.group1.sleepphase.data.alarm_components.TurningOffTypes;
import com.mse.group1.sleepphase.data.source.AlarmsDataSource;
import com.mse.group1.sleepphase.data.source.SimpleAlarmsDataSource;
import com.mse.group1.sleepphase.util.AppExecutors;

import java.util.ArrayList;
import java.util.Arrays;


public class AddEditViewModel extends AndroidViewModel {

    private AlarmsDataSource alarmsDataSource;

    public final MutableLiveData<Integer> typeSpinnerPosition = new MutableLiveData<>();

    public final MutableLiveData<String> name = new MutableLiveData<>();

    public final MutableLiveData<Integer> ringAtHour = new MutableLiveData<>();
    public final MutableLiveData<Integer> ringAtMinute = new MutableLiveData<>();
    public final MutableLiveData<String> ringAtMode = new MutableLiveData<>();

    public final MutableLiveData<Boolean> mondayActive = new MutableLiveData<>();
    public final MutableLiveData<Boolean> tuesdayActive = new MutableLiveData<>();
    public final MutableLiveData<Boolean> wednesdayActive = new MutableLiveData<>();
    public final MutableLiveData<Boolean> thursdayActive = new MutableLiveData<>();
    public final MutableLiveData<Boolean> fridayActive = new MutableLiveData<>();
    public final MutableLiveData<Boolean> saturdayActive = new MutableLiveData<>();
    public final MutableLiveData<Boolean> sundayActive = new MutableLiveData<>();

    public final MutableLiveData<Boolean> pickReminder = new MutableLiveData<>();
    public final MutableLiveData<Boolean> atReminder = new MutableLiveData<>();
    public final MutableLiveData<Boolean> noReminder = new MutableLiveData<>();

    public final MutableLiveData<String> sound = new MutableLiveData<>(); // not bound to layout yet

    public final MutableLiveData<Boolean> vibrate = new MutableLiveData<>();

    public final MutableLiveData<Integer> volume = new MutableLiveData<>();

    public final MutableLiveData<Boolean> snooze = new MutableLiveData<>();

    public final MutableLiveData<Integer> turningOffSpinnerPosition = new MutableLiveData<>();

    public final MutableLiveData<Integer> turningOffAmount = new MutableLiveData<>();
    public final MutableLiveData<Integer> turningOffDifficulty = new MutableLiveData<>(); // 0,1,2


    public final MutableLiveData<String> difficulty = new MutableLiveData<>();

    public final MutableLiveData<Integer> amount = new MutableLiveData<>();

    //... checklists here


    public final MutableLiveData<Alarm> alarmObservable = new MutableLiveData<>();

    private final MutableLiveData<Event<Object>> alarmUpdated = new MutableLiveData<>();

    private final MutableLiveData<Event<String>> toastText = new MutableLiveData<>();

    private final MutableLiveData<Event<String>> informationDialog = new MutableLiveData<>();


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
                typeSpinnerPosition.setValue(alarm.getType() == AlarmType.REGULAR ? 0 : (alarm.getType() == AlarmType.STEP_BY_STEP ? 1 : 2));
                name.setValue(alarm.getName());
                ringAtHour.setValue(alarm.getRingAt().getHourOfDay());
                ringAtMinute.setValue(alarm.getRingAt().getMinuteOfHour());
                sound.setValue(alarm.getSound());
                vibrate.setValue(alarm.getVibrate());
                volume.setValue(alarm.getVolume());
                snooze.setValue(alarm.getSnooze_enabled());

                mondayActive.setValue(alarm.getDays().contains("Mo"));
                tuesdayActive.setValue(alarm.getDays().contains("Tu"));
                wednesdayActive.setValue(alarm.getDays().contains("We"));
                thursdayActive.setValue(alarm.getDays().contains("Th"));
                fridayActive.setValue(alarm.getDays().contains("Fr"));
                saturdayActive.setValue(alarm.getDays().contains("Sa"));
                sundayActive.setValue(alarm.getDays().contains("Su"));

                TurningOffTypes type = alarm.getTurning_off_alarm().getTypes();
                turningOffSpinnerPosition.setValue(type == TurningOffTypes.SWIPE_OVER_SCREEN ? 0 : (type == TurningOffTypes.MATH_EQUATION ? 1 : 2));
                Integer amount = alarm.getTurning_off_alarm().getAmount();
                turningOffAmount.setValue(type == TurningOffTypes.SWIPE_OVER_SCREEN ? 0 : (type == TurningOffTypes.MATH_EQUATION ? amount-1 : amount/10-1));
                Integer difficulty = alarm.getTurning_off_alarm().getDifficulty();
                turningOffDifficulty.setValue(difficulty);


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

    public MutableLiveData<Event<String>> getToastText() {
        return toastText;
    }

    public void displayInformationDialog(String typeOfDialog) {
        informationDialog.setValue(new Event<>(typeOfDialog));
    }

    public MutableLiveData<Event<String>> getInformationDialog() {
        return informationDialog;
    }
}

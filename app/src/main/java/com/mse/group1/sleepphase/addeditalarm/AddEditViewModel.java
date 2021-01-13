package com.mse.group1.sleepphase.addeditalarm;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;
import com.mse.group1.sleepphase.data.alarm_components.ChecklistBedtime;
import com.mse.group1.sleepphase.data.alarm_components.TurningOffAlarm;
import com.mse.group1.sleepphase.data.alarm_components.TurningOffTypes;
import com.mse.group1.sleepphase.data.source.AlarmsDataSource;
import com.mse.group1.sleepphase.data.source.SimpleAlarmsDataSource;
import com.mse.group1.sleepphase.util.AppExecutors;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;


public class AddEditViewModel extends AndroidViewModel {

    private AlarmsDataSource alarmsDataSource;

    public final MutableLiveData<Integer> typeSpinnerPosition = new MutableLiveData<>();

    public final MutableLiveData<String> name = new MutableLiveData<>();

    public final MutableLiveData<Integer> ringAtHour = new MutableLiveData<>();
    public final MutableLiveData<Integer> ringAtMinute = new MutableLiveData<>();

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

    public final MutableLiveData<Integer> soundSpinnerPosition = new MutableLiveData<>();

    public final MutableLiveData<Boolean> vibrate = new MutableLiveData<>();

    public final MutableLiveData<Integer> volume = new MutableLiveData<>();

    public final MutableLiveData<Boolean> snooze = new MutableLiveData<>();

    public final MutableLiveData<Integer> turningOffSpinnerPosition = new MutableLiveData<>();

    public final MutableLiveData<Integer> turningOffAmount = new MutableLiveData<>();
    public final MutableLiveData<Integer> turningOffDifficulty = new MutableLiveData<>(); // 0,1,2

    private final MutableLiveData<List<ChecklistBedtime>> itemss = new MutableLiveData<>();
    public final MutableLiveData<String> addChecklistField = new MutableLiveData<>();


    private final MutableLiveData<Event<Object>> alarmUpdated = new MutableLiveData<>();

    private final MutableLiveData<Event<String>> toastText = new MutableLiveData<>();

    private final MutableLiveData<Event<String>> informationDialog = new MutableLiveData<>();


    private String alarmId;

    private boolean isNewAlarm;

    //true after first load
    private boolean isDataLoaded = false;

    private boolean alarmActive = true;

    public AddEditViewModel(@NonNull Application application) {
        super(application);
        alarmsDataSource = SimpleAlarmsDataSource.getInstance(new AppExecutors(), application);
    }

    public void start(final String alarmId, AlarmType type) {
        this.alarmId = alarmId;
        if (alarmId == null) {
            // new alarm creation
            name.setValue("");
            ringAtHour.setValue(LocalTime.now().getHourOfDay());
            ringAtMinute.setValue(LocalTime.now().getMinuteOfHour());
            volume.setValue(50);
            vibrate.setValue(false);
            snooze.setValue(false);
            isNewAlarm = true;
            mondayActive.setValue(false);
            tuesdayActive.setValue(false);
            wednesdayActive.setValue(false);
            thursdayActive.setValue(false);
            fridayActive.setValue(false);
            saturdayActive.setValue(false);
            sundayActive.setValue(false);

            turningOffDifficulty.setValue(0);
            turningOffAmount.setValue(0);

            itemss.setValue(new ArrayList<ChecklistBedtime>());
            if (type != null) {
                typeSpinnerPosition.setValue(type == AlarmType.REGULAR ? 0 : (type == AlarmType.STEP_BY_STEP ? 1 : 2));
            }
            return;
        }
        if (isDataLoaded) {
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
                String song = alarm.getSound();
                soundSpinnerPosition.setValue(song.equals("Feeling Good") ? 0 : (song.equals("Strings Galore") ? 1 : 2));
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
                turningOffAmount.setValue(type == TurningOffTypes.SWIPE_OVER_SCREEN ? 0 : amount);
                Integer difficulty = alarm.getTurning_off_alarm().getDifficulty();
                turningOffDifficulty.setValue(difficulty);

                itemss.setValue(alarm.getChecklist_bedtime());


                alarmActive = alarm.getActive();
                isDataLoaded = true;
            }

            @Override
            public void onDataNotAvailable() {
                //TODO error handling
            }
        });
    }

    // Called after click on last button
    public void saveAlarm() {
        Alarm alarm = new Alarm();
        alarm.setActive(true);
        alarm.setType(typeSpinnerPosition.getValue() == 0 ? AlarmType.REGULAR : (typeSpinnerPosition.getValue() == 1 ? AlarmType.STEP_BY_STEP : AlarmType.SKIP_A_NIGHT));
        if (alarm.getType().equals(AlarmType.STEP_BY_STEP)) {
            alarm.setLastChange(LocalDate.now());
        }
        alarm.setName(name.getValue());
        alarm.setRingAt(new LocalTime(ringAtHour.getValue(), ringAtMinute.getValue()));
        alarm.setRecurring(true);
        alarm.setGoal(LocalTime.now()); // TODO goal
        ArrayList<String> daysStrings = makeDaysStrings();
        alarm.setDays(daysStrings);
        alarm.setSkip(LocalDate.now()); //TODO skip
        alarm.setChangeBy(5);           //TODO change by
        alarm.setEveryDays(3);          // TODO everyDays
        alarm.setSound(soundSpinnerPosition.getValue() == 0 ? "Feeling Good" : soundSpinnerPosition.getValue() == 1 ? "Strings Galore" : "Tropical Keys");
        alarm.setVolume(volume.getValue());
        alarm.setVibrate(vibrate.getValue());
        alarm.setSnooze_enabled(snooze.getValue());
        alarm.setSnooze_every_min(3);   // TODO snooze
        alarm.setSnooze_times(1);       //TODO snooze
        TurningOffTypes tot = turningOffSpinnerPosition.getValue() == 0 ? TurningOffTypes.SWIPE_OVER_SCREEN :
                (turningOffSpinnerPosition.getValue() == 1 ? TurningOffTypes.MATH_EQUATION : TurningOffTypes.SHAKE_THE_PHONE);
        alarm.setTurning_off_alarm(new TurningOffAlarm(tot, turningOffDifficulty.getValue(), turningOffAmount.getValue()));
        alarm.setChecklist_bedtime(itemss.getValue());
        if (isNewAlarm() || this.alarmId == null) {
            alarmsDataSource.saveAlarm(alarm);
            alarmUpdated.setValue(new Event<>(new Object()));
            alarm.activateAlarm(getApplication());                   // DAL?
        } else {
            alarm.setId(this.alarmId);
            alarm.setActive(alarmActive);
            alarmsDataSource.saveAlarm(alarm);
            alarmUpdated.setValue(new Event<>(new Object()));
            alarm.deactivateAlarm(getApplication());
            alarm.activateAlarm(getApplication());
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

    public LiveData<List<ChecklistBedtime>> getItemss() {
        return itemss;
    }

    public void addItemToChecklist() {
        List<ChecklistBedtime> list = itemss.getValue();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(new ChecklistBedtime(addChecklistField.getValue(), false));
        itemss.setValue(list);
        addChecklistField.setValue("");
    }

    private ArrayList<String> makeDaysStrings() {
        ArrayList<String> strings = new ArrayList<>();
        if (mondayActive.getValue()) {
            strings.add("Mo");
        }
        if (tuesdayActive.getValue()) {
            strings.add("Tu");
        }
        if (wednesdayActive.getValue()) {
            strings.add("We");
        }
        if (thursdayActive.getValue()) {
            strings.add("Th");
        }
        if (fridayActive.getValue()) {
            strings.add("Fr");
        }
        if (saturdayActive.getValue()) {
            strings.add("Sa");
        }
        if (sundayActive.getValue()) {
            strings.add("Su");
        }
        if (strings.size() == 0) {
            int day = LocalDate.now().getDayOfWeek();
            if (ringAtHour.getValue() < LocalTime.now().getHourOfDay() ||
                    (ringAtHour.getValue() == LocalTime.now().getHourOfDay() && ringAtMinute.getValue() < LocalTime.now().getMinuteOfHour())) {
                day = (day + 1) % 7;
            }
            if (day == 1) {
                strings.add("Mo");
            }
            if (day == 2) {
                strings.add("Tu");
            }
            if (day == 3) {
                strings.add("We");
            }
            if (day == 4) {
                strings.add("Th");
            }
            if (day == 5) {
                strings.add("Fr");
            }
            if (day == 6) {
                strings.add("Sa");
            }
            if (day == 7) {
                strings.add("Su");
            }
        }
        return strings;
    }
}

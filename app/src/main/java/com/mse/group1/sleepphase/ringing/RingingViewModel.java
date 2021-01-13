package com.mse.group1.sleepphase.ringing;

import android.app.Application;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.data.alarm_components.TurningOffTypes;
import com.mse.group1.sleepphase.data.source.AlarmsDataSource;
import com.mse.group1.sleepphase.data.source.SimpleAlarmsDataSource;
import com.mse.group1.sleepphase.util.AppExecutors;
import com.mse.group1.sleepphase.util.DateUtils;
import org.joda.time.LocalTime;

import java.util.Calendar;
import java.util.Locale;

public class RingingViewModel extends AndroidViewModel {

    private AlarmsDataSource alarmsDataSource;

    private final MutableLiveData<Alarm> alarm = new MutableLiveData<>();

    private final MutableLiveData<String> name = new MutableLiveData<>();

    private final MutableLiveData<String> ringAtString = new MutableLiveData<>();

    private final MutableLiveData<String> dateString = new MutableLiveData<>();

    private final MutableLiveData<String> turningOffType = new MutableLiveData<>();

    private final MutableLiveData<Boolean> snoozeEnabled = new MutableLiveData<>();

    private final MutableLiveData<String> randomEquation = new MutableLiveData<>();

    private final MutableLiveData<String> typedSolution = new MutableLiveData<>();

    private final MutableLiveData<Integer> progress = new MutableLiveData<>();

    private final MutableLiveData<Integer> amount = new MutableLiveData<>();

    private final MutableLiveData<Integer> difficulty = new MutableLiveData<>();

    private final MutableLiveData<Boolean> invalidAnswer = new MutableLiveData<>();

    private final MutableLiveData<Integer> shakeCount = new MutableLiveData<>();

    private final MutableLiveData<Event<Object>> shakeCountAchieved = new MutableLiveData<>();

    private final MutableLiveData<Event<Object>> alarmLoadedObservable = new MutableLiveData<>();



    public void start(final String alarmId) {
        shakeCount.setValue(0);
        alarmsDataSource.getAlarm(alarmId, new AlarmsDataSource.GetAlarmsCallback() {
            @Override
            public void onAlarmLoaded(Alarm alarm) {
                RingingViewModel.this.alarm.setValue(alarm);
                name.setValue(alarm.getName());
                LocalTime ringTime = alarm.getRingAt();
                ringAtString.setValue(String.format(Locale.ENGLISH, "%02d:%02d",ringTime.getHourOfDay(), ringTime.getMinuteOfHour()));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int today = calendar.get(Calendar.DAY_OF_WEEK);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                dateString.setValue(DateUtils.getDayOfWeekString(today) + " " + DateUtils.getMonthOfYearString(month) + " " + dayOfMonth);

                turningOffType.setValue(alarm.getTurning_off_alarm().getTypes().toString());
                snoozeEnabled.setValue(alarm.getSnooze_enabled());
                if (alarm.getTurning_off_alarm().getTypes() == TurningOffTypes.MATH_EQUATION) {
                    randomEquation.setValue(EquationGenerator.generateEquation(alarm.getTurning_off_alarm().getDifficulty()));
                }
                typedSolution.setValue("");
                progress.setValue(1);
                if (turningOffType.getValue().equals("SHAKE_THE_PHONE")) {
                    amount.setValue((alarm.getTurning_off_alarm().getAmount() + 1) * 10);
                } else {
                    amount.setValue(alarm.getTurning_off_alarm().getAmount() + 1);
                }
                difficulty.setValue(alarm.getTurning_off_alarm().getDifficulty());
                invalidAnswer.setValue(false);

                alarmLoadedObservable.setValue(new Event<>(new Object()));
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

    public MutableLiveData<Boolean> getSnoozeEnabled() {
        return snoozeEnabled;
    }

    public MutableLiveData<String> getTurningOffType() {
        return turningOffType;
    }

    public MutableLiveData<String> getRandomEquation() {
        return randomEquation;
    }

    public MutableLiveData<String> getTypedSolution() {
        return typedSolution;
    }

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }

    public MutableLiveData<Integer> getAmount() {
        return amount;
    }

    public MutableLiveData<Boolean> getInvalidAnswer() {
        return invalidAnswer;
    }

    public MutableLiveData<Integer> getShakeCount() {
        return shakeCount;
    }

    public void incrementShakeCount () {
        shakeCount.setValue(shakeCount.getValue() + 1);
        if (shakeCount.getValue() >= amount.getValue()) {
            shakeCountAchieved.setValue(new Event<>(new Object()));
        }
    }

    public void equationAdd(String s) {
        invalidAnswer.setValue(false);
        if (typedSolution.getValue().length() > 5) {
            return;
        }
        if (s.equals("-") && typedSolution.getValue().length() > 0) {
            return;
        }
        typedSolution.setValue(typedSolution.getValue() + s);
    }

    public void equationRemoveOne() {
        invalidAnswer.setValue(false);
        String solution = typedSolution.getValue();
        if (solution.length() > 0) {
            solution = solution.substring(0, solution.length()-1);
        }
        typedSolution.setValue(solution);
    }

    // returns true if final equation solution is valid
    public boolean checkEquation () {
        int currentProgress = progress.getValue();
        if (EquationGenerator.parseSolution(randomEquation.getValue()) == Integer.parseInt(typedSolution.getValue())) {
            if (currentProgress == amount.getValue()) {
                return true;
            } else {
                progress.setValue(currentProgress + 1);
                typedSolution.setValue("");
                randomEquation.setValue(EquationGenerator.generateEquation(difficulty.getValue()));
                return false;
            }
        }
        invalidAnswer.setValue(true);
        return false;
    }

    public MutableLiveData<Event<Object>> getShakeCountAchieved() {
        return shakeCountAchieved;
    }

    public MutableLiveData<Event<Object>> getAlarmLoadedObservable() {
        return alarmLoadedObservable;
    }

    public MutableLiveData<Integer> getDifficulty() {
        return difficulty;
    }
}

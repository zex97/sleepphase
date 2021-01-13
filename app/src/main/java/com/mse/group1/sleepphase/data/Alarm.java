package com.mse.group1.sleepphase.data;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.*;
import com.mse.group1.sleepphase.data.alarm_components.*;
import com.mse.group1.sleepphase.ringing.AlarmReceiver;
import org.joda.time.LocalTime;
import org.joda.time.LocalDate;

import java.util.*;

import static com.mse.group1.sleepphase.ringing.AlarmReceiver.*;

@Entity(tableName = "alarms")
@TypeConverters(AlarmConverters.class)
public final class Alarm {

    public Alarm () {
        this.id = UUID.randomUUID().toString();
    }

    public void activateAlarm(Context context) {
        this.active = true;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(ALARM_ID, getId());
        intent.putExtra(DAYS, getDaysForScheduler());
        intent.putExtra(TITLE, getName());
        intent.putExtra(SONG, getSound());
//        intent.putExtra(IS_RECURRING, getRecurring());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, getId().hashCode(), intent, 0);

        Calendar ringTime = Calendar.getInstance();
        ringTime.setTimeInMillis(System.currentTimeMillis());
        ringTime.set(Calendar.HOUR_OF_DAY, ringAt.getHourOfDay());
        ringTime.set(Calendar.MINUTE, ringAt.getMinuteOfHour());
        ringTime.set(Calendar.SECOND, 0);
        ringTime.set(Calendar.MILLISECOND, 0);

        if (ringTime.getTimeInMillis() <= System.currentTimeMillis()) {
            ringTime.set(Calendar.DAY_OF_MONTH, ringTime.get(Calendar.DAY_OF_MONTH) + 1);
        }

        /* Alarms created with setRepeating aren't exact (they can be delayed for a couple minutes) so for demonstration purposes we will only
        create one-off alarms using setExact() */
//        if (recurring) {
//            final long mseconds = 1000 * 60 * 60 * 24;
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), mseconds, alarmPendingIntent);
//        }
        String toastString = String.format(Locale.ENGLISH, "Alarm %s scheduled for %02d:%02d.", name, ringAt.getHourOfDay(), ringAt.getMinuteOfHour());
        Toast.makeText(context, toastString, Toast.LENGTH_LONG).show();
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, ringTime.getTimeInMillis(), pendingIntent);
    }

    public void deactivateAlarm(Context context) {
        this.active = false;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, getId().hashCode(), intent, 0);
        alarmManager.cancel(pendingIntent);

        String string = String.format(Locale.ENGLISH,"Alarm %s canceled for %02d:%02d", name, ringAt.getHourOfDay(), ringAt.getMinuteOfHour());
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "alarmId")
    private String id;

    @NonNull
    @ColumnInfo(name = "active")
    private Boolean active;

    @NonNull
    @ColumnInfo(name = "type")
    private AlarmType type;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "ringAt")
    private LocalTime ringAt;

    @NonNull
    @ColumnInfo(name = "recurring")
    private Boolean recurring;

    @Nullable
    @ColumnInfo(name = "goal")
    private LocalTime goal;                   // step by step and skip a night alarms

    @NonNull
    @ColumnInfo(name = "days")
    private ArrayList<String> days;

    @Nullable
    @ColumnInfo(name = "skip")
    private LocalDate skip;                   //only skip a night alarms

    @Nullable
    @ColumnInfo(name = "changeBy")
    private Integer changeBy;            //only step by step alarms

    @Nullable
    @ColumnInfo(name = "everyDays")
    private Integer everyDays;           //only step by step alarms

    @Nullable
    @ColumnInfo(name = "lastChange")
    private LocalDate lastChange;       // only step by step

    @NonNull
    @ColumnInfo(name = "sound")
    private String sound;

    @NonNull
    @ColumnInfo(name = "volume")
    private Integer volume;

    @NonNull
    @ColumnInfo(name = "vibrate")
    private Boolean vibrate;

    @NonNull
    @ColumnInfo(name = "snooze_enabled")
    private Boolean snooze_enabled;

    @NonNull
    @ColumnInfo(name = "snooze_every_min")
    private Integer snooze_every_min;

    @NonNull
    @ColumnInfo(name = "snooze_times")
    private Integer snooze_times;

    @NonNull
    @ColumnInfo(name = "turning_off_alarm")
    private TurningOffAlarm turning_off_alarm;

    @NonNull
    @ColumnInfo(name = "checklist_bedtime")
    private List<ChecklistBedtime> checklist_bedtime;

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public Boolean getActive() {
        return active;
    }

    @NonNull
    public AlarmType getType() {
        return type;
    }

    @Ignore
    public String getTypeForList() {
        if (type == AlarmType.REGULAR) {
            return "Regular";
        } else if (type == AlarmType.SKIP_A_NIGHT) {
            return "Skip a Night";
        } else {
            return "Step by Step";
        }
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public LocalTime getRingAt() {
        return ringAt;
    }

    @Ignore
    public String getRingAtForList () {
        return ringAt.toString("HH:mm");
    }

    @NonNull
    public Boolean getRecurring() {
        return recurring;
    }

    @Nullable
    public LocalTime getGoal() {
        return goal;
    }

    @Ignore
    public String getGoalForList () {
        return goal.toString("HH:mm");
    }


    @NonNull
    public ArrayList<String> getDays() {
        return days;
    }

    @Ignore
    public String getDaysForList () {
        if (days.size() == 7) {
            return "Everyday";
        }
        String s = "";
        for (String day : days) {
            s += day + " ";
        }
        return s;
    }

    @Ignore
    public String getDaysForScheduler () {
        String s = "";
        for (String day : days) {
            s += day + " ";
        }
        return s;
    }

    @Nullable
    public LocalDate getSkip() {
        return skip;
    }

    @Nullable
    public Integer getChangeBy() {
        return changeBy;
    }

    @Nullable
    public Integer getEveryDays() {
        return everyDays;
    }

    @NonNull
    public Integer getVolume() {
        return volume;
    }

    @NonNull
    public String getSound() {
        return sound;
    }

    @NonNull
    public Boolean getVibrate() {
        return vibrate;
    }

    @NonNull
    public Boolean getSnooze_enabled() {
        return snooze_enabled;
    }

    @NonNull
    public Integer getSnooze_every_min() {
        return snooze_every_min;
    }

    @NonNull
    public Integer getSnooze_times() {
        return snooze_times;
    }

    @NonNull
    public TurningOffAlarm getTurning_off_alarm() {
        return turning_off_alarm;
    }

    @Ignore
    public String getTurningOffAlarmForList() {
        if (turning_off_alarm.getTypes() == TurningOffTypes.SWIPE_OVER_SCREEN) {
            return "Swipe screen";
        } else if (turning_off_alarm.getTypes() == TurningOffTypes.MATH_EQUATION) {
            return "Math exercise";
        } else {
            return "Shake phone";
        }
    }

    @Nullable
    public LocalDate getLastChange() {
        return lastChange;
    }

    @NonNull
    public List<ChecklistBedtime> getChecklist_bedtime() {
        return checklist_bedtime;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setActive(@NonNull Boolean active) {
        this.active = active;
    }

    public void setType(@NonNull AlarmType type) {
        this.type = type;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setRingAt(@NonNull LocalTime ringAt) {
        this.ringAt = ringAt;
    }

    public void setRecurring(@NonNull Boolean recurring) {
        this.recurring = recurring;
    }

    public void setGoal(@Nullable LocalTime goal) {
        this.goal = goal;
    }

    public void setDays(@NonNull ArrayList<String> days) {
        this.days = days;
    }

    public void setSkip(@Nullable LocalDate skip) {
        this.skip = skip;
    }

    public void setChangeBy(@Nullable Integer changeBy) {
        this.changeBy = changeBy;
    }

    public void setEveryDays(@Nullable Integer everyDays) {
        this.everyDays = everyDays;
    }

    public void setSound(@NonNull String sound) {
        this.sound = sound;
    }

    public void setVolume(@NonNull Integer volume) {
        this.volume = volume;
    }

    public void setVibrate(@NonNull Boolean vibrate) {
        this.vibrate = vibrate;
    }

    public void setSnooze_enabled(@NonNull Boolean snooze_enabled) {
        this.snooze_enabled = snooze_enabled;
    }

    public void setSnooze_every_min(@NonNull Integer snooze_every_min) {
        this.snooze_every_min = snooze_every_min;
    }

    public void setSnooze_times(@NonNull Integer snooze_times) {
        this.snooze_times = snooze_times;
    }

    public void setTurning_off_alarm(@NonNull TurningOffAlarm turning_off_alarm) {
        this.turning_off_alarm = turning_off_alarm;
    }

    public void setChecklist_bedtime(@NonNull List<ChecklistBedtime> checklist_bedtime) {
        this.checklist_bedtime = checklist_bedtime;
    }

    public void setLastChange(@Nullable LocalDate lastChange) {
        this.lastChange = lastChange;
    }

    @NonNull
    @Override
    public String toString() {
        return "Alarm: " + active + ", " + type + ", " + name + ", " + ringAt + ", " + recurring + ", " + goal + ", " + days + ", " + skip + ", " +
                changeBy + ", " + everyDays + ", " + sound + ", " + volume + ", " + vibrate + ", " + snooze_enabled + ", " + snooze_every_min + ", " +
                snooze_times + ", " + turning_off_alarm + ", " + checklist_bedtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarm alarm = (Alarm) o;
        if (!Objects.equals(type, alarm.type)) return false;
        if (!Objects.equals(active, alarm.active)) return false;
        if (!Objects.equals(name, alarm.name)) return false;
        if (!Objects.equals(ringAt, alarm.ringAt)) return false;
        if (!Objects.equals(recurring, alarm.recurring)) return false;
        if (!Objects.equals(goal, alarm.goal)) return false;
        if (!Objects.equals(days, alarm.days)) return false;
        if (!Objects.equals(skip, alarm.skip)) return false;
        if (!Objects.equals(lastChange, alarm.lastChange)) return false;
        if (!Objects.equals(changeBy, alarm.changeBy)) return false;
        if (!Objects.equals(everyDays, alarm.everyDays)) return false;
        if (!Objects.equals(sound, alarm.sound)) return false;
        if (!Objects.equals(volume, alarm.volume)) return false;
        if (!Objects.equals(vibrate, alarm.vibrate)) return false;
        if (!Objects.equals(snooze_enabled, alarm.snooze_enabled)) return false;
        if (!Objects.equals(snooze_every_min, alarm.snooze_every_min)) return false;
        if (!Objects.equals(snooze_times, alarm.snooze_times)) return false;
        if (!Objects.equals(turning_off_alarm, alarm.turning_off_alarm)) return false;
        return Objects.equals(checklist_bedtime, alarm.checklist_bedtime);
    }


    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (ringAt != null ? ringAt.hashCode() : 0);
        result = 31 * result + (recurring != null ? recurring.hashCode() : 0);
        result = 31 * result + (goal != null ? goal.hashCode() : 0);
        result = 31 * result + (lastChange != null ? lastChange.hashCode() : 0);
        result = 31 * result + (days != null ? days.hashCode() : 0);
        result = 31 * result + (skip != null ? skip.hashCode() : 0);
        result = 31 * result + (changeBy != null ? changeBy.hashCode() : 0);
        result = 31 * result + (everyDays != null ? everyDays.hashCode() : 0);
        result = 31 * result + (sound != null ? sound.hashCode() : 0);
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        result = 31 * result + (vibrate != null ? vibrate.hashCode() : 0);
        result = 31 * result + (snooze_enabled != null ? snooze_enabled.hashCode() : 0);
        result = 31 * result + (snooze_every_min != null ? snooze_every_min.hashCode() : 0);
        result = 31 * result + (snooze_times != null ? snooze_times.hashCode() : 0);
        result = 31 * result + (turning_off_alarm != null ? turning_off_alarm.hashCode() : 0);
        result = 31 * result + (checklist_bedtime != null ? checklist_bedtime.hashCode() : 0);
        return result;
    }
}

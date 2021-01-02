package com.mse.group1.sleepphase.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.*;
import com.google.common.base.Strings;
import com.mse.group1.sleepphase.data.alarm_components.AlarmConverters;
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;
import com.mse.group1.sleepphase.data.alarm_components.ChecklistBedtimeWakeup;
import com.mse.group1.sleepphase.data.alarm_components.TurningOffAlarm;
import org.joda.time.LocalTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "alarms")
@TypeConverters(AlarmConverters.class)
public final class Alarm {

    public Alarm () {
        this.id = UUID.randomUUID().toString();
    }

    @Ignore
    public void setAlarm (Alarm alarm) {
        this.id = alarm.id;
        this.active = alarm.active;
        this.type = alarm.type;
        this.name = alarm.name;
        this.ringAt = alarm.ringAt;
        this.goal = alarm.goal;
        this.days = alarm.days;
        this.skip = alarm.skip;
        this.changeBy = alarm.changeBy;
        this.everyDays = alarm.everyDays;
        this.sound = alarm.sound;
        this.vibrate = alarm.vibrate;
        this.snooze_times = alarm.snooze_times;
        this.snooze_every_min = alarm.snooze_every_min;
        this.snooze_enabled = alarm.snooze_enabled;
        this.turning_off_alarm = alarm.turning_off_alarm;
        this.checklist_bedtime = alarm.checklist_bedtime;
        this.checklist_wakeup = alarm.checklist_wakeup;
    }

    @Ignore
    public boolean isInvalid () {
        return Strings.isNullOrEmpty(name);
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

    @NonNull
    @ColumnInfo(name = "sound")
    private String sound;

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
    private ArrayList<ChecklistBedtimeWakeup> checklist_bedtime;

    @NonNull
    @ColumnInfo(name = "checklist_wakeup")
    private ArrayList<ChecklistBedtimeWakeup> checklist_wakeup;

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
        return type.toString().replace("_"," ");
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

    @NonNull
    public ArrayList<ChecklistBedtimeWakeup> getChecklist_bedtime() {
        return checklist_bedtime;
    }

    @NonNull
    public ArrayList<ChecklistBedtimeWakeup> getChecklist_wakeup() {
        return checklist_wakeup;
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

    public void setChecklist_bedtime(@NonNull ArrayList<ChecklistBedtimeWakeup> checklist_bedtime) {
        this.checklist_bedtime = checklist_bedtime;
    }

    public void setChecklist_wakeup(@NonNull ArrayList<ChecklistBedtimeWakeup> checklist_wakeup) {
        this.checklist_wakeup = checklist_wakeup;
    }

    @NonNull
    @Override
    public String toString() {
        return "Alarm: " + active + ", " + type + ", " + name + ", " + ringAt + ", " + goal + ", " + days + ", " + skip + ", " +
                changeBy + ", " + everyDays + ", " + sound + ", " + vibrate + ", " + snooze_enabled + ", " + snooze_every_min + ", " +
                snooze_times + ", " + turning_off_alarm + ", " + checklist_bedtime + ", " + checklist_wakeup;
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
        if (!Objects.equals(goal, alarm.goal)) return false;
        if (!Objects.equals(days, alarm.days)) return false;
        if (!Objects.equals(skip, alarm.skip)) return false;
        if (!Objects.equals(changeBy, alarm.changeBy)) return false;
        if (!Objects.equals(everyDays, alarm.everyDays)) return false;
        if (!Objects.equals(sound, alarm.sound)) return false;
        if (!Objects.equals(vibrate, alarm.vibrate)) return false;
        if (!Objects.equals(snooze_enabled, alarm.snooze_enabled)) return false;
        if (!Objects.equals(snooze_every_min, alarm.snooze_every_min)) return false;
        if (!Objects.equals(snooze_times, alarm.snooze_times)) return false;
        if (!Objects.equals(turning_off_alarm, alarm.turning_off_alarm)) return false;
        if (!Objects.equals(checklist_bedtime, alarm.checklist_bedtime)) return false;
        return Objects.equals(checklist_wakeup, alarm.checklist_wakeup);
    }


    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (ringAt != null ? ringAt.hashCode() : 0);
        result = 31 * result + (goal != null ? goal.hashCode() : 0);
        result = 31 * result + (days != null ? days.hashCode() : 0);
        result = 31 * result + (skip != null ? skip.hashCode() : 0);
        result = 31 * result + (changeBy != null ? changeBy.hashCode() : 0);
        result = 31 * result + (everyDays != null ? everyDays.hashCode() : 0);
        result = 31 * result + (sound != null ? sound.hashCode() : 0);
        result = 31 * result + (vibrate != null ? vibrate.hashCode() : 0);
        result = 31 * result + (snooze_enabled != null ? snooze_enabled.hashCode() : 0);
        result = 31 * result + (snooze_every_min != null ? snooze_every_min.hashCode() : 0);
        result = 31 * result + (snooze_times != null ? snooze_times.hashCode() : 0);
        result = 31 * result + (turning_off_alarm != null ? turning_off_alarm.hashCode() : 0);
        result = 31 * result + (checklist_bedtime != null ? checklist_bedtime.hashCode() : 0);
        result = 31 * result + (checklist_wakeup != null ? checklist_wakeup.hashCode() : 0);
        return result;
    }
}

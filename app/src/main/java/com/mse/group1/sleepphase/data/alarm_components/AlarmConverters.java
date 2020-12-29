package com.mse.group1.sleepphase.data.alarm_components;

import androidx.room.TypeConverter;
import org.joda.time.LocalTime;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class AlarmConverters {

    @TypeConverter
    public AlarmType toAlarmType(String value) {
        return Enum.valueOf(AlarmType.class, value);
    }

    @TypeConverter
    public String fromAlarmType(AlarmType value) {
        return value.toString();
    }

    @TypeConverter
    public LocalTime toTime(String value) {
        return LocalTime.parse(value);
    }

    @TypeConverter
    public String fromTime(LocalTime value) {
        return value.toString();
    }

    @TypeConverter
    public LocalDate toDate(String value) {
        DateTimeFormatter format = DateTimeFormat.forPattern("dd-MM-yyyy");
        return format.parseLocalDate(value);
    }

    @TypeConverter
    public String fromDate(LocalDate value) {
        DateTimeFormatter format = DateTimeFormat.forPattern("dd-MM-yyyy");
        return format.print(value);
    }

    @TypeConverter
    public TurningOffAlarm toTurningOffAlarm(String value) {
        String[] strings = value.split(" ");
        return new TurningOffAlarm(Enum.valueOf(TurningOffTypes.class, strings[0]), Integer.valueOf(strings[1]), Integer.valueOf(strings[2]));
    }

    @TypeConverter
    public String fromTurningOffAlarm(TurningOffAlarm value) {
        return value.getTypes().toString() + " " + value.getAmount() + " " + value.getDifficulty();
    }

    @TypeConverter
    public ArrayList<ChecklistBedtimeWakeup> toArrayListChecklistBedtimeWakeup(String value) {
        ArrayList<ChecklistBedtimeWakeup> list = new ArrayList<>();
        String[] strings = value.split(",");
        for (int i = 0; i < strings.length; i++) {
            list.add(ChecklistBedtimeWakeup.fromString(strings[i]));
        }
        return list;
    }

    @TypeConverter
    public String fromArrayListChecklistBedtimeWakeup(ArrayList<ChecklistBedtimeWakeup> value) {
        String string = "";
        for (ChecklistBedtimeWakeup item : value) {
            string += item.toString() + ",";
        }
        return string;
    }

    @TypeConverter
    public ArrayList<String> toArrayListDays(String value) {
        ArrayList<String> list = new ArrayList<>();
        String[] strings = value.split(",");
        Collections.addAll(list, strings);
        return list;
    }

    @TypeConverter
    public String fromArrayListDays(ArrayList<String> value) {
        String string = "";
        for (String item : value) {
            string += item + ",";
        }
        return string;
    }


}

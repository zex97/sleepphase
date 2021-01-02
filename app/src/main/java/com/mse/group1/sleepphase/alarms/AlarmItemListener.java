package com.mse.group1.sleepphase.alarms;

import com.mse.group1.sleepphase.data.Alarm;

public interface AlarmItemListener {

    void onSwitchChange(Alarm alarm);

    void onAlarmClicked(Alarm alarm);
}

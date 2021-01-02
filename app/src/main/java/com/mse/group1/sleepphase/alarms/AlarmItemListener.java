package com.mse.group1.sleepphase.alarms;

import android.view.View;
import com.mse.group1.sleepphase.data.Alarm;

public interface AlarmItemListener {

    void onSwitchChange(Alarm alarm, View view);

    void onAlarmClicked(Alarm alarm);
}

package com.mse.group1.sleepphase.alarms;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.data.Alarm;

import java.util.Locale;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private TextView alarmTime;
    private TextView alarmGoal;
    private TextView alarmName;
    private TextView alarmType;
    private Switch alarmActive;

    private SwitchChangeListener changeListener;

    public AlarmViewHolder(@NonNull View itemView, SwitchChangeListener changeListener) {
        super(itemView);

        alarmTime = itemView.findViewById(R.id.alarmTimeTextView);
        alarmGoal = itemView.findViewById(R.id.alarmGoalTextView);
        alarmName = itemView.findViewById(R.id.alarmNameTextView);
        alarmType = itemView.findViewById(R.id.alarmTypeTextView);
        alarmActive = itemView.findViewById(R.id.myAlarmsSwitch);

        this.changeListener = changeListener;
    }

    public void bind(final Alarm alarm) {
        String alarmTimeString = String.format(Locale.ENGLISH, "%02d:%02d", alarm.getRingAt().getHourOfDay(), alarm.getRingAt().getMinuteOfHour());
        alarmTime.setText(alarmTimeString);

        alarmActive.setChecked(alarm.getActive());

        alarmType.setText(alarm.getType().toString());

        alarmName.setText(alarm.getName());

        if (alarm.getGoal() != null) {
            alarmGoal.setText(alarm.getGoal().toString());
        }


        alarmActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeListener.onSwitchChange(alarm);
            }
        });
    }

    public Switch getAlarmActive() {
        return alarmActive;
    }
}

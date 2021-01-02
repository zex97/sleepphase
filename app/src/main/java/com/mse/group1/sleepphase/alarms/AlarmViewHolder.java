package com.mse.group1.sleepphase.alarms;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.databinding.AlarmItemBinding;

import java.util.Locale;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private TextView alarmTime;
    private TextView alarmGoal;
    private TextView alarmName;
    private TextView alarmType;
    private Switch alarmActive;

    private AlarmItemBinding binding;

    private AlarmItemListener changeListener;

    public AlarmViewHolder(@NonNull View itemView, final AlarmItemBinding binding) {
        super(itemView);

        alarmTime = itemView.findViewById(R.id.alarmTimeTextView);
        alarmGoal = itemView.findViewById(R.id.alarmGoalTextView);
        alarmName = itemView.findViewById(R.id.alarmNameTextView);
        alarmType = itemView.findViewById(R.id.alarmTypeTextView);
        alarmActive = itemView.findViewById(R.id.myAlarmsSwitch);
        this.binding = binding;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // POKUSATI DIREKTNO DA  PROMIJENIS OBSERVABLE, ako ne direktno da pozoves edit, ko ga jebe
                changeListener.onAlarmClicked(binding.getAlarm());
            }
        });
    }

    public void bind(final Alarm alarm, final AlarmItemListener changeListener) {
        this.changeListener = changeListener;

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

        binding.setAlarm(alarm);
        binding.setListener(this.changeListener);
        binding.executePendingBindings();
    }

    public Switch getAlarmActive() {
        return alarmActive;
    }
}

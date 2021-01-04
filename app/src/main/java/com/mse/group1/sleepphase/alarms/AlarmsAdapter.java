package com.mse.group1.sleepphase.alarms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.databinding.AlarmItemBinding;

import java.util.List;

public class AlarmsAdapter extends BaseAdapter {

    private final AlarmsViewModel viewModel;

    private List<Alarm> alarmList;

    private LifecycleOwner lifecycleOwner;

    public AlarmsAdapter(List<Alarm> alarms, AlarmsViewModel viewModel, LifecycleOwner activity) {
        this.viewModel = viewModel;
        setList(alarms);
        lifecycleOwner = activity;

    }

    @Override
    public View getView(int position, final View view, final ViewGroup viewGroup) {
        AlarmItemBinding binding;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            binding = AlarmItemBinding.inflate(inflater, viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(view);
        }

        AlarmItemListener listener = new AlarmItemListener() {
            @Override
            public void onSwitchChange(Alarm alarm, View v) {
//                boolean active = ((Switch)v).isChecked();
                // TODO   viewModel.... schedule
            }

            @Override
            public void onAlarmClicked(Alarm alarm) {
                viewModel.editAlarmObservable(alarm.getId());
            }
        };

        binding.setAlarm(alarmList.get(position));
        binding.setLifecycleOwner(lifecycleOwner);
        binding.setListener(listener);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    private void setList(List<Alarm> alarms) {
        alarmList = alarms;
        notifyDataSetChanged();
    }

    @Override
    public Alarm getItem(int position) {
        return alarmList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void replaceData(List<Alarm> alarms) {
        setList(alarms);
    }

    @Override
    public int getCount() {
        if (alarmList == null) {
            return 0;
        } else {
            return alarmList.size();
        }
    }
}


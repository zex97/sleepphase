package com.mse.group1.sleepphase.alarms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.Switch;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import com.google.common.collect.Collections2;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.databinding.AlarmItemBinding;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlarmsAdapter extends BaseAdapter {

    private final AlarmsViewModel viewModel;

    private List<Alarm> alarmList;

    private LifecycleOwner lifecycleOwner;

    private AlarmItemListener listener;

    public AlarmsAdapter(List<Alarm> alarms, AlarmsViewModel viewModel, LifecycleOwner activity, AlarmItemListener listener) {
        this.viewModel = viewModel;
        setList(alarms);
        lifecycleOwner = activity;
        this.listener = listener;
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

        binding.setAlarm(alarmList.get(position));
        binding.setLifecycleOwner(lifecycleOwner);
        binding.setListener(listener);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    private void setList(List<Alarm> alarms) {
        Comparator<Alarm> compareByTime = new Comparator<Alarm>() {
            @Override
            public int compare(Alarm o1, Alarm o2) {
                return o1.getRingAt().compareTo(o2.getRingAt());
            }
        };
        if (alarms != null) {
            Collections.sort(alarms, compareByTime);
        }
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


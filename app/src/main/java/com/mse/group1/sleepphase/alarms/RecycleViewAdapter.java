package com.mse.group1.sleepphase.alarms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.databinding.AlarmItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<AlarmViewHolder> {
    private List<Alarm> alarms;
    private AlarmItemListener changeListener;

    public RecycleViewAdapter(AlarmItemListener listener) {
        this.alarms = new ArrayList<>();
        this.changeListener = listener;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        AlarmItemBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Create the binding
        binding = AlarmItemBinding.inflate(inflater, parent, false);
        return new AlarmViewHolder(itemView, binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        holder.bind(alarm,changeListener);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(@NonNull AlarmViewHolder holder) {
        super.onViewRecycled(holder);
        holder.getAlarmActive().setOnCheckedChangeListener(null);
    }
}

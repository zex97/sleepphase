package com.mse.group1.sleepphase.addeditalarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import com.mse.group1.sleepphase.data.alarm_components.ChecklistBedtime;
import com.mse.group1.sleepphase.databinding.ChecklistItemBinding;

import java.util.List;

public class ChecklistAdapter extends BaseAdapter {

    private final AddEditViewModel viewModel;

    private List<ChecklistBedtime> bedtimeList;

    private LifecycleOwner lifecycleOwner;

    public ChecklistAdapter(List<ChecklistBedtime> items, AddEditViewModel viewModel, LifecycleOwner activity) {
        this.viewModel = viewModel;
        setList(items);
        lifecycleOwner = activity;

    }

    @Override
    public View getView(int position, final View view, final ViewGroup viewGroup) {
        ChecklistItemBinding binding;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            binding = ChecklistItemBinding.inflate(inflater, viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(view);
        }

        binding.setItem(bedtimeList.get(position));
        binding.setLifecycleOwner(lifecycleOwner);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    private void setList(List<ChecklistBedtime> items) {
        bedtimeList = items;
        notifyDataSetChanged();
    }

    @Override
    public ChecklistBedtime getItem(int position) {
        return bedtimeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void replaceData(List<ChecklistBedtime> items) {
        setList(items);
    }

    @Override
    public int getCount() {
        if (bedtimeList == null) {
            return 0;
        } else {
            return bedtimeList.size();
        }
    }
}


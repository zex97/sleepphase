package com.mse.group1.sleepphase.checklist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import com.mse.group1.sleepphase.data.alarm_components.ChecklistBedtime;
import com.mse.group1.sleepphase.databinding.ChecklistCheckoffItemBinding;
import com.mse.group1.sleepphase.databinding.ChecklistItemBinding;
import com.mse.group1.sleepphase.databinding.QuizItemBinding;

import java.util.List;

public class ChecklistAdapter extends BaseAdapter {

    private final ChecklistViewModel viewModel;
    private List<ChecklistBedtime> checklist;
    private LifecycleOwner lifecycleOwner;

    public ChecklistAdapter(ChecklistViewModel viewModel, List<ChecklistBedtime> checklist, LifecycleOwner lifecycleOwner) {
        this.viewModel = viewModel;
        this.checklist = checklist;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    public int getCount() {
        if (checklist == null) {
            return 0;
        }
        return checklist.size();
    }

    @Override
    public Object getItem(int position) {
        return checklist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void replaceData(List<ChecklistBedtime> checklist) {
        this.checklist = checklist;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, final View view, final ViewGroup viewGroup) {
        ChecklistCheckoffItemBinding binding;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            binding = ChecklistCheckoffItemBinding.inflate(inflater, viewGroup, false);
        } else {
            binding = DataBindingUtil.getBinding(view);
        }

        binding.setItem(checklist.get(position));
        binding.setLifecycleOwner(lifecycleOwner);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    private void setList(List<ChecklistBedtime> items) {
        checklist = items;
        notifyDataSetChanged();
    }
}

package com.mse.group1.sleepphase.checklist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.data.alarm_components.ChecklistBedtime;
import com.mse.group1.sleepphase.databinding.ChecklistFragmentBinding;
import java.util.ArrayList;

public class ChecklistFragment extends Fragment {

    private ChecklistViewModel viewModel;
    private ChecklistAdapter adapter;
    private ChecklistFragmentBinding binding;

    public ChecklistFragment() {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
    }

    private void setupListAdapter() {
        ListView listView =  binding.listChecklist;
        adapter = new ChecklistAdapter(viewModel, new ArrayList<ChecklistBedtime>(0), getActivity());
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ChecklistFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(ChecklistViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getActivity());
        setHasOptionsMenu(true);
        return binding.getRoot();
    }
}

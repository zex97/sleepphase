package com.mse.group1.sleepphase.alarms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.databinding.AlarmsFragmentBinding;

import java.util.ArrayList;

public class AlarmsFragment extends Fragment {

    private AlarmsViewModel viewModel;

    private AlarmsAdapter adapter;

    private AlarmsFragmentBinding binding;

    public AlarmsFragment () {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupToastListener();

        setupListAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadAlarms();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AlarmsFragmentBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(getActivity()).get(AlarmsViewModel.class);


        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getActivity());

        setHasOptionsMenu(true);

        return binding.getRoot();
    }


    private void setupToastListener() {
        viewModel.getToastText().observe(getViewLifecycleOwner(), new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> event) {
                String message = event.getContentIfNotHandled();
                if (message != null) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void setupListAdapter() {
        ListView listView =  binding.listAlarms;

        adapter = new AlarmsAdapter(
                new ArrayList<Alarm>(0),
                viewModel,
                getActivity()
        );
        listView.setAdapter(adapter);
    }
}

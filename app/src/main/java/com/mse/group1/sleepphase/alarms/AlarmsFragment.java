package com.mse.group1.sleepphase.alarms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.data.Alarm;

import java.util.List;

public class AlarmsFragment extends Fragment implements AlarmItemListener {

    private RecycleViewAdapter adapter;
    private RecyclerView alarmsRecyclerView;
    private AlarmsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new RecycleViewAdapter(this);
        viewModel = new ViewModelProvider(this).get(AlarmsViewModel.class);
        viewModel.getAlarms().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                if (alarms != null) {
                    adapter.setAlarms(alarms);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupToastListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadAlarms();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarms_fragment, container, false);
        alarmsRecyclerView = view.findViewById(R.id.frag_alarms_recycle_view);
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        alarmsRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onSwitchChange(Alarm alarm) {
        // TODO implement alarm scheduling
        System.out.println("switchhhhh");
    }

    @Override
    public void onAlarmClicked(Alarm alarm) {
        System.out.println("CCCCCCCCCC " + alarm.getId());
        System.out.println(viewModel.getEditAlarmObservable().getValue());
        viewModel.editAlarmObservable(alarm.getId());
        System.out.println(viewModel.getEditAlarmObservable().getValue());
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
}

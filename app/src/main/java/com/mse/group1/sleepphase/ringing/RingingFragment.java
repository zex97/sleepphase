package com.mse.group1.sleepphase.ringing;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.data.Alarm;
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;
import com.mse.group1.sleepphase.data.alarm_components.ChecklistBedtime;
import com.mse.group1.sleepphase.data.alarm_components.TurningOffAlarm;
import com.mse.group1.sleepphase.data.alarm_components.TurningOffTypes;
import com.mse.group1.sleepphase.databinding.RingingFragmentBinding;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Calendar;


public class RingingFragment extends Fragment {

    private RingingViewModel viewModel;

    private RingingFragmentBinding binding;

    public RingingFragment () {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.start(getActivity().getIntent().getStringExtra("ALARM_ID"));

        RingingActionsListener listener = new RingingActionsListener() {
            @Override
            public void snoozePressed() {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 1);                   // for demonstration purposes
                Alarm originalAlarm = viewModel.getAlarm().getValue();

                Alarm alarm = new Alarm();
                alarm.setName(viewModel.getName() + " Snooze");
                alarm.setActive(true);
                alarm.setType(AlarmType.REGULAR);
                alarm.setRingAt(new LocalTime(calendar));
                alarm.setRecurring(false);
                alarm.setDays(new ArrayList<String>());
                alarm.setSound(originalAlarm.getSound());
                alarm.setVolume(originalAlarm.getVolume());
                alarm.setVibrate(originalAlarm.getVibrate());
                alarm.setSnooze_enabled(true);                  // TODO disable after a few times?
                alarm.setSnooze_every_min(5);                   //TODO not implemented yet, maybe leave out?
                alarm.setSnooze_times(1);                       //TODO same
                alarm.setTurning_off_alarm(originalAlarm.getTurning_off_alarm());
                alarm.setChecklist_bedtime(originalAlarm.getChecklist_bedtime());

                alarm.activateAlarm(getActivity().getApplicationContext());

                Intent intentAlarmService = new Intent(getActivity().getApplicationContext(), AlarmService.class);
                getActivity().getApplicationContext().stopService(intentAlarmService);
                getActivity().finish();
            }

            @Override
            public void dismissPressed() {
                Intent intentAlarmService = new Intent(getActivity().getApplicationContext(), AlarmService.class);
                getActivity().getApplicationContext().stopService(intentAlarmService);
                getActivity().finish();
            }

            @Override
            public void goPressed() {
                EquationFragment fragment = new EquationFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_for_ringing, fragment, "EquationFragment")
                        .addToBackStack(null)
                        .commit();
            }
        };

        binding.setLifecycleOwner(getActivity());
        binding.setListener(listener);
        binding.executePendingBindings();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.ringing_fragment, container, false);
        if (binding == null) {
            binding = RingingFragmentBinding.bind(root);
        }
        viewModel = new ViewModelProvider(getActivity()).get(RingingViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getActivity());
        setRetainInstance(false);

        return binding.getRoot();
    }
}

package com.mse.group1.sleepphase.ringing;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.alarms.AlarmsActivity;
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

    private SensorManager mSensorManager;

    private float shakeAcceleration = 0.0f;

    private float currentAcceleration = 0.0f;

    private float lastAcceleration = 0.0f;

    private Calendar lastShake = Calendar.getInstance();

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
//            float y = se.values[1];
//            float z = se.values[2];
            lastAcceleration = currentAcceleration;
            currentAcceleration = (float) Math.sqrt((double) (x*x));
//            currentAcceleration = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = currentAcceleration - lastAcceleration;
            shakeAcceleration = shakeAcceleration * 1.0f + delta;
            if (shakeAcceleration > 1 && System.currentTimeMillis() - lastShake.getTimeInMillis() > 750) {
                viewModel.incrementShakeCount();
                lastShake.setTimeInMillis(System.currentTimeMillis());
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        String type = viewModel.getTurningOffType().getValue();
        if (type != null && type.equals("SHAKE_THE_PHONE") ) {
            mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        String type = viewModel.getTurningOffType().getValue();
        if (type != null && type.equals("SHAKE_THE_PHONE") ) {
            mSensorManager.unregisterListener(mSensorListener);
        }
        super.onPause();
    }

    public RingingFragment () {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.start(getActivity().getIntent().getStringExtra("ALARM_ID"));

        viewModel.getShakeCountAchieved().observe(getViewLifecycleOwner(), new Observer<Event<Object>>() {
            @Override
            public void onChanged(Event<Object> event) {
                if (event.getContentIfNotHandled() != null) {
                    Intent intentAlarmService = new Intent(getActivity().getApplicationContext(), AlarmService.class);
                    getActivity().getApplicationContext().stopService(intentAlarmService);
                    getActivity().finish();
                }
            }
        });

        viewModel.getAlarmLoadedObservable().observe(getViewLifecycleOwner(), new Observer<Event<Object>>() {
            @Override
            public void onChanged(Event<Object> event) {
                if (event.getContentIfNotHandled() != null) {
                    if (!viewModel.getTurningOffType().getValue().equals("SHAKE_THE_PHONE")) {
                        mSensorManager.unregisterListener(mSensorListener);
                    }
                }
            }
        });

        RingingActionsListener listener = new RingingActionsListener() {
            @Override
            public void snoozePressed() {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 1);                   // for demonstration purposes
                Alarm originalAlarm = viewModel.getAlarm().getValue();

                Alarm alarm = new Alarm();
                alarm.setName(viewModel.getName().getValue() + " Snooze");
                alarm.setActive(true);
                alarm.setType(AlarmType.REGULAR);
                alarm.setRingAt(new LocalTime(calendar));
                alarm.setRecurring(false);
                alarm.setDays(new ArrayList<String>());
                alarm.setSound(originalAlarm.getSound());
                alarm.setVolume(originalAlarm.getVolume());
                alarm.setVibrate(originalAlarm.getVibrate());
                alarm.setSnooze_enabled(true);
                alarm.setSnooze_every_min(5);                 //not implemented yet
                alarm.setSnooze_times(1);                     //not implemented yet
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

        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

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

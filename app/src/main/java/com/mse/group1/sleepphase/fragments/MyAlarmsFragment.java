package com.mse.group1.sleepphase.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.mse.group1.sleepphase.EditAlarmActivity;
import com.mse.group1.sleepphase.R;

import java.util.Random;


public class MyAlarmsFragment extends Fragment {


    public MyAlarmsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_my_alarms, container, false);
        ConstraintLayout singleAlarmView;

        LinearLayout scrollableLayout = fragmentView.findViewById(R.id.scrollableLayout);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0,0,0,10);
//        singleAlarmView.setLayoutParams(layoutParams);
//        scrollableLayout.addView(singleAlarmView);

        // TODO: exercise 3, read from database
        for (int i = 0; i < 6; i++) {
            singleAlarmView = (ConstraintLayout) inflater.inflate(R.layout.alarm_layout, null);
            singleAlarmView.setLayoutParams(layoutParams);

            singleAlarmView.setTag(i);

            TextView alarmName = (TextView) singleAlarmView.getViewById(R.id.alarmNameTextView);
            alarmName.setText("My Alarm " + (i+1));

            TextView alarmTime = (TextView) singleAlarmView.getViewById(R.id.alarmTimeTextView);
            alarmTime.setText("" + ((i+5)*2) + ":" + ((i+2)*7));

            TextView alarmType = (TextView) singleAlarmView.getViewById(R.id.alarmTypeTextView);
            alarmType.setText("Step By Step");

            TextView alarmGoal = (TextView) singleAlarmView.getViewById(R.id.alarmGoalTextView);
            alarmGoal.setText("Goal: " + ((i+5)*2-1) + ":" + ((i+2)*7));

            TextView alarmDays = (TextView) singleAlarmView.getViewById(R.id.daysOrDateAlarmTextView);
            alarmDays.setText("Mo-Fr");

            Switch alarmSwitch = (Switch) singleAlarmView.getViewById(R.id.myAlarmsSwitch);
            if (i%2 == 0) {
                alarmSwitch.setChecked(true);
            } else {
                alarmSwitch.setChecked(false);
            }

            singleAlarmView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_item));
                            Intent intent = new Intent(getActivity(), EditAlarmActivity.class);
                            intent.putExtra(EditAlarmActivity.ALARM_ID_KEY, (int) v.getTag());
                            startActivity(intent);
                        }
                    }
            );

            scrollableLayout.addView(singleAlarmView);
        }

//        ConstraintSet constraintSet = new ConstraintSet();
//        constraintSet.clone(myAlarmsLayout);
//        constraintSet.connect(singleAlarmView.getId(), ConstraintSet.TOP, R.id.textViewMyAlarms, ConstraintSet.BOTTOM, 15);
//        constraintSet.applyTo(myAlarmsLayout);
        return fragmentView;
    }

}

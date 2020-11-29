package com.mse.group1.sleepphase.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import com.mse.group1.sleepphase.R;


public class MyAlarmsFragment extends Fragment {


    public MyAlarmsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_my_alarms, container, false);
        ConstraintLayout singleAlarmView = (ConstraintLayout) inflater.inflate(R.layout.alarm_layout, null);

        LinearLayout scrollableLayout = fragmentView.findViewById(R.id.scrollableLayout);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0,0,0,10);
        singleAlarmView.setLayoutParams(layoutParams);
        scrollableLayout.addView(singleAlarmView);

        for (int i = 0; i < 6; i++) {
            singleAlarmView = (ConstraintLayout) inflater.inflate(R.layout.alarm_layout, null);
            singleAlarmView.setLayoutParams(layoutParams);
            scrollableLayout.addView(singleAlarmView);
        }

//        ConstraintSet constraintSet = new ConstraintSet();
//        constraintSet.clone(myAlarmsLayout);
//        constraintSet.connect(singleAlarmView.getId(), ConstraintSet.TOP, R.id.textViewMyAlarms, ConstraintSet.BOTTOM, 15);
//        constraintSet.applyTo(myAlarmsLayout);
        return fragmentView;
    }

}

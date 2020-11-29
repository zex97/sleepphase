package com.mse.group1.sleepphase.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mse.group1.sleepphase.R;

public class TurningOffAlarmFragment extends Fragment {

    public TurningOffAlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // TODO depending if button has been pressed show or don't show (return different - empty fragment instead??)

        return inflater.inflate(R.layout.fragment_turning_off_alarm, container, false);
    }

}

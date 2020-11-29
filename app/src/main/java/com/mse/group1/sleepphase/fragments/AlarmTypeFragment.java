package com.mse.group1.sleepphase.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mse.group1.sleepphase.R;

public class AlarmTypeFragment extends Fragment {

    public AlarmTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // TODO depending on alarm type return different fragment

        return inflater.inflate(R.layout.fragment_alarm_type_regular, container, false);
    }

}

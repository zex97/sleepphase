package com.mse.group1.sleepphase.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.mse.group1.sleepphase.EditAlarmActivity;
import com.mse.group1.sleepphase.R;


public class NewAlarmFragment extends Fragment {


    public NewAlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_new_alarm, container, false);

        Button createNewAlarmButton = fragmentView.findViewById(R.id.createNew);

        createNewAlarmButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_item));
                        Intent intent = new Intent(getActivity(), EditAlarmActivity.class);
                        //intent.putExtra(null, (int) v.getTag());
                        startActivity(intent);
                    }
                }
        );
        return fragmentView;
    }



}

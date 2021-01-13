package com.mse.group1.sleepphase.addeditalarm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.data.alarm_components.AlarmType;
import com.mse.group1.sleepphase.databinding.AddEditFragment1Binding;

public class AddEditAlarmFragment extends Fragment {

public static final String EDIT_ALARM_CODE = "edit_alarm_code";

    private AddEditViewModel viewModel;

    private AddEditFragment1Binding binding;

    private AlarmType alarmType = null;

    public AddEditAlarmFragment(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (getArguments() != null && getArguments().get(EDIT_ALARM_CODE) != null) {
            actionBar.setTitle("Edit Alarm");
        } else {
            actionBar.setTitle("Create Alarm");
        }

        if (getArguments() != null) {
            viewModel.start(getArguments().getString(EDIT_ALARM_CODE), null);
        } else {
            viewModel.start(null, alarmType);
        }

        Button nextButton = getActivity().findViewById(R.id.button_next_1);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickSoundFragment fragment = new PickSoundFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_for_add_edit_fragment, fragment, "PickSoundFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        EditText editText = getActivity().findViewById(R.id.edit_text_1);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(v.getId() == R.id.edit_text_1 && !hasFocus) {

                    InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.add_edit_fragment1, container, false);
        if (binding == null) {
            binding = AddEditFragment1Binding.bind(root);
        }
        viewModel = new ViewModelProvider(getActivity()).get(AddEditViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getActivity());
        setRetainInstance(false);

        return binding.getRoot();
    }

    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }
}

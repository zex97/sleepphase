package com.mse.group1.sleepphase.addeditalarm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.data.alarm_components.ChecklistBedtime;
import com.mse.group1.sleepphase.databinding.AddEditFragment4Binding;

import java.util.ArrayList;

public class ChecklistFragment extends Fragment {

    private AddEditViewModel viewModel;

    private ChecklistAdapter adapter;

    private AddEditFragment4Binding binding;

    public ChecklistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupListAdapter();

        Button nextButton = getActivity().findViewById(R.id.button_next_4);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.saveAlarm();
            }
        });

        EditText editText = getActivity().findViewById(R.id.edit_text_2);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(v.getId() == R.id.edit_text_2 && !hasFocus) {

                    InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.add_edit_fragment_4, container, false);
        if (binding == null) {
            binding = AddEditFragment4Binding.bind(root);
        }
        viewModel = new ViewModelProvider(getActivity()).get(AddEditViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getActivity());
        setRetainInstance(false);

        return binding.getRoot();
    }

    private void setupListAdapter() {
        ListView listView =  binding.listBedtime;

        adapter = new ChecklistAdapter(
                new ArrayList<ChecklistBedtime>(0),
                viewModel,
                getActivity()
        );
        listView.setAdapter(adapter);
    }

}

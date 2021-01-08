package com.mse.group1.sleepphase.ringing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.databinding.EquationFragmentBinding;

public class EquationFragment extends Fragment {
    private RingingViewModel viewModel;

    private EquationFragmentBinding binding;

    public EquationFragment () {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView checkButton = getActivity().findViewById(R.id.check_equation_button);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.checkEquation()) {
                    Intent intentAlarmService = new Intent(getActivity().getApplicationContext(), AlarmService.class);
                    getActivity().getApplicationContext().stopService(intentAlarmService);
                    getActivity().finish();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.equation_fragment, container, false);
        if (binding == null) {
            binding = EquationFragmentBinding.bind(root);
        }
        viewModel = new ViewModelProvider(getActivity()).get(RingingViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getActivity());
        setRetainInstance(false);

        return binding.getRoot();
    }
}

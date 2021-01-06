package com.mse.group1.sleepphase.addeditalarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.Event;
import com.mse.group1.sleepphase.R;
import com.mse.group1.sleepphase.databinding.AddEditFragment2Binding;

public class PickSoundFragment extends Fragment {

    private AddEditViewModel viewModel;

    private AddEditFragment2Binding binding;

    public PickSoundFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button nextButton = getActivity().findViewById(R.id.button_next_2);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TurningOffTypeFragment fragment = new TurningOffTypeFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_for_add_edit_fragment, fragment, "TurningOffTypeFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.add_edit_fragment2, container, false);
        if (binding == null) {
            binding = AddEditFragment2Binding.bind(root);
        }
        viewModel = new ViewModelProvider(getActivity()).get(AddEditViewModel.class);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(getActivity());
        setRetainInstance(false);

        return binding.getRoot();
    }

}

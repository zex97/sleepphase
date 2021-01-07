package com.mse.group1.sleepphase.ringing;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.mse.group1.sleepphase.R;


public class RingingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringing);

        RingingFragment fragment = (RingingFragment) getSupportFragmentManager().findFragmentById(R.id.container_for_ringing);
        if (fragment == null) {
            fragment = new RingingFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_for_ringing, fragment);
            transaction.commit();
        }

    }

}

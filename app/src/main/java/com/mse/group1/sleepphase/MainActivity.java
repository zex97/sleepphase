package com.mse.group1.sleepphase;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import com.mse.group1.sleepphase.fragments.MyAlarmsFragment;
import com.mse.group1.sleepphase.fragments.NewAlarmFragment;
import com.mse.group1.sleepphase.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private ViewPager viewPager;


    //Fragments

    NewAlarmFragment newAlarmFragment;
    MyAlarmsFragment myAlarmsFragment;
    SettingsFragment settingsFragment;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_new_alarm:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.navigation_my_alarms:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.navigation_settings:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        newAlarmFragment = new NewAlarmFragment();
        myAlarmsFragment = new MyAlarmsFragment();
        settingsFragment = new SettingsFragment();
        adapter.addFragment(newAlarmFragment);
        adapter.addFragment(myAlarmsFragment);
        adapter.addFragment(settingsFragment);
        viewPager.setAdapter(adapter);
    }

}

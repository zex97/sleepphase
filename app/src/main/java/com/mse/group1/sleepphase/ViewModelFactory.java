package com.mse.group1.sleepphase;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.mse.group1.sleepphase.alarms.AlarmsViewModel;
import com.mse.group1.sleepphase.data.source.AlarmsDataSource;
import com.mse.group1.sleepphase.data.source.SimpleAlarmsDataSource;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
//
//    @SuppressLint("StaticFieldLeak")
//    private static volatile ViewModelFactory INSTANCE;
//
//    private final SimpleAlarmsDataSource dataSource;
//
//    public static ViewModelFactory getInstance(Application application) {
//
//        if (INSTANCE == null) {
//            synchronized (ViewModelFactory.class) {
//                if (INSTANCE == null) {
////                    INSTANCE = new ViewModelFactory(
////                            Injection.provideTasksRepository(application.getApplicationContext()));
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//    public SimpleAlarmsDataSource getDataSource() {
//        return dataSource;
//    }
//
//    @VisibleForTesting
//    public static void destroyInstance() {
//        INSTANCE = null;
//    }
//
//    private ViewModelFactory(SimpleAlarmsDataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    @Override
//    public <T extends ViewModel> T create(Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(AlarmsViewModel.class)) {
//            //noinspection unchecked
//            return (T) new AlarmsViewModel(dataSource);
//        }
//
//        throw new IllegalArgumentException("ViewModel class unknown: " + modelClass.getName());
//    }
}

package com.mse.group1.sleepphase.alarms;

import android.widget.ListView;
import androidx.databinding.BindingAdapter;
import com.mse.group1.sleepphase.data.Alarm;

import java.util.List;

public class AlarmsListBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:items")
    public static void setItems(ListView listView, List<Alarm> items) {
        AlarmsAdapter adapter = (AlarmsAdapter) listView.getAdapter();
        if (adapter != null)
        {
            adapter.replaceData(items);
        }
    }
}

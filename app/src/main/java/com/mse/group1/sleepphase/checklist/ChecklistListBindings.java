package com.mse.group1.sleepphase.checklist;

import android.widget.ListView;
import androidx.databinding.BindingAdapter;
import com.mse.group1.sleepphase.data.alarm_components.ChecklistBedtime;

import java.util.List;

public class ChecklistListBindings {

    @BindingAdapter("android:items")
    public static void setItems(ListView listView, List<ChecklistBedtime> items) {
        ChecklistAdapter adapter = (ChecklistAdapter) listView.getAdapter();
        if (adapter != null)
        {
            adapter.replaceData(items);
        }
    }

}

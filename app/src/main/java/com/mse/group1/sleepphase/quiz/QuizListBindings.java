package com.mse.group1.sleepphase.quiz;

import android.widget.ListView;
import androidx.databinding.BindingAdapter;
import com.mse.group1.sleepphase.data.quiz_components.Question;

import java.util.List;

public class QuizListBindings {

    @BindingAdapter("android:items")
    public static void setItems(ListView listView, List<Question> items) {
        QuizAdapter adapter = (QuizAdapter) listView.getAdapter();
        if (adapter != null)
        {
            adapter.replaceData(items);
        }
    }

}

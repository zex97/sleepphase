package com.mse.group1.sleepphase.data.alarm_components;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ChecklistBedtime {

    private String description;
    private Boolean checked;

    public ChecklistBedtime() {

    }

    public ChecklistBedtime(String description, Boolean checked) {
        this.description = description;
        this.checked = checked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @NonNull
    @Override
    public String toString() {
        return description + "_____" + checked;
    }

    public static ChecklistBedtime fromString(String string) {
        ChecklistBedtime item = new ChecklistBedtime();
        item.setDescription(string.split("_____")[0]);
        item.setChecked(string.split("_____")[1].equals("true"));
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChecklistBedtime checklistBedtime = (ChecklistBedtime) o;

        if (!Objects.equals(description, checklistBedtime.description)) return false;
        return Objects.equals(checked, checklistBedtime.checked);
    }


    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (checked != null ? checked.hashCode() : 0);
        return result;
    }
}

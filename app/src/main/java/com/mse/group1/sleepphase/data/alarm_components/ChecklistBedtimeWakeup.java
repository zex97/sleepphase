package com.mse.group1.sleepphase.data.alarm_components;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ChecklistBedtimeWakeup {

    private String description;
    private Boolean checked;

    public ChecklistBedtimeWakeup () {

    }

    public ChecklistBedtimeWakeup (String description, Boolean checked) {
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
        return description + " " + checked;
    }

    public static ChecklistBedtimeWakeup fromString(String string) {
        ChecklistBedtimeWakeup item = new ChecklistBedtimeWakeup();
        item.setDescription(string.split(" ")[0]);
        item.setChecked(string.split(" ")[1].equals("true"));
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChecklistBedtimeWakeup checklistBedtimeWakeup = (ChecklistBedtimeWakeup) o;

        if (!Objects.equals(description, checklistBedtimeWakeup.description)) return false;
        return Objects.equals(checked, checklistBedtimeWakeup.checked);
    }


    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (checked != null ? checked.hashCode() : 0);
        return result;
    }
}

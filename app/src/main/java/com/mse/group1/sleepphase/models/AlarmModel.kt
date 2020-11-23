package com.mse.group1.sleepphase.models

import java.time.LocalDate
import java.time.LocalTime

data class AlarmModel (var type: AlarmType,
                       var name: String,
                       var ringAt: LocalTime,
                       var goal: LocalTime?,        // step by step and skip a night alarms
                       var days: Array<String>,
                       var skip: LocalDate?,        //only skip a night alarms
                       var changeBy: Int?,          //only step by step alarms
                       var everyDays: Int?,         //only step by step alarms
                       var sound: String,
                       var vibrate: Boolean,
                       var snooze_enabled: Boolean,
                       var snooze_every_min: Int,
                       var snooze_times: Int,
                       var turning_off_alarm: TurningOffAlarm,
                       var checklist_bedtime: Array<ChecklistBedtimeWakeup>,
                       var checklist_wakeup: Array<ChecklistBedtimeWakeup>) {
    constructor() : this(AlarmType.REGULAR, "", LocalTime.MIN, null, emptyArray(), null, null, null, "",
            false, false, 0, 0, TurningOffAlarm(TurningOffTypes.SWIPE_OVER_SCREEN, 0, 0),
            Array(0) { ChecklistBedtimeWakeup() }, Array(0) { ChecklistBedtimeWakeup() })
}

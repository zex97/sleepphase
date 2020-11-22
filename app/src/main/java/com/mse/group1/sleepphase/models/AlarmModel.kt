package com.mse.group1.sleepphase.models

import java.time.LocalTime

data class AlarmModel (val type: AlarmType, val name: String, val ringAt: LocalTime, val days: Array<String>,
                       val sound: String, val vibrate: Boolean, val snooze_enabled: Boolean,
                       val snooze_every_min: Int, val snooze_times: Int, val turning_off_alarm: TurningOffAlarm,
                       val checklist_bedtime: Array<ChecklistBedtimeWakeup>, val checklist_wakeup: Array<ChecklistBedtimeWakeup>)

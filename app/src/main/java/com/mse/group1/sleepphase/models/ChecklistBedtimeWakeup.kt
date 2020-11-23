package com.mse.group1.sleepphase.models

data class ChecklistBedtimeWakeup (val description: String, val checked: Boolean) {
    constructor() : this ("",false)
}


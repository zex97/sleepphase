package com.mse.group1.sleepphase.models

data class AnswerModel (
        var answer: String,
        var indicates: AlarmType) {
    constructor() : this ("", AlarmType.REGULAR)
}
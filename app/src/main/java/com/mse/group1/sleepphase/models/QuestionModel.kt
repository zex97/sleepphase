package com.mse.group1.sleepphase.models

data class QuestionModel (
        var question: String,
        var answers: Array<AnswerModel> ){
    constructor() : this ("", Array(0) { AnswerModel() })
}
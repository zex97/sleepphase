package com.mse.group1.sleepphase.models

data class QuizModel (
        var description: String,
        var questions: Array<QuestionModel> ){
    constructor() : this ("", Array(0) { QuestionModel() })

}
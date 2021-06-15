package com.rsschool.quiz.data.repository.model

class QuestionItem(
    val question: String, // @StringRes
    val answerOptions: Array<String>,
    val answerNumber: Int
)
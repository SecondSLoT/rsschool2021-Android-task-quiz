package com.rsschool.quiz.domain

import com.rsschool.quiz.data.repository.model.QuestionItem

interface Repository {

    fun getQuestions(): List<QuestionItem>

    fun getAnswers(): IntArray

    fun setAnswers(answers: IntArray)
}
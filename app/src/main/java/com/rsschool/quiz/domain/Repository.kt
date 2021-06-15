package com.rsschool.quiz.domain

import com.rsschool.quiz.data.repository.model.QuestionItem

interface Repository {

    fun getQuestions(): List<QuestionItem>
}
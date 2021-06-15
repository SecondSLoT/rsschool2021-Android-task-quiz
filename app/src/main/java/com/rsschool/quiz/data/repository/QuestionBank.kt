package com.rsschool.quiz.data.repository

import com.rsschool.quiz.data.questionBank.model.QuestionEntity

interface QuestionBank {
    fun getQuestions(): List<QuestionEntity>
}
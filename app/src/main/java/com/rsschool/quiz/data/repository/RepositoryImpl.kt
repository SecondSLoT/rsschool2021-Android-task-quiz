package com.rsschool.quiz.data.repository

import com.rsschool.quiz.data.questionBank.QuestionBankImpl
import com.rsschool.quiz.data.repository.mapper.EntityToItemMapper
import com.rsschool.quiz.data.repository.model.QuestionItem
import com.rsschool.quiz.domain.Repository

object RepositoryImpl : Repository {

    private val questionBank: QuestionBank = QuestionBankImpl

    override fun getQuestions(): List<QuestionItem> {
        val questionEntityList = questionBank.getQuestions()
        return EntityToItemMapper.map(questionEntityList)
    }

    override fun getAnswers(): IntArray {
        return questionBank.getAnswers()
    }

    override fun setAnswers(answers: IntArray) {
        questionBank.setAnswers(answers)
    }
}
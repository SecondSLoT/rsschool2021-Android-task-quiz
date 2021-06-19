package com.rsschool.quiz.domain.usecase

import com.rsschool.quiz.data.repository.RepositoryImpl
import com.rsschool.quiz.data.repository.model.QuestionItem
import com.rsschool.quiz.domain.Repository

class SetAnswersUseCase {

    private val repository: Repository = RepositoryImpl

    fun execute(answers: IntArray) {
        repository.setAnswers(answers)
    }
}
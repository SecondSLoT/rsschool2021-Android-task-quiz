package com.rsschool.quiz.domain.usecase

import com.rsschool.quiz.data.repository.RepositoryImpl
import com.rsschool.quiz.domain.Repository

class GetAnswersUseCase {

    private val repository: Repository = RepositoryImpl

    fun execute(): IntArray {
        return repository.getAnswers()
    }
}
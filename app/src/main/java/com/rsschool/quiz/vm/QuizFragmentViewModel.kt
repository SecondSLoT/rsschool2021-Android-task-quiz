package com.rsschool.quiz.vm

import androidx.lifecycle.ViewModel
import com.rsschool.quiz.data.repository.model.QuestionItem
import com.rsschool.quiz.domain.usecase.GetQuestionsUseCase

class QuizFragmentViewModel : ViewModel() {

    private val getQuestionsUseCase = GetQuestionsUseCase()
    var questions = listOf<QuestionItem>()

    init {
        questions = getQuestionsUseCase.execute()
    }
}
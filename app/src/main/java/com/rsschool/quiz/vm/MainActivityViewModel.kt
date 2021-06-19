package com.rsschool.quiz.vm

import androidx.lifecycle.ViewModel
import com.rsschool.quiz.data.repository.model.QuestionItem
import com.rsschool.quiz.domain.usecase.GetQuestionsUseCase

class MainActivityViewModel : ViewModel() {

    private val getQuestionsUseCase = GetQuestionsUseCase()
    var position = 0
    var questions: List<QuestionItem> = getQuestionsUseCase.execute()
    var answers: IntArray = IntArray(questions.size) { -1 }
    var checkedButtonIds = IntArray(questions.size) { -1 }

    fun clearAnswers() {
        position = 0
        answers = IntArray(questions.size) { -1 }
        checkedButtonIds = IntArray(questions.size) { -1 }
    }
}
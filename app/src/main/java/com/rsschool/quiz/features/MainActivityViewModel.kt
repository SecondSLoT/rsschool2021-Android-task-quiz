package com.rsschool.quiz.features

import androidx.lifecycle.ViewModel
import com.rsschool.quiz.data.repository.model.QuestionItem
import com.rsschool.quiz.domain.usecase.GetQuestionsUseCase
import com.rsschool.quiz.features.quiz.ui.QuizFragment

class MainActivityViewModel : ViewModel() {

    private val getQuestionsUseCase = GetQuestionsUseCase()
    var lastOpenedFragment: String = QuizFragment.QUIZ_FRAGMENT_ID
    var position = 0
    var questions: List<QuestionItem> = getQuestionsUseCase.execute()
    var answers: IntArray = IntArray(questions.size) { -1 }

    fun clearAnswers() {
        position = 0
        answers = IntArray(questions.size) { -1 }
    }
}
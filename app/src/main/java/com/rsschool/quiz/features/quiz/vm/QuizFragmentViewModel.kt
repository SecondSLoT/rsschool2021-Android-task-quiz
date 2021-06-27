package com.rsschool.quiz.features.quiz.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rsschool.quiz.data.repository.model.QuestionItem

class QuizFragmentViewModel : ViewModel() {

    var position: Int = 0
    var questions: List<QuestionItem> = emptyList()
    var answer: Int = -1

    private var enableNextButtonMutableLiveData = MutableLiveData<Boolean>()
    val enableNextButtonLiveData: LiveData<Boolean>
        get() = enableNextButtonMutableLiveData

    private var submitMutableLiveData = MutableLiveData<Boolean>()
    val submitLiveData: LiveData<Boolean>
        get() = submitMutableLiveData

    private var positionMutableLiveData = MutableLiveData<Int>()
    val positionLiveData: LiveData<Int>
        get() = positionMutableLiveData


    fun onAnswerChosen(answerIndex: Int) {
        enableNextButtonMutableLiveData.value = true
            answer = answerIndex
    }

    fun onNextButtonClicked() {
        if (position != questions.size - 1) {
            positionMutableLiveData.value = position + 1
        } else {
            submitMutableLiveData.value = true
        }
    }

    fun onPreviousButtonClicked() {
        positionMutableLiveData.value = position - 1
    }
}
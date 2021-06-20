package com.rsschool.quiz.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rsschool.quiz.data.repository.model.QuestionItem

class QuizFragmentViewModel : ViewModel() {

    var position: Int = 0
    var questions: List<QuestionItem> = emptyList()
    var answer: Int = -1
    var checkedButtonId: Int = -1

    private var enableNextButtonMutableLiveData = MutableLiveData<Boolean>()
    val enableNextButtonLiveData: LiveData<Boolean>
        get() = enableNextButtonMutableLiveData

    private var submitMutableLiveData = MutableLiveData<Boolean>()
    val submitLiveData: LiveData<Boolean>
        get() = submitMutableLiveData

    private var positionMutableLiveData = MutableLiveData<Int>()
    val positionLiveData: LiveData<Int>
        get() = positionMutableLiveData


    fun onAnswerChosen(checkedButtonId: Int, answerIndex: Int) {
        Log.d("TAG", "onAnswerChosen called: $answerIndex")
        enableNextButtonMutableLiveData.value = true
            answer = answerIndex
            this.checkedButtonId = checkedButtonId
    }

    fun onNextButtonClicked() {
        if (position != questions.size - 1) {
            positionMutableLiveData.value = position + 1
        } else {
            submitMutableLiveData.value = true
        }
    }

    fun onPreviousButtonClicked() {
        enableNextButtonMutableLiveData.value = true
        positionMutableLiveData.value = position - 1
    }
}
package com.rsschool.quiz.vm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rsschool.quiz.R
import com.rsschool.quiz.data.repository.model.QuestionItem
import com.rsschool.quiz.domain.usecase.Statistics
import com.rsschool.quiz.extentions.capitalize
import kotlin.system.exitProcess

class ResultFragmentViewModel : ViewModel() {

    var questions: List<QuestionItem> = emptyList()
    var answers: IntArray = IntArray(questions.size) { -1 }
    var statistics: Statistics? = null

    private var resultInPercentMutableLiveData = MutableLiveData<String>()
    val resultInPercentLiveData: LiveData<String>
        get() = resultInPercentMutableLiveData

    private var resultMutableLiveData = MutableLiveData<String>()
    val resultLiveData: LiveData<String>
        get() = resultMutableLiveData

    private var restartQuizMutableLiveData = MutableLiveData<Boolean>()
    val restartQuizLiveData: LiveData<Boolean>
        get() = restartQuizMutableLiveData

    private var shareMutableLiveData = MutableLiveData<Boolean>()
    val shareLiveData: LiveData<Boolean>
        get() = shareMutableLiveData

    fun getStatistics(context: Context) {
        statistics = Statistics(questions, answers)
        resultInPercentMutableLiveData.value =
            "${context.getString(R.string.your_result).capitalize()}: " +
                    "${statistics?.getResultInPercent()}"
        resultMutableLiveData.value =
            "(${statistics?.countRightAnswers()}/${statistics?.countQuestions()})"
    }

    fun onRestartButtonClicked() {
        restartQuizMutableLiveData.value = true
    }

    fun onShareButtonClicked() {
        shareMutableLiveData.value = true
    }

    fun onExitButtonClicked() {
        exitProcess(0)
    }
}
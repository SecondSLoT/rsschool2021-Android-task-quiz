package com.rsschool.quiz.domain.usecase

import android.content.Context
import com.rsschool.quiz.R
import com.rsschool.quiz.data.repository.model.QuestionItem
import com.rsschool.quiz.extentions.capitalize
import kotlin.math.roundToInt

class Statistics(
    private val questions: List<QuestionItem>,
    private val answers: IntArray
) {

    fun getResultInPercent(): String {
        return "${(countRightAnswers().toDouble() / countQuestions().toDouble() * 100)
            .roundToInt()} %"
    }

    fun countRightAnswers(): Int {
        var rightAnswers = 0
        for (i in questions.indices) {
            if (questions[i].answerNumber == answers[i]) {
                rightAnswers++
            }
        }
        return rightAnswers
    }

    fun countQuestions() = questions.size

    fun getReport(context: Context): String {
        val report = StringBuilder(
            "${context.getString(R.string.your_result).capitalize()}: " +
                    "${getResultInPercent()}\n" +
                    "(${countRightAnswers()}/${countQuestions()})\n"
        )

        for (i in questions.indices) {
            report.append(
                "\n${context.getString(R.string.question).capitalize()} ${i + 1}: " +
                        "${questions[i].question}\n" +
                        "${context.getString(R.string.your_answer).capitalize()}: " +
                        "${questions[i].answerOptions?.get(answers[i])}\n"
            )
        }

        return report.toString()
    }
}
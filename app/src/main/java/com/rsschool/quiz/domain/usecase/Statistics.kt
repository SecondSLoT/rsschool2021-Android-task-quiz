package com.rsschool.quiz.domain.usecase

import com.rsschool.quiz.data.repository.model.QuestionItem
import kotlin.math.roundToInt

class Statistics(
    private val questions: List<QuestionItem>,
    private val answers: Array<Int>
) {

    fun resultInPercent(): String {
        return "${(countRightAnswers().toDouble() / countQuestions().toDouble() * 100)
            .roundToInt()} %"
    }

    private fun countRightAnswers(): Int {
        var rightAnswers = 0
        for (i in questions.indices) {
            if (questions[i].answerNumber == answers[i]) {
                rightAnswers++
            }
        }
        return rightAnswers
    }

    private fun countQuestions() = questions.size

    fun getReport(): String {
        val report = StringBuilder(
            "Your result: ${resultInPercent()}\n" +
                    "(${countRightAnswers()}/${countQuestions()})\n"
        )

        for (i in questions.indices) {
            report.append(
                "\nQuestion ${i + 1}: ${questions[i].question}\n" +
                        "Your answer: ${questions[i].answerOptions[answers[i]]}\n"
            )
        }

        return report.toString()
    }
}
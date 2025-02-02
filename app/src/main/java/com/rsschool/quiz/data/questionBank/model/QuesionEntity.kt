package com.rsschool.quiz.data.questionBank.model

import androidx.annotation.StringRes

data class QuestionEntity(
    @StringRes val questionRes: Int,
    val answerOptions: IntArray,
    val answerNumber: Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionEntity

        if (questionRes != other.questionRes) return false
        if (!answerOptions.contentEquals(other.answerOptions)) return false
        if (answerNumber != other.answerNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = questionRes
        result = 31 * result + answerOptions.contentHashCode()
        result = 31 * result + answerNumber
        return result
    }
}

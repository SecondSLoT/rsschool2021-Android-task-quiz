package com.rsschool.quiz.data.questionBank.model

data class QuestionEntity(
    val question: String,
    val answerOptions: Array<String>,
    val answerNumber: Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionEntity

        if (question != other.question) return false
        if (!answerOptions.contentEquals(other.answerOptions)) return false
        if (answerNumber != other.answerNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = question.hashCode()
        result = 31 * result + answerOptions.contentHashCode()
        result = 31 * result + answerNumber
        return result
    }
}

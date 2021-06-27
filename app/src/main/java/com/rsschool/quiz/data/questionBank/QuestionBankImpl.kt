package com.rsschool.quiz.data.questionBank

import com.rsschool.quiz.R
import com.rsschool.quiz.data.questionBank.model.QuestionEntity
import com.rsschool.quiz.data.repository.QuestionBank

object QuestionBankImpl : QuestionBank {

    private val questions: List<QuestionEntity> = listOf(
        QuestionEntity(
            R.string.question_1,
            intArrayOf(
                R.string.answer_1_1,
                R.string.answer_1_2,
                R.string.answer_1_3,
                R.string.answer_1_4,
                R.string.answer_1_5),
            1
        ),
        QuestionEntity(
            R.string.question_2,
            intArrayOf(
                R.string.answer_2_1,
                R.string.answer_2_2,
                R.string.answer_2_3,
                R.string.answer_2_4,
                R.string.answer_2_5),
            4
        ),
        QuestionEntity(
            R.string.question_3,
            intArrayOf(
                R.string.answer_3_1,
                R.string.answer_3_2,
                R.string.answer_3_3,
                R.string.answer_3_4,
                R.string.answer_3_5),
            3
        ),
        QuestionEntity(
            R.string.question_4,
            intArrayOf(
                R.string.answer_4_1,
                R.string.answer_4_2,
                R.string.answer_4_3,
                R.string.answer_4_4,
                R.string.answer_4_5),
            1
        ),
        QuestionEntity(
            R.string.question_5,
            intArrayOf(
                R.string.answer_5_1,
                R.string.answer_5_2,
                R.string.answer_5_3,
                R.string.answer_5_4,
                R.string.answer_5_5),
            0
        ),
        QuestionEntity(
            R.string.question_6,
            intArrayOf(
                R.string.answer_6_1,
                R.string.answer_6_2,
                R.string.answer_6_3,
                R.string.answer_6_4,
                R.string.answer_6_5),
            2
        ),
        QuestionEntity(
            R.string.question_7,
            intArrayOf(
                R.string.answer_7_1,
                R.string.answer_7_2,
                R.string.answer_7_3,
                R.string.answer_7_4,
                R.string.answer_7_5),
            3
        ),
        QuestionEntity(
            R.string.question_8,
            intArrayOf(
                R.string.answer_8_1,
                R.string.answer_8_2,
                R.string.answer_8_3,
                R.string.answer_8_4,
                R.string.answer_8_5),
            2
        ),
        QuestionEntity(
            R.string.question_9,
            intArrayOf(
                R.string.answer_9_1,
                R.string.answer_9_2,
                R.string.answer_9_3,
                R.string.answer_9_4,
                R.string.answer_9_5),
            0
        ),
    )

    override fun getQuestions(): List<QuestionEntity> {
        return questions
    }
}
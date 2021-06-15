package com.rsschool.quiz.data.repository.mapper

import com.rsschool.quiz.core.mapper.Mapper
import com.rsschool.quiz.data.questionBank.model.QuestionEntity
import com.rsschool.quiz.data.repository.model.QuestionItem

object EntityToItemMapper : Mapper<List<QuestionEntity>, List<QuestionItem>> {
    override fun map(type: List<QuestionEntity>?): List<QuestionItem> {
        return type?.map {
            QuestionItem(
                question = it.question,
                answerOptions = it.answerOptions,
                answerNumber = it.answerNumber
            )
        } ?: emptyList()
    }
}
package com.rsschool.quiz.core.mapper

interface Mapper<in A, out B> {
    fun map(type: A?): B
}
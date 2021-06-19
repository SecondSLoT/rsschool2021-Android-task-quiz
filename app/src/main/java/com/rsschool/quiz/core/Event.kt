package com.rsschool.quiz.core

open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    /*
    * Возвращает content и предотвращает его повторное использование
    */
    fun getContentIfNotHandledOrNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /*
    * Возвращает content, даже если он уже был получен
    */
    fun peekContent(): T = content
}
package com.rsschool.quiz.core

import com.rsschool.quiz.R

object Themes {

    private val themes = intArrayOf(
        R.style.Theme_Quiz_First,
        R.style.Theme_Quiz_Second,
        R.style.Theme_Quiz_Third,
        R.style.Theme_Quiz_Fourth,
        R.style.Theme_Quiz_Fifth,
        R.style.Theme_Quiz_Sixth,
        R.style.Theme_Quiz_Seventh
    )

    fun getThemeId(number: Int): Int {
        return themes[number % themes.size]
    }
}
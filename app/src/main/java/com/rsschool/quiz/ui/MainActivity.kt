package com.rsschool.quiz.ui

import android.os.Bundle
import android.util.TypedValue
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.core.Themes
import com.rsschool.quiz.vm.MainActivityViewModel

class MainActivity :
    AppCompatActivity(R.layout.activity_main),
    QuizFragment.Callbacks,
    ResultFragment.Callbacks {

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeTheme(viewModel.position)
        openQuizFragment()
    }

    private fun openQuizFragment() {
        startFragment(QuizFragment.newInstance(
            viewModel.position,
            viewModel.questions,
            viewModel.answers[viewModel.position],
            viewModel.checkedButtonIds[viewModel.position])
        )
    }

    private fun openResultFragment() {
        startFragment(ResultFragment.newInstance(viewModel.questions, viewModel.answers))
    }

    private fun startFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onChangeQuestionClicked(
        newPosition: Int,
        prevAnswer: Int,
        prevCheckedButtonId: Int) {
        viewModel.answers[viewModel.position] = prevAnswer
        viewModel.checkedButtonIds[viewModel.position] = prevCheckedButtonId
        viewModel.position = newPosition
        changeTheme(viewModel.position)
        openQuizFragment()
    }

    override fun onSubmitButtonClicked(prevAnswer: Int, prevCheckedButtonId: Int) {
        viewModel.answers[viewModel.position] = prevAnswer
        viewModel.checkedButtonIds[viewModel.position] = prevCheckedButtonId
        changeTheme(0)
        paintStatusBar()
        openResultFragment()
    }

    private fun changeTheme(position: Int) {
        setTheme(Themes.getThemeId(position))
    }

    override fun paintStatusBar() {
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.statusBarColor, typedValue, true);
        window.statusBarColor = typedValue.data
    }

    override fun onRestartButtonClicked() {
        viewModel.clearAnswers()
        openQuizFragment()
    }
}
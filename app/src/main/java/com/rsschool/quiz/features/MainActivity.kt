package com.rsschool.quiz.features

import android.os.Bundle
import android.util.TypedValue
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.core.Themes
import com.rsschool.quiz.features.quiz.ui.QuizFragment
import com.rsschool.quiz.features.result.ui.ResultFragment
import com.rsschool.quiz.view.ChangeTheme
import com.rsschool.quiz.view.RegisterFragmentId
import com.rsschool.quiz.view.dialog.RestartDialog

class MainActivity :
    AppCompatActivity(R.layout.activity_main),
    QuizFragment.Callbacks,
    ResultFragment.Callbacks,
    RegisterFragmentId,
    ChangeTheme,
    RestartDialog.NoticeDialogListener {

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeTheme()
        openLastFragment(viewModel.lastOpenedFragment)
    }

    private fun openLastFragment(id: String) {
        when (id) {
            QuizFragment.QUIZ_FRAGMENT_ID -> openQuizFragment()
            ResultFragment.RESULT_FRAGMENT_ID -> openResultFragment()
        }
    }

    private fun openQuizFragment(slideInAnimId: Int? = null, slideOutAnimId: Int? = null) {
        startFragment(
            QuizFragment.newInstance(
                viewModel.position,
                viewModel.questions,
                viewModel.answers[viewModel.position],
            ),
            slideInAnimId,
            slideOutAnimId
        )
    }

    private fun openResultFragment() {
        startFragment(
            ResultFragment.newInstance(viewModel.questions, viewModel.answers),
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
    }

    private fun startFragment(
        fragment: Fragment,
        slideInAnimId: Int? = null,
        slideOutAnimId: Int? = null
    ) {

        val transaction = supportFragmentManager.beginTransaction()

        if (slideInAnimId != null && slideOutAnimId != null) {
            transaction.setCustomAnimations(slideInAnimId, slideOutAnimId)
        }

        transaction
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onAnswerChosen(answer: Int) {
        viewModel.answers[viewModel.position] = answer
    }

    override fun onChangeQuestionClicked(newPosition: Int) {
        val slideInAnimId: Int
        val slideOutAnimId: Int

        if (viewModel.position < newPosition) {
            slideInAnimId = R.anim.slide_in_right
            slideOutAnimId = R.anim.slide_out_left
        } else {
            slideInAnimId = R.anim.slide_in_left
            slideOutAnimId = R.anim.slide_out_right
        }

        viewModel.position = newPosition
        openQuizFragment(slideInAnimId, slideOutAnimId)
    }

    override fun onSubmitButtonClicked() {
        viewModel.position = 0
        openResultFragment()
    }

    override fun registerLastOpenedFragment(id: String) {
        viewModel.lastOpenedFragment = id
    }

    override fun changeTheme() {
        setTheme(Themes.getThemeId(viewModel.position))
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.statusBarColor, typedValue, true);
        window.statusBarColor = typedValue.data
    }

    override fun onRestartButtonClicked() {
        viewModel.clearAnswers()
        openQuizFragment()
    }

    override fun onPositiveButtonClicked(dialog: DialogFragment) {
        onRestartButtonClicked()
    }
}
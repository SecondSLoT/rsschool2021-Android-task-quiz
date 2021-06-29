package com.rsschool.quiz.features.quiz.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.radiobutton.MaterialRadioButton
import com.rsschool.quiz.R
import com.rsschool.quiz.data.repository.model.QuestionItem
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.extentions.capitalize
import com.rsschool.quiz.view.ChangeTheme
import com.rsschool.quiz.features.quiz.vm.QuizFragmentViewModel
import com.rsschool.quiz.view.RegisterFragmentId
import com.rsschool.quiz.view.dialog.ExitDialog


class QuizFragment : Fragment() {

    private val viewModel by viewModels<QuizFragmentViewModel>()
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var callbacks: Callbacks? = null
    private var callbackRegisterFragmentId: RegisterFragmentId? = null
    private var callbackChangeTheme: ChangeTheme? = null

    interface Callbacks {

        fun onAnswerChosen(answer: Int)

        fun onChangeQuestionClicked(newPosition: Int)

        fun onSubmitButtonClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Callbacks) {
            callbacks = context
        } else {
            throw RuntimeException("$context must implement QuizFragment.Callbacks")
        }

        if (context is RegisterFragmentId) {
            callbackRegisterFragmentId = context
        } else {
            throw RuntimeException("$context must implement RegisterFragmentId")
        }

        if (context is ChangeTheme) {
            callbackChangeTheme = context
        } else {
            throw RuntimeException("$context must implement ChangeTheme")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        callbackChangeTheme?.changeTheme()
        callbackRegisterFragmentId?.registerLastOpenedFragment(QUIZ_FRAGMENT_ID)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.position = arguments?.getInt(POSITION) ?: 0
        viewModel.questions =
            arguments?.getParcelableArrayList(QUESTIONS) ?: emptyList()
        viewModel.answer = arguments?.getInt(ANSWER) ?: -1

        if (viewModel.position == viewModel.questions.size - 1) {
            binding.nextButton.text = context?.getString(R.string.submit)
        }

        if (viewModel.position == 0) isPreviousButtonVisible(false)
        if (viewModel.answer == -1) isNextButtonEnabled(false)

        binding.run {
            toolbar.title = "${getString(R.string.question).capitalize()} " +
                    "${viewModel.position + 1} " +
                    "${getString(R.string.of)} ${viewModel.questions.size}"

            question.text = getString(viewModel.questions[viewModel.position].questionRes)
        }

        val answerOptionsNumber = viewModel.questions[viewModel.position].answerOptions?.size ?: 0

        for (i in 0 until answerOptionsNumber) {
            val radioButton = MaterialRadioButton(requireContext())
            radioButton.text =
                viewModel.questions[viewModel.position].answerOptions?.get(i)?.let {
                    getString(it)
                }
            binding.radioGroup.addView(
                radioButton,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            if (i == viewModel.answer) radioButton.isChecked = true
        }

        setListeners()
        setObservers()

    }

    private fun isPreviousButtonVisible(isVisible: Boolean) {
        if (isVisible) {
            binding.previousButton.visibility = View.VISIBLE
        } else {
            binding.previousButton.visibility = View.INVISIBLE
            binding.toolbar.navigationIcon = null
        }
    }

    private fun isNextButtonEnabled(isEnabled: Boolean) {
        binding.nextButton.isEnabled = isEnabled
    }

    private fun setListeners() {
        binding.run {
            nextButton.setOnClickListener {
                viewModel.onNextButtonClicked()
            }

            previousButton.setOnClickListener {
                viewModel.onPreviousButtonClicked()
            }

            toolbar.setNavigationOnClickListener {
                viewModel.onPreviousButtonClicked()
            }

            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val checkedButton = group.findViewById<RadioButton>(checkedId)
                viewModel.onAnswerChosen(group.indexOfChild(checkedButton))
                callbacks?.onAnswerChosen(viewModel.answer)
            }
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner) {
                if (viewModel.position > 0) {
                    viewModel.onPreviousButtonClicked()
                } else {
                    ExitDialog().show(parentFragmentManager, ExitDialog.TAG)
                }
            }

    }

    private fun setObservers() {
        viewModel.enableNextButtonLiveData.observe(
            viewLifecycleOwner,
            {
                isNextButtonEnabled(it)
            }
        )

        viewModel.positionLiveData.observe(
            viewLifecycleOwner,
            { newPosition -> changeQuestion(newPosition) })

        viewModel.submitLiveData.observe(viewLifecycleOwner, { submit() })
    }

    private fun changeQuestion(newPosition: Int) {
        callbacks?.onChangeQuestionClicked(newPosition)
    }

    private fun submit() {
        callbacks?.onSubmitButtonClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {

        const val QUIZ_FRAGMENT_ID = "quiz_fragment"
        private const val POSITION = "position"
        private const val QUESTIONS = "questions"
        private const val ANSWER = "answer"

        fun newInstance(
            position: Int,
            questions: List<QuestionItem>,
            answer: Int,
        ): QuizFragment {

            val fragment = QuizFragment()
            fragment.arguments = bundleOf(
                POSITION to position,
                QUESTIONS to questions,
                ANSWER to answer,
            )
            return fragment
        }
    }
}
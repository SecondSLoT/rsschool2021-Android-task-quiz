package com.rsschool.quiz.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rsschool.quiz.R
import com.rsschool.quiz.data.repository.model.QuestionItem
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.extentions.capitalize
import com.rsschool.quiz.vm.QuizFragmentViewModel


class QuizFragment : Fragment() {

    private val viewModel by viewModels<QuizFragmentViewModel>()
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var callbacks: Callbacks? = null

    interface Callbacks {

        fun paintStatusBar()

        fun onChangeQuestionClicked(newPosition: Int, prevAnswer: Int, prevCheckedButtonId: Int)

        fun onSubmitButtonClicked(prevAnswer: Int, prevCheckedButtonId: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Callbacks) {
            callbacks = context
        } else {
            throw RuntimeException("$context must implement QuizFragment.Callbacks")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        callbacks?.paintStatusBar()
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
        viewModel.checkedButtonId = arguments?.getInt(CHECKED_BUTTON_ID) ?: -1

        if (viewModel.position != viewModel.questions.size - 1) {
            binding.nextButton.text = context?.getString(R.string.next)
        } else {
            binding.nextButton.text = context?.getString(R.string.submit)
        }

        if (viewModel.position == 0) isPreviousButtonVisible(false)

        binding.run {
            toolbar.title = "${context?.getString(R.string.question)?.capitalize()} " +
                    "${viewModel.position + 1} " +
                    "${context?.getString(R.string.of)} ${viewModel.questions.size}"
            question.text = viewModel.questions[viewModel.position].question
            optionOne.text = viewModel.questions[viewModel.position].answerOptions?.get(0)
            optionTwo.text = viewModel.questions[viewModel.position].answerOptions?.get(1)
            optionThree.text = viewModel.questions[viewModel.position].answerOptions?.get(2)
            optionFour.text = viewModel.questions[viewModel.position].answerOptions?.get(3)
            optionFive.text = viewModel.questions[viewModel.position].answerOptions?.get(4)

            if (viewModel.checkedButtonId != -1) {
                radioGroup.check(viewModel.checkedButtonId)
            } else {
                isNextButtonEnabled(false)
            }

            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val checkedButton = group.findViewById<RadioButton>(checkedId)
                viewModel.onAnswerChosen(checkedId, group.indexOfChild(checkedButton))
            }
        }

        setListeners()
        setObservers()

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.onPreviousButtonClicked()
                }
            })
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
        callbacks?.onChangeQuestionClicked(
            newPosition,
            viewModel.answer,
            viewModel.checkedButtonId
        )
    }

    private fun submit() {
        callbacks?.onSubmitButtonClicked(viewModel.answer, viewModel.checkedButtonId)
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

        private const val POSITION = "position"
        private const val QUESTIONS = "questions"
        private const val ANSWER = "answer"
        private const val CHECKED_BUTTON_ID = "checked_button_id"

        fun newInstance(
            position: Int,
            questions: List<QuestionItem>,
            answer: Int,
            checkedButtonId: Int
        ): QuizFragment {

            val fragment = QuizFragment()
            fragment.arguments = bundleOf(
                POSITION to position,
                QUESTIONS to questions,
                ANSWER to answer,
                CHECKED_BUTTON_ID to checkedButtonId
            )
            return fragment
        }
    }
}
package com.rsschool.quiz.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rsschool.quiz.R
import com.rsschool.quiz.data.repository.model.QuestionItem
import com.rsschool.quiz.databinding.FragmentResultBinding
import com.rsschool.quiz.extentions.capitalize
import com.rsschool.quiz.vm.ResultFragmentViewModel

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<ResultFragmentViewModel>()
    private var callbacks: Callbacks? = null
    private var callbackChangeTheme: ChangeTheme? = null

    interface Callbacks {
        fun onRestartButtonClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Callbacks) {
            callbacks = context
        } else {
            throw RuntimeException("$context must implement ResultFragment.Callbacks")
        }

        if (context is ChangeTheme) {
            callbackChangeTheme = context
        } else {
            throw RuntimeException("$context must implement ChangeTheme")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        callbackChangeTheme?.changeTheme()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.questions =
            arguments?.getParcelableArrayList(QUESTIONS) ?: emptyList()
        viewModel.answers = arguments?.getIntArray(ANSWERS) ?: intArrayOf()

        context?.let { viewModel.getStatistics(it) }

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.run {
            restartImageButton.setOnClickListener { viewModel.onRestartButtonClicked() }
            shareImageButton.setOnClickListener { viewModel.onShareButtonClicked() }
            exitImageButton.setOnClickListener { viewModel.onExitButtonClicked() }
        }
    }

    private fun setObservers() {

        viewModel.resultInPercentLiveData.observe(
            viewLifecycleOwner,
            { binding.resultPercentTextView.text = it }
        )
        viewModel.resultLiveData.observe(
            viewLifecycleOwner,
            { binding.resultTextView.text = it }
        )
        viewModel.restartQuizLiveData.observe(
            viewLifecycleOwner, { if (it) callbacks?.onRestartButtonClicked() }
        )
        viewModel.shareLiveData.observe(
            viewLifecycleOwner, { if (it) sendReport() }
        )
    }

    private fun sendReport() {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, context?.getString(R.string.quiz_results)?.capitalize())
            putExtra(Intent.EXTRA_TEXT, context?.let { viewModel.statistics?.getReport(it) })
        }.also {intent ->
            startActivity(intent)
        }
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
        private const val QUESTIONS = "QUESTIONS"
        private const val ANSWERS = "ANSWERS"

        @JvmStatic
        fun newInstance(questions: List<QuestionItem>, answers: IntArray): ResultFragment {
            val fragment = ResultFragment()
            fragment.arguments = bundleOf(QUESTIONS to questions, ANSWERS to answers)
            return fragment
        }
    }
}
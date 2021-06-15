package com.rsschool.quiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.vm.QuizFragmentViewModel

class QuizFragment : Fragment() {

    private val viewModel by viewModels<QuizFragmentViewModel>()
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
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
        binding.question.text = viewModel.questions[0].question
    }

    companion object {

        fun newInstance(): QuizFragment {
            return QuizFragment()
        }

    }
}
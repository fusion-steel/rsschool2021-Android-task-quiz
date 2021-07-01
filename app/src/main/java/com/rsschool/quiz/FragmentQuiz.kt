package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

class FragmentQuiz : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private var currentID = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        currentID = requireArguments().getInt(QUESTION_ID)
        binding.toolbar.title = "Question ${ currentID + 1 }"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigator().launchPrevious() }
            })

        setQuestion()

        val currentAnswer = UserAnswers.answersID.getOrNull(currentID)
        if (currentAnswer != null) {
            checkRadioButton(currentAnswer)
            binding.nextButton.isEnabled = true
        }

        binding.nextButton.setOnClickListener {
            addAnswer(binding.radioGroup.indexOfChild(
                binding.radioGroup.findViewById(
                    binding.radioGroup.checkedRadioButtonId)))
            navigator().launchNext() }

        binding.previousButton.setOnClickListener {
            navigator().launchPrevious() }

        if (currentID > 0) {
            binding.previousButton.isEnabled = true
        } else {
            binding.toolbar.navigationIcon = null
        }

        if (currentID == Content.questions.lastIndex) {
            binding.nextButton.text = "Submit"
        }

        binding.toolbar.setNavigationOnClickListener { navigator().launchPrevious() }

        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            binding.nextButton.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setQuestion() {
        val currentQuestion = Content.questions[currentID]
        binding.question.text = currentQuestion.question

        binding.option1.text = currentQuestion.answers[0]
        binding.option2.text = currentQuestion.answers[1]
        binding.option3.text = currentQuestion.answers[2]
        binding.option4.text = currentQuestion.answers[3]
        binding.option5.text = currentQuestion.answers[4]
    }

    private fun addAnswer(id: Int) {
        val answers = UserAnswers.answersID
        if (answers.getOrNull(currentID) != null) {
            answers[currentID] = id
        } else {
            answers.add(id)
        }
        println(answers.toString())
    }

    private fun checkRadioButton(id: Int) {
        when (id) {
            0 -> binding.option1.isChecked = true
            1 -> binding.option2.isChecked = true
            2 -> binding.option3.isChecked = true
            3 -> binding.option4.isChecked = true
            4 -> binding.option5.isChecked = true
        }
    }

    companion object {
        private const val QUESTION_ID = "questionID"

        @JvmStatic
        fun newInstance(question: Int) = FragmentQuiz().apply {
            arguments = bundleOf(QUESTION_ID to question)
        }
    }
}
package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

class FragmentQuiz : Fragment() {

    private lateinit var binding: FragmentQuizBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUI(getRandomQuestion())
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getRandomQuestion(): Question {
        return Content.questions.shuffled().first()
    }

    private fun setUI(question: Question) {
        binding.question.text = question.question
        for ((index, answer) in question.answers.withIndex()) {
            val radioGroup = binding.radioGroup
            val radioButton = RadioButton(context)
            radioButton.apply {
                id = index
                text = answer
                textSize = 20F
                height = 180
            }
            radioGroup.addView(radioButton)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentQuiz()
    }
}
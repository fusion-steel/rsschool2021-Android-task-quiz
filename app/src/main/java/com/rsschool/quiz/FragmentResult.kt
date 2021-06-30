package com.rsschool.quiz

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding

class FragmentResult : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val questionAnswerPair = Content.questions.zip(UserAnswers.answersID)
    private val result = calcResult()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigator().finish() }
            })

        binding.tvResult.text = String.format(resources.getString(R.string.result), result)

        binding.share.setOnClickListener { shareText() }
        binding.reload.setOnClickListener { navigator().startQuiz() }
        binding.close.setOnClickListener { navigator().finish() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calcResult(): Int {
        var correctAnswerSum = 0

        for (i in questionAnswerPair) {
            if (i.first.correctAnswer == i.second)
                correctAnswerSum++
        }

        return (correctAnswerSum.toDouble() / questionAnswerPair.size * 100).toInt()
    }

    private fun shareText() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, prepareTextToShare())
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun prepareTextToShare(): String {
        val string = StringBuilder()

        string.append("Your result: $result %\n\n")
        for ((index, pair) in questionAnswerPair.withIndex()) {
            string.append("${ index + 1 }) ${ pair.first.question }\n")
            string.append("Your answer: ${ pair.first.answers[pair.second] }\n\n")
        }
        return string.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentResult()
    }
}
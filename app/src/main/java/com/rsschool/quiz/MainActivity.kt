package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    private var current = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            startQuiz()
        }

    }

    override fun startQuiz() {
        current = 0
        changeTheme()
        UserAnswers.answersID.clear()
        val fragment = FragmentQuiz.newInstance(current)
        supportFragmentManager
            .beginTransaction()
            .replace(binding.mainContainer.id, fragment)
            .commit()
    }

    override fun launchNext() {
        if (current == Content.questions.size - 1) {
            launchResult()
        } else {
            current++
            changeTheme()
            supportFragmentManager
                .beginTransaction()
                .replace(binding.mainContainer.id, FragmentQuiz.newInstance(current))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun launchPrevious() {
        dropLastAnswer()
        if (current > 0) {
            current--
            changeTheme()
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    override fun launchResult() {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.mainContainer.id, FragmentResult.newInstance())
            .commit()
    }

    private fun changeTheme() {
        val currentTheme = when (current) {
            0 -> R.style.Theme_Quiz_First
                .also { window.statusBarColor = getColor(R.color.deep_orange_100_dark) }
            1 -> R.style.Theme_Quiz_Second
                .also { window.statusBarColor = getColor(R.color.yellow_100_dark) }
            2 -> R.style.Theme_Quiz_Third
                .also { window.statusBarColor = getColor(R.color.light_green_100_dark) }
            3 -> R.style.Theme_Quiz_Fourth
                .also { window.statusBarColor = getColor(R.color.cyan_100_dark) }
            else -> R.style.Theme_Quiz_Fifth
                .also { window.statusBarColor = getColor(R.color.deep_purple_100_dark) }
        }
        setTheme(currentTheme)
    }

    private fun dropLastAnswer() {
//        println("remove ${ UserAnswers.answersID.last() }")
        UserAnswers.answersID.removeAt(UserAnswers.answersID.lastIndex)
    }
}
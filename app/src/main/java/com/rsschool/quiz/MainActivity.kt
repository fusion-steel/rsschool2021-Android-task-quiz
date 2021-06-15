package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.mainContainer.id, createFragment())
                .commit()
        }
    }

    private fun createFragment(): FragmentQuiz {
        return FragmentQuiz.newInstance()
    }

    private fun launchNextFragment() {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(binding.mainContainer.id, createFragment())
            .commit()
    }
}
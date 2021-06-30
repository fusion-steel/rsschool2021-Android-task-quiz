package com.rsschool.quiz

import androidx.fragment.app.Fragment

interface Navigator {
    fun startQuiz()
    fun launchNext()
    fun launchPrevious()
    fun launchResult()
    fun finish()
}

fun Fragment.navigator(): Navigator = requireActivity() as Navigator
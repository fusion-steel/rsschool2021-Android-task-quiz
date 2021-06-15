package com.rsschool.quiz

data class Question(
    val question: String,
    val answers: List<String>,
    val correctAnswer: Int
)
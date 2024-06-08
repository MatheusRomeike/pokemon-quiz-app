package com.example.myrecyclerviewapplication.controller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myrecyclerviewapplication.compose.PokemonQuizScreen

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonQuizScreen()
        }
    }
}

package com.example.myrecyclerviewapplication.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.myrecyclerviewapplication.compose.PokemonQuizScreen
import com.example.myrecyclerviewapplication.model.user.User
import com.example.myrecyclerviewapplication.model.user_score.UserScore
import com.example.myrecyclerviewapplication.viewmodel.PokemonViewModel
import com.example.myrecyclerviewapplication.viewmodel.UserScoreViewModel
import com.example.myrecyclerviewapplication.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : ComponentActivity() {
    private val pokeDatabase: PokemonViewModel by viewModels()
    private val user: UserViewModel by viewModels()
    private val scoreView: UserScoreViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokeDatabase.getAll()
        val pokemons = pokeDatabase.pokemonsLiveData.value
        user.getCurrentUser()
        val currentUser = user.userData.value

        if (pokemons.isNullOrEmpty()) {
            throw IllegalStateException("Empty Pokemon List")
        }

        setContent {
            PokemonQuizScreen(pokemons, currentUser!!, finishQuiz = { score -> finishQuiz(score) })
        }
    }

    private fun finishQuiz(score: Int) {
        Toast.makeText(this, "Quiz finalizado, Score Total: $score", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, HomeActivity::class.java))
        user.getCurrentUser()
        val currentUser = user.userData.value
        scoreView.insert(UserScore(userId = currentUser!!.id, score = score))
        finish()
    }
}

package com.example.myrecyclerviewapplication.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.myrecyclerviewapplication.compose.HomeScreen
import com.example.myrecyclerviewapplication.compose.PokemonQuizScreen
import com.example.myrecyclerviewapplication.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : ComponentActivity() {
    private val pokeDatabase: PokemonViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokeDatabase.getAll()
        val pokemons = pokeDatabase.pokemonsLiveData.value
        if (pokemons.isNullOrEmpty()) {
            throw IllegalStateException("Empty Pokemon List")
        }

        setContent {
            PokemonQuizScreen(pokemons)
        }
    }
}

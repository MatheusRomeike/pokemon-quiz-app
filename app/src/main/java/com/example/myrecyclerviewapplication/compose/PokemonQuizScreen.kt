package com.example.myrecyclerviewapplication.compose

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myrecyclerviewapplication.R
import kotlinx.coroutines.delay

import com.example.myrecyclerviewapplication.model.pokemon.Pokemon
import java.util.Locale

@Composable
fun PokemonQuizScreen(pokemons:  List<Pokemon>) {
    var changeQuiz by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val shuffledPokemons = pokemons.shuffled()
    val randomPokemons = shuffledPokemons.take(4)
    val correctPokemon = randomPokemons[0];
    val correctAnswer = correctPokemon.name;
    val options = randomPokemons.map { poke -> poke.name.replaceFirstChar { it.uppercase() } }.shuffled()

//    Log.d("TESTE", "Pokemon 1: $pokemons.get(0)")

    LaunchedEffect(Unit) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.quem_e_esse_pokemon)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
        }
        delay(3000)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RevealingPokemon(
            imageUrl = correctPokemon.urlImage,
            changeQuiz
        )
        Text("Quem é esse Pokémon?", style = TextStyle(fontSize = 24.sp, color = Color.Black))
        Spacer(modifier = Modifier.height(16.dp))
        Quiz(correctPokemon = correctPokemon, options = options, changeQuiz) {
            changeQuiz = it
        }
    }
}
@Composable
fun RevealingPokemon(imageUrl: String, changeQuiz: Boolean) {
    var animationPlayed by remember { mutableStateOf(false) }
    val colorAlphaAnim = remember { Animatable(1f) }

//    LaunchedEffect(changeQuiz) {
//        animationPlayed = false
//    }

    LaunchedEffect(changeQuiz) {
        Log.d("TESTE", "$animationPlayed")
        animationPlayed = false
        colorAlphaAnim.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 3000,
                easing = LinearEasing
            )
        )
        animationPlayed = true
    }

    if (!animationPlayed) {
        BlackPokemon(imageUrl = imageUrl)
    } else {
        AnimatedPokemon(imageUrl = imageUrl)
    }
}

@Composable fun Quiz(correctPokemon: Pokemon, options: List<String>,changeQuiz: Boolean, onOptionSelected: (Boolean) -> Unit) {
    var selectedOption by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(selectedOption) {
        if (selectedOption != null) {
            delay(500) // Aguarda 2 segundos
            onOptionSelected(!changeQuiz)
            selectedOption = null
        }
    }

    options.forEachIndexed { index, option ->
        val backgroundColor = when {
            selectedOption == null -> Color.Blue
            option.lowercase() == correctPokemon.name.lowercase() -> Color.Green
            option.lowercase() != correctPokemon.name.lowercase() -> Color.Red
            else -> Color.Transparent
        }
        Button(
            onClick = {
                selectedOption = option
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
        ) {
            Text(option, color = Color.White)
        }
    }
}

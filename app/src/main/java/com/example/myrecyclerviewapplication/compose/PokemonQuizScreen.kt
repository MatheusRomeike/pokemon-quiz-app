package com.example.myrecyclerviewapplication.compose

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myrecyclerviewapplication.R
import kotlinx.coroutines.delay

import com.example.myrecyclerviewapplication.model.pokemon.Pokemon
import com.example.myrecyclerviewapplication.model.user.User

@Composable
fun PokemonQuizScreen(pokemons:  List<Pokemon>, currentUser: User, finishQuiz: (Int) -> Unit,) {
    var countQuiz by remember { mutableIntStateOf(0) }
    var totalScore by remember { mutableIntStateOf(0) }

    if (countQuiz == 10) {
        finishQuiz(totalScore)
    }

    val context = LocalContext.current

    val shuffledPokemons = pokemons.shuffled()
    val randomPokemons = shuffledPokemons.take(4)
    val correctPokemon = randomPokemons[0];
    val options = randomPokemons.map { poke -> poke.name.replaceFirstChar { it.uppercase() } }.shuffled()


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
            countQuiz
        )
        Text("Quem é esse Pokémon?", style = TextStyle(fontSize = 24.sp, color = Color.Black))
        Spacer(modifier = Modifier.height(16.dp))
        Quiz(
            correctPokemon = correctPokemon,
            options = options,
            countQuiz = countQuiz,
            totalScore = totalScore,
            onOptionSelected = {
                countQuiz = it
            },
            addScore = {
                totalScore = it
            }
        )
    }
}
@Composable
fun RevealingPokemon(imageUrl: String, countQuiz: Int) {
    var animationPlayed by remember { mutableStateOf(false) }
    val colorAlphaAnim = remember { Animatable(1f) }

    LaunchedEffect(countQuiz) {
        animationPlayed = false
        colorAlphaAnim.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 2000,
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

@Composable fun Quiz(
    correctPokemon: Pokemon,
    options: List<String>,
    countQuiz: Int,
    totalScore: Int,
    onOptionSelected: (Int) -> Unit,
    addScore: (Int) -> Unit
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var startTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(selectedOption) {
        if (selectedOption != null) {
            val timeElapsed = System.currentTimeMillis() - startTime
            val score = calculateScore(timeElapsed)
            delay(500)


            onOptionSelected(countQuiz + 1)
            addScore(totalScore + score)
            selectedOption = null
            startTime = System.currentTimeMillis()
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
            enabled = countQuiz < 10,
            colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
        ) {
            Text(option, color = Color.White)
        }
    }
}

fun calculateScore(timeElapsed: Long): Int {
    val score = when {
        timeElapsed < 1500 -> 100
        timeElapsed < 2000 -> 80
        timeElapsed < 2500 -> 60
        timeElapsed < 3000 -> 40
        timeElapsed < 3500 -> 20
        else -> 0
    }
    return (score)
}
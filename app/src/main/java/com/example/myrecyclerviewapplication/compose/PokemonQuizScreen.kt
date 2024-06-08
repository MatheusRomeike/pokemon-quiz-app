package com.example.myrecyclerviewapplication.compose

import android.media.MediaPlayer
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myrecyclerviewapplication.R
import kotlinx.coroutines.delay

import android.widget.Toast

@Composable
fun PokemonQuizScreen() {
    var showPokemon by remember { mutableStateOf(false) }
    var userAnswer by remember { mutableStateOf("") }
    var showAnswer by remember { mutableStateOf(false) }
    val correctAnswer = "Pikachu" // Você pode mudar isso para fazer um quiz dinâmico
    val options = listOf("Bulbasaur", "Charmander", "Squirtle", "Pikachu")
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.quem_e_esse_pokemon)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
        }
        delay(7000)
        showPokemon = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showPokemon) {
            AnimatedPokemon(image = painterResource(id = R.drawable.pikachu))
            Spacer(modifier = Modifier.height(16.dp))

        } else {
            RevealingPokemon(image = painterResource(id = R.drawable.pikachu))
        }
        Text("Quem é esse Pokémon?", style = TextStyle(fontSize = 24.sp, color = Color.Black))
        Spacer(modifier = Modifier.height(16.dp))
        options.forEach { option ->
            Button(
                onClick = {
                    userAnswer = option
                    showAnswer = true
                    val message = if (userAnswer.equals(correctAnswer, ignoreCase = true)) "Correto! É o $correctAnswer!" else "Errado! O correto é $correctAnswer."
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                enabled = !showAnswer // Desabilita o botão se a resposta já foi mostrada
            ) {
                Text(option)
            }
        }
        if (showAnswer) {
            Text(
                text = "",
                style = TextStyle(fontSize = 20.sp, color = Color.Black)
            )
        }
    }
}

@Composable
fun RevealingPokemon(image: Painter) {
    var animationPlayed by remember { mutableStateOf(false) }
    val colorAlphaAnim = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        colorAlphaAnim.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 5000,
                easing = LinearEasing
            )
        )
        animationPlayed = true

    }

    val color = Color.Black.copy(alpha = colorAlphaAnim.value)
    if (!animationPlayed) {
        Image(
            painter = image,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
    } else {
        AnimatedPokemon(image = image)
    }
}

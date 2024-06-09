package com.example.myrecyclerviewapplication.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myrecyclerviewapplication.R

@Composable
fun HomeScreen(onQuizClicked: () -> Unit, onScoreboardClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PokemonImage("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onQuizClicked() }) {
            Text(text = "Quiz")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onScoreboardClicked() }) {
            Text(text = "Scoreboard")
        }
    }
}

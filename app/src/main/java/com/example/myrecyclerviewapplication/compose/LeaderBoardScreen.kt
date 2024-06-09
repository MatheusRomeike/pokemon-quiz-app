package com.example.myrecyclerviewapplication.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myrecyclerviewapplication.model.user_score.UserScoreWithName


@Composable
fun LeaderboardScreen(scoresList:  List<UserScoreWithName>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Leaderboard", color = Color.White) },
                backgroundColor = Color(0xFF6200EE),
                contentColor = Color.White
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Top 10 Pontuadores",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                LeaderboardList(scoresList)
            }
        }
    )
}


@Composable
fun LeaderboardList(entries: List<UserScoreWithName>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(entries) { index, entry ->
            LeaderboardItem(entry, index + 1)
        }
    }
}

@Composable
fun LeaderboardItem(entry: UserScoreWithName, rank: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$rank.",
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = entry.name,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = entry.score.toString(),
            fontSize = 16.sp,
            color = Color.Black
        )
    }

}

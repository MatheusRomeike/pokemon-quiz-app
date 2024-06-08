package com.example.myrecyclerviewapplication.controller


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myrecyclerviewapplication.compose.LeaderboardScreen

class LeaderBoardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeaderboardScreen()
        }
    }
}
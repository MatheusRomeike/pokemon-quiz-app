package com.example.myrecyclerviewapplication.controller


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.myrecyclerviewapplication.compose.LeaderboardScreen
import com.example.myrecyclerviewapplication.model.user_score.UserScoreWithName
import com.example.myrecyclerviewapplication.viewmodel.UserScoreViewModel
import com.example.myrecyclerviewapplication.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderBoardActivity : ComponentActivity() {
    private val scoreView: UserScoreViewModel by viewModels()
    private val user: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scoreView.getAll()
        val scoreList = scoreView.userScoreListData.value

        val userScoresWithNames = scoreList!!.map { userScore ->
            user.getById(userScore.userId)
            val scoreUser = user.userData.value
            UserScoreWithName(name = scoreUser!!.name, score = userScore.score)
        }


        setContent {
            LeaderboardScreen(userScoresWithNames)
        }

    }
}
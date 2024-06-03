package com.example.myrecyclerviewapplication.model.user_score

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_score")
data class UserScore(
    @PrimaryKey var userId:Int,
    var score:Int,
)
package com.example.myrecyclerviewapplication.model.user_score

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserScoreDao {

    @Insert
    fun insert(userScore: UserScore)

    @Query("SELECT * FROM user_score WHERE userId = :userId")
    fun getByUserId(userId: Int): UserScore

    @Query("UPDATE user_score SET score = :score WHERE userId = :userId")
    fun update(userId: Int, score: Int): Int?
}

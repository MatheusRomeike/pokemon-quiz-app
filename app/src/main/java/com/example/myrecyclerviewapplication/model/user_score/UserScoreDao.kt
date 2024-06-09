package com.example.myrecyclerviewapplication.model.user_score

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myrecyclerviewapplication.model.pokemon.Pokemon

@Dao
interface UserScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userScore: UserScore)

    @Query("SELECT * FROM user_score ORDER BY score DESC")
    fun getAll(): List<UserScore>

    @Query("SELECT * FROM user_score WHERE userId = :userId")
    fun getByUserId(userId: Int): UserScore

    @Query("UPDATE user_score SET score = :score WHERE userId = :userId")
    fun update(userId: Int, score: Int): Int?
}

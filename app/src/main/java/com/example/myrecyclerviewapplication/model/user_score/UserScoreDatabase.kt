package com.example.myrecyclerviewapplication.model.user_score


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserScore::class], version = 1)
abstract class UserScoreDatabase: RoomDatabase() {
    abstract fun userScoreDao(): UserScoreDao
}
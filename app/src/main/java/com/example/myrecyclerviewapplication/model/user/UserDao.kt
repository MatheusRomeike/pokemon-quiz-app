package com.example.myrecyclerviewapplication.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getById(userId: Int): User?

    @Query("SELECT * FROM users WHERE name = :name AND password = :password")
    fun getUserByNameAndPassword(name: String, password: String): User?

    @Query("SELECT * FROM users WHERE name = :name")
    fun getUserByName(name: String): User?
}

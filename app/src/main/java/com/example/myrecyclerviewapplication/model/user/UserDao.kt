package com.example.myrecyclerviewapplication.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

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

    @Query("SELECT * FROM users WHERE current = 1")
    fun getCurrentUser(): User?
    @Query("UPDATE users SET current = 0 WHERE current = 1")
    fun clearCurrentUser()

    @Query("UPDATE users SET current = 1 WHERE id = :userId")
    fun setCurrentUser(userId: Int)

    @Transaction
    fun updateCurrentUser(userId: Int) {
        clearCurrentUser()
        setCurrentUser(userId)
    }
}

package com.example.myrecyclerviewapplication.model.user_pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserPokemonDao {

    @Insert
    fun insert(userPokemon: UserPokemon)

    @Query("SELECT * FROM user_pokemon WHERE userId = :userId")
    fun getByUserId(userId: Int): List<UserPokemon>

    @Query("SELECT * FROM user_pokemon WHERE userId = :userId and pokemonId = :pokemonId")
    fun getByUserPokemonId(userId: Int, pokemonId: Int): UserPokemon

    @Query("DELETE FROM user_pokemon WHERE userId = :userId AND pokemonId = :pokemonId")
    fun delete(userId: Int, pokemonId: Int): Int?
}

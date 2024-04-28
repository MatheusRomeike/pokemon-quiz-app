package com.example.myrecyclerviewapplication.model.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PokemonDao {

    @Insert
    fun insert(pokemons: List<Pokemon>)

    @Query("SELECT * FROM pokemons WHERE id = :id")
    fun getById(id: Int): Pokemon?

    @Query("SELECT * FROM pokemons")
    fun getAll(): List<Pokemon>
}

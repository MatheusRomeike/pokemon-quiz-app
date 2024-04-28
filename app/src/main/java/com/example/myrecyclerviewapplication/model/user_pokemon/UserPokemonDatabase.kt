package com.example.myrecyclerviewapplication.model.user_pokemon


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserPokemon::class], version = 1)
abstract class UserPokemonDatabase: RoomDatabase() {
    abstract fun userPokemonDao(): UserPokemonDao
}
package com.example.myrecyclerviewapplication.model.user_pokemon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_pokemon", primaryKeys = ["userId", "pokemonId"])
data class UserPokemon(
    var userId:Int,
    var pokemonId:Int,
)
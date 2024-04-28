package com.example.myrecyclerviewapplication.model.user_pokemon

import javax.inject.Inject
import javax.inject.Singleton

class UserPokemonRepository @Inject constructor(var dao: UserPokemonDao) {
    lateinit var userPokemons: List<UserPokemon>
    public var userPokemon: UserPokemon? = null

    fun insert(userPokemon: UserPokemon) {
        dao.insert(userPokemon)
    }

    fun getByUserId(userId: Int) {
        userPokemons = dao.getByUserId(userId)
    }

    fun getByUserPokemonId(userId: Int, pokemonId: Int) {
        userPokemon = dao.getByUserPokemonId(userId, pokemonId)
    }

    fun delete(userId: Int, pokemonId: Int) {
        dao.delete(userId, pokemonId)
    }
}
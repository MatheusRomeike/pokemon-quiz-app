package com.example.myrecyclerviewapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrecyclerviewapplication.model.user.UserRepository
import com.example.myrecyclerviewapplication.model.user_pokemon.UserPokemon
import com.example.myrecyclerviewapplication.model.user_pokemon.UserPokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserPokemonViewModel @Inject constructor(private val userPokemonRepository: UserPokemonRepository)
    : ViewModel()  {
    val userPokemonsData = MutableLiveData<List<UserPokemon>>()
    val userPokemonData = MutableLiveData<UserPokemon>()

    fun insert(userPokemon: UserPokemon) {
        userPokemonRepository.insert(userPokemon)
        userPokemonsData.value = userPokemonRepository.userPokemons
    }

    fun getByUserId(userId: Int) {
        userPokemonRepository.getByUserId(userId)
        userPokemonsData.value = userPokemonRepository.userPokemons
    }

    fun getByUserPokemonId(userId: Int, pokemonId: Int) {
        userPokemonRepository.getByUserPokemonId(userId, pokemonId)
        userPokemonData.value = userPokemonRepository.userPokemon
    }

    fun delete(userId: Int, pokemonId: Int) {
        userPokemonRepository.delete(userId, pokemonId)
        userPokemonsData.value = userPokemonRepository.userPokemons
    }

    fun refresh(){
        userPokemonsData.value = userPokemonRepository.userPokemons
        userPokemonData.value = userPokemonRepository.userPokemon
    }
}

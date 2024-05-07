package com.example.myrecyclerviewapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrecyclerviewapplication.model.pokemon.Pokemon
import com.example.myrecyclerviewapplication.model.pokemon.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val pokemonRepository: PokemonRepository)
    : ViewModel() {
    val pokemonsLiveData = MutableLiveData<List<Pokemon>>()
    val pokemonLiveData = MutableLiveData<Pokemon>()

    fun insert(pokemons: List<Pokemon>) {
        pokemonRepository.insert(pokemons)
        pokemonsLiveData.value = pokemonRepository.pokemons
    }

    fun getById(id: Int) {
        pokemonRepository.getById(id)
        pokemonLiveData.value = pokemonRepository.pokemon
    }

    fun refresh() {
        pokemonsLiveData.value = pokemonRepository.pokemons
        pokemonLiveData.value = pokemonRepository.pokemon
    }

    fun getAll(){
        pokemonRepository.getAll()
        pokemonsLiveData.value = pokemonRepository.pokemons
    }
}

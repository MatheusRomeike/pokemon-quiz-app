package com.example.myrecyclerviewapplication.model.pokemon

import javax.inject.Inject

class PokemonRepository @Inject constructor(var dao: PokemonDao) {
    var pokemons =  dao.getAll()
    public var pokemon: Pokemon? = null

    fun insert(pokemon: List<Pokemon>){
        dao.insert(pokemons)
        pokemons = dao.getAll()
    }

    fun getById(id: Int){
        pokemon = dao.getById(id)
    }

    fun getAll(){
        pokemons = dao.getAll()
    }
}
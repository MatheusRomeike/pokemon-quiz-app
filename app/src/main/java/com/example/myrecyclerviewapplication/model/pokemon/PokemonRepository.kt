package com.example.myrecyclerviewapplication.model.pokemon

import javax.inject.Inject

class PokemonRepository @Inject constructor(var dao: PokemonDao) {
    var pokemons =  dao.getAll()
    public var pokemon: Pokemon? = null

    fun insert(pokemon: List<Pokemon>){
        dao.insert(pokemon)
        pokemons = dao.getAll()
    }

    fun getById(id: Int) : Pokemon? {
        return dao.getById(id)
    }

    fun getAll(){
        pokemons = dao.getAll()
    }
}
package com.example.myrecyclerviewapplication.controller

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecyclerviewapplication.R
import com.example.myrecyclerviewapplication.model.pokemon.Pokemon
import com.example.myrecyclerviewapplication.model.pokemon.PokemonRepository
import com.example.myrecyclerviewapplication.viewmodel.PokemonViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PokemonDetailsActivity : AppCompatActivity() {
    @Inject
    lateinit var pokemonRepository: PokemonRepository
    private val viewModel: PokemonViewModel by viewModels()

    private lateinit var imagePokemon: ImageView
    private lateinit var name : TextView
    private lateinit var weight : TextView
    private lateinit var  height : TextView
    private lateinit var type : TextView
    private lateinit var color : TextView
    private lateinit var baseXp : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokemon_view)

        imagePokemon = findViewById(R.id.imageViewPokemon)
        name = findViewById(R.id.pokemonViewName)
        weight = findViewById(R.id.weightValue)
        height = findViewById(R.id.heightValue)
        type = findViewById(R.id.typeValue)
        color = findViewById(R.id.colorValue)
        baseXp = findViewById(R.id.baseXpValue)


        val pokemonPosition = intent.getIntExtra("pokemonPosition",-1)
        print(pokemonPosition)
//        Log.e("Teste","${pokemonPosition}")
        if(pokemonPosition >= 0) {
            val pokemon = pokemonRepository.getById(pokemonPosition)
//            Log.e("Teste Pokemon","${pokemon}")
            Picasso.get()
                .load(pokemon!!.urlImage)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .into(imagePokemon)

            name.text = pokemon.name
            weight.text = pokemon.weight.toString()
            height.text = pokemon.height.toString()
            type.text = pokemon.type
            color.text = pokemon.color
            baseXp.text = pokemon.baseExperience.toString()
        }
        viewModel.getAll()
    }
}
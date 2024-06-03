// FusaoActivity.kt
package com.example.myrecyclerviewapplication.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecyclerviewapplication.R
import com.example.myrecyclerviewapplication.viewmodel.PokemonViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FusaoActivity : AppCompatActivity() {

    private val viewModel: PokemonViewModel by viewModels()

    private lateinit var spinnerPokemon1: Spinner
    private lateinit var spinnerPokemon2: Spinner
    private lateinit var imagePokemonFusion: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fusao_activity)

        spinnerPokemon1 = findViewById(R.id.spinner_pokemon_1)
        spinnerPokemon2 = findViewById(R.id.spinner_pokemon_2)
        imagePokemonFusion = findViewById(R.id.image_pokemon_fusion)

        viewModel.getAll()

        viewModel.pokemonsLiveData.observe(this) { pokemonList ->
            val pokemonNames = pokemonList
                .filter { it.id <= 151 }
                .map { it.name }

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                pokemonNames
            )

            spinnerPokemon1.adapter = adapter
            spinnerPokemon2.adapter = adapter
        }

        val updateFusionImage = {
            val selectedName1 = viewModel.pokemonsLiveData.value!!.filter { it.name == spinnerPokemon1.selectedItem as String }.map{ it.id}.first()
            val selectedName2 = viewModel.pokemonsLiveData.value!!.filter { it.name == spinnerPokemon2.selectedItem as String }.map{ it.id}.first()

            val url = "https://images.alexonsager.net/pokemon/fused/$selectedName1/$selectedName1.$selectedName2.png"

            Picasso.get()
                .load(url)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .into(imagePokemonFusion)
        }

        spinnerPokemon1.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                updateFusionImage()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        spinnerPokemon2.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                updateFusionImage()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_pokedex -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }

                R.id.action_sair -> {
                    startActivity(Intent(this, LoginRegisterActivity::class.java))
                    true
                }

                else -> {
                    false
                }
            }
        }
    }
}

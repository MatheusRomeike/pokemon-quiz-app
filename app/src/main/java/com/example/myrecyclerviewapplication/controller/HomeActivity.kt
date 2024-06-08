package com.example.myrecyclerviewapplication.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.myrecyclerviewapplication.compose.HomeScreen
import com.example.myrecyclerviewapplication.model.pokemon.Pokemon
import com.example.myrecyclerviewapplication.model.pokemon.PokemonRepository
import com.example.myrecyclerviewapplication.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val viewModel: PokemonViewModel by viewModels()
    @Inject
    lateinit var pokemonRepository: PokemonRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(
                onQuizClicked = {
                    startActivity(Intent(this, QuizActivity::class.java))
                },
                onScoreboardClicked = {
                    startActivity(Intent(this, LeaderBoardActivity::class.java))
                }
            )
        }

        loadPokemonList()
        viewModel.getAll()
        if (viewModel.pokemonsLiveData.value.isNullOrEmpty()) {
            fetchPokemonList()
        }
    }

    private fun fetchPokemonList() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://pokeapi.co/api/v2/pokemon/?offset=0&limit=151")
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                val inputStream = urlConnection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()

                bufferedReader.use { reader ->
                    var line = reader.readLine()
                    while (line != null) {
                        stringBuilder.append(line)
                        line = reader.readLine()
                    }
                }

                val jsonObject = JSONObject(stringBuilder.toString())
                val jsonArray = jsonObject.getJSONArray("results")

                val pokemonListToInsert = mutableListOf<Pokemon>()

                for (i in 0 until jsonArray.length()) {
                    val pokemonObject = jsonArray.getJSONObject(i)
                    val name = pokemonObject.getString("name")
                    val pokemonUrl = pokemonObject.getString("url")

                    val pokemonDetailsJson = URL(pokemonUrl).readText()
                    val pokemonDetailsObject = JSONObject(pokemonDetailsJson)

                    val id = pokemonDetailsObject.getInt("id")
                    val baseExperience = pokemonDetailsObject.getInt("base_experience")
                    val height = pokemonDetailsObject.getInt("height")
                    val isDefault = pokemonDetailsObject.getBoolean("is_default")
                    val order = pokemonDetailsObject.getInt("order")
                    val weight = pokemonDetailsObject.getInt("weight")

                    var urlImage = ""
                    if (pokemonDetailsObject.has("sprites")) {
                        val spritesObject = pokemonDetailsObject.getJSONObject("sprites")
                        urlImage = spritesObject.getString("front_default")
                    }

                    var typeName = ""
                    if (pokemonDetailsObject.has("types")) {
                        val typesArray = pokemonDetailsObject.getJSONArray("types")
                        if (typesArray.length() > 0) {
                            val firstTypeObject = typesArray.getJSONObject(0)
                            if (firstTypeObject.has("type")) {
                                val typeObject = firstTypeObject.getJSONObject("type")
                                if (typeObject.has("name")) {
                                    typeName = typeObject.getString("name")
                                }
                            }
                        }
                    }

                    var color = "white"
                    val speciesUrl = pokemonDetailsObject.getJSONObject("species").getString("url")
                    val speciesJson = URL(speciesUrl).readText()
                    val speciesObject = JSONObject(speciesJson)
                    try {
                        val colorUrl = URL(speciesObject.getJSONObject("color").getString("url"))
                        val colorJson = colorUrl.readText()
                        val colorObject = JSONObject(colorJson)
                        color = colorObject.getString("name")
                    } catch (e: Exception) {
                        Log.e("HomeActivity", "Error fetching Pokemon color: ${e.message}")
                    }

                    pokemonListToInsert.add(
                        Pokemon(
                            id = i + 1,
                            name = name,
                            baseExperience = baseExperience,
                            height = height,
                            isDefault = isDefault,
                            order = order,
                            weight = weight,
                            urlImage = urlImage,
                            color = color,
                            type = typeName
                        )
                    )
                }

                viewModel.pokemonsLiveData.postValue(pokemonListToInsert)
                pokemonRepository.insert(pokemonListToInsert)

            } catch (e: Exception) {
                Log.e("HomeActivity", "Error fetching Pokemon: ${e.message}")
            }
        }
    }

    private fun loadPokemonList() {
        try {
            viewModel.getAll()
        } catch (e: Exception) {
            Log.e("HomeActivity", "Error loading pokemons")
            }
    }
}
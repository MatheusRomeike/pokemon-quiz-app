import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecyclerviewapplication.R
import com.example.myrecyclerviewapplication.model.pokemon.Pokemon

class PokemonAdapter(private var pokemonList: List<Pokemon>, val clickListener: OnPokemonClickListener) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    interface OnPokemonClickListener{
        fun onPokemonClick(view:View,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)

        val backgroundColor = when (pokemon.type) {
            "normal" -> Color.parseColor("#A8A878") // Normal
            "fire" -> Color.parseColor("#F08030") // Fire
            "water" -> Color.parseColor("#6890F0") // Water
            "electric" -> Color.parseColor("#F8D030") // Electric
            "grass" -> Color.parseColor("#78C850") // Grass
            "ice" -> Color.parseColor("#98D8D8") // Ice
            "fighting" -> Color.parseColor("#C03028") // Fighting
            "poison" -> Color.parseColor("#A040A0") // Poison
            "ground" -> Color.parseColor("#E0C068") // Ground
            "flying" -> Color.parseColor("#A890F0") // Flying
            "psychic" -> Color.parseColor("#F85888") // Psychic
            "bug" -> Color.parseColor("#A8B820") // Bug
            "rock" -> Color.parseColor("#B8A038") // Rock
            "ghost" -> Color.parseColor("#705898") // Ghost
            "dragon" -> Color.parseColor("#7038F8") // Dragon
            "dark" -> Color.parseColor("#705848") // Dark
            "steel" -> Color.parseColor("#B8B8D0") // Steel
            "fairy" -> Color.parseColor("#EE99AC") // Fairy
            else -> Color.parseColor("#68A090") // Default (Unknown type)
        }

        Log.e("Teste Cor", "$backgroundColor")
        holder.itemView.setBackgroundColor(backgroundColor)
    }

    fun updateList(newList: List<Pokemon>) {
        pokemonList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = pokemonList.size

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewType: TextView = itemView.findViewById(R.id.textViewType)

        fun bind(pokemon: Pokemon) {
            textViewName.text = "${pokemon.name}"
            textViewType.text = "${pokemon.type}"
            itemView.setOnClickListener{
                clickListener.onPokemonClick(it, pokemon.id)
            }
        }
    }

}

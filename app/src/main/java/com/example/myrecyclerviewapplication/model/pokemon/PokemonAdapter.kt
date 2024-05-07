import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecyclerviewapplication.R
import com.example.myrecyclerviewapplication.model.pokemon.Pokemon

class PokemonAdapter(private var pokemonList: List<Pokemon>, private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)

        val backgroundColor = when (pokemon.color) {
            "black" -> R.color.black
            "blue" -> R.color.blue
            "brown" -> R.color.brown
            "gray" -> R.color.gray
            "green" -> R.color.green
            "pink" -> R.color.pink
            "purple" -> R.color.purple
            "red" -> R.color.red
            "white" -> R.color.white
            "yellow" -> R.color.yellow
            else -> R.color.pokemon_color
        }
        holder.itemView.setBackgroundResource(backgroundColor)
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

        }
    }

}

package com.jdccmobile.pokecombat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdccmobile.pokecombat.R
import com.jdccmobile.pokecombat.data.api.response.PokemonList
import com.jdccmobile.pokecombat.databinding.ItemPokemonBinding
import com.squareup.picasso.Picasso

class PokedexAdapter(private val onClickListener: (Int) -> Unit) : RecyclerView.Adapter<PokedexAdapter.PokemonViewHolder>() {

    private val pokemonList: MutableList<PokemonList> = mutableListOf()

    fun setPokemonList(newPokemonList: List<PokemonList>) {
        pokemonList.clear()
        pokemonList.addAll(newPokemonList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.item_pokemon, parent, false))
    }

    override fun getItemCount(): Int = pokemonList.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = pokemonList[position]
        holder.render(item, onClickListener)
    }


    // ViewHolder
    inner class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemPokemonBinding.bind(view)

        fun render(pokemon: PokemonList, onClickListener: (Int) -> Unit){ // se llama automaticamente por cada item
            binding.tvPkmName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            val pokemonId = pokemon.url.substringBeforeLast("/").substringAfterLast("/").toInt()
            binding.tvPkmId.text = String.format("#%03d", pokemonId)
            val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"
            Picasso.get().load(url).into(binding.ivPkmImage)

            itemView.setOnClickListener{
                onClickListener(pokemonId)
            }
        }
    }
}

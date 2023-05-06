package com.jdccmobile.pokecombat.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdccmobile.pokecombat.R
import com.jdccmobile.pokecombat.data.api.response.PokemonList
import com.jdccmobile.pokecombat.databinding.ItemPokemonBinding
import com.squareup.picasso.Picasso

class PokedexAdapter(private val pokemon: List<PokemonList>, private val onClickListener: (Int) -> Unit) : RecyclerView.Adapter<PokedexAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.item_pokemon, parent, false))
    }

    override fun getItemCount(): Int = pokemon.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = pokemon[position]
        holder.render(position, item, onClickListener)
    }

    // ViewHolder
    inner class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemPokemonBinding.bind(view)

        fun render(position: Int, pokemon: PokemonList, onClickListener: (Int) -> Unit){ // se llama automaticamente por cada item
            binding.tvPkmName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            binding.tvPkmId.text = String.format("#%03d", position + 1)
            val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${position + 1}.png"
            Picasso.get().load(url).into(binding.ivPkmImage)

            itemView.setOnClickListener{
                Log.i("TAG", "pulsado")
                onClickListener(position + 1)
            }
        }
    }
}






//itemView.setOnClickListener {
//                Log.i("JDJD", "PULSADO $position")
//                val context = itemView.context
//                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
//                val fragmentTransaction = fragmentManager.beginTransaction()
//                val selectPokemonFragment = SelectPokemonFragment()
//                fragmentTransaction.add(R.id.frSelectPokemonContainer, selectPokemonFragment)
//                fragmentTransaction.addToBackStack(null)
//                fragmentTransaction.commit()
//            }
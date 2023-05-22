package com.jdccmobile.pokecombat.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jdccmobile.pokecombat.R
import com.jdccmobile.pokecombat.databinding.ActivityPokedexBinding
import com.jdccmobile.pokecombat.ui.viewModel.PokedexViewModel
import com.jdccmobile.pokecombat.ui.adapter.PokedexAdapter
import com.jdccmobile.pokecombat.ui.view.SelectedPokemonFragment.Companion.POKEMON_ID
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PokedexActivity @Inject constructor() : AppCompatActivity() {

    private val pokedexViewModel: PokedexViewModel by viewModels()
    private lateinit var binding: ActivityPokedexBinding

    private var isLoading = false
    private var offset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokedexBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActivity()
        pokedexViewModel.getAllPokemons(offset) // first 20 pokemons
    }

    private fun initActivity() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvPokemon.layoutManager = GridLayoutManager(this, 2)

        val adapter = PokedexAdapter { pokemonId ->
            onItemSelected(pokemonId)
        }

        binding.rvPokemon.adapter = adapter

        // Load more data at the end of the recyclerview
        binding.rvPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                    // Llegaste al final de la lista y no se está cargando más datos
                    loadMoreData()
                }
            }
        })

        pokedexViewModel.pokemonsList.observe(this) { pokemon ->
            adapter.setPokemonList(pokemon)
            isLoading = false
            binding.pbLoadingNewPokemon.visibility = View.GONE
            binding.pbLoadingPokedex.visibility = View.GONE
            binding.rvPokemon.visibility = View.VISIBLE
        }
    }

    private fun loadMoreData(){
        isLoading = true
        binding.pbLoadingNewPokemon.visibility = View.VISIBLE
        offset += 20
        pokedexViewModel.getAllPokemons(offset)
        Log.i("JDJD", "se llego al final")



    }

    private fun onItemSelected(pokemonId: Int){
        val bundle = bundleOf(POKEMON_ID to pokemonId)
        val selectedPokemonFragment = SelectedPokemonFragment()
        selectedPokemonFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .add(R.id.frSelectedPokemonContainer, selectedPokemonFragment)
            .commit()
        binding.frSelectedPokemonContainer.visibility = View.VISIBLE
    }




}
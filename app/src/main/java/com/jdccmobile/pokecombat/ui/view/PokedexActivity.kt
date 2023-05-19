package com.jdccmobile.pokecombat.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokedexBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActivity()
        pokedexViewModel.initViewModel()
    }

    private fun initActivity() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvPokemon.layoutManager = GridLayoutManager(this, 2)
        pokedexViewModel.pokemonsList.observe(this) { pokemon ->
            binding.rvPokemon.adapter = PokedexAdapter(pokemon) { pokemonId ->
                onItemSelected(pokemonId)
            }
            binding.pbLoadingPokedex.visibility = View.GONE
            binding.rvPokemon.visibility = View.VISIBLE
        }
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
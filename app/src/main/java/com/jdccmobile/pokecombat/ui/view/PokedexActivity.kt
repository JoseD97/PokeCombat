package com.jdccmobile.pokecombat.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.jdccmobile.pokecombat.R
import com.jdccmobile.pokecombat.databinding.ActivityPokedexBinding
import com.jdccmobile.pokecombat.ui.ViewModel.PokedexViewModel
import com.jdccmobile.pokecombat.ui.adapter.PokedexAdapter
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

        pokedexViewModel.initViewModel()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvPokemon.layoutManager = GridLayoutManager(this, 2)
        pokedexViewModel.pokemonsList.observe(this, Observer { pokemon ->
            binding.rvPokemon.adapter = PokedexAdapter(pokemon) })
    }
//
//    private fun onItemSelected(pokemonId: Int){ // todo abrir fragment al pulsar el boton // poner sobra fuera del cardview
//        Toast.makeText(this, "$pokemonId", Toast.LENGTH_SHORT).show()
//        val bundle = bundleOf(POKEMON_ID to pokemonId)
//        val selectPokemonFragment = SelectPokemonFragment()
//        selectPokemonFragment.arguments = bundle
//        supportFragmentManager.beginTransaction()
//            .add(R.id.frSelectPokemonContainer, selectPokemonFragment)
//            .addToBackStack(null)
//            .commit()
//        binding.frSelectPokemonContainer.visibility = View.VISIBLE
//    }




}
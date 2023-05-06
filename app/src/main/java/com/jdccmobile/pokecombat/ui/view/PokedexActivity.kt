package com.jdccmobile.pokecombat.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
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

        initActivity()
        pokedexViewModel.initViewModel()
    }

    private fun initActivity() {
        initRecyclerView()
        // TODO OCULTAR VISTA CUANDO SE VUELVE CON EL BACK ON CLICK LISTENER DESDE EL COMBAT
        binding.frSelectedPokemonContainer.visibility = View.GONE
    }

    private fun initRecyclerView() {
        // TODO AÑADIR CIRCULO DE CARGA MIENTRAS SE CARGAN LOS POKEMONS
        binding.rvPokemon.layoutManager = GridLayoutManager(this, 2)
        pokedexViewModel.pokemonsList.observe(this) { pokemon ->
            binding.rvPokemon.adapter = PokedexAdapter(pokemon) { pokemonId ->
                onItemSelected(pokemonId)
            }
        }
    }

    private fun onItemSelected(pokemonId: Int){ // todo abrir fragment al pulsar el boton // poner sobra fuera del cardview
        Toast.makeText(this, "$pokemonId", Toast.LENGTH_SHORT).show()
        val selectedPokemonFragment = SelectedPokemonFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.frSelectedPokemonContainer, selectedPokemonFragment)
            .addToBackStack(null)
            .commit()
        binding.frSelectedPokemonContainer.visibility = View.VISIBLE
    }




}
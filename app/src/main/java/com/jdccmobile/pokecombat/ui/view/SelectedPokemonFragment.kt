package com.jdccmobile.pokecombat.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import com.jdccmobile.pokecombat.R
import com.jdccmobile.pokecombat.databinding.FragmentSelectedPokemonBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectedPokemonFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentSelectedPokemonBinding? = null
    private val binding get() = _binding!!

//    private val selectPokemonViewModel: SelectPokemonViewModel by viewModels()

    private var pokemonId: Int? = null
//    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokemonId = it.getInt(POKEMON_ID)
//            param2 = it.getString(ARG_PARAM2)
        }

//        selectPokemonViewModel.initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectedPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        selectPokemonViewModel.pokemonInfo.observe(requireActivity(), Observer { pokemonInfo ->
//            binding.tvSelectedPkmName.text = pokemonInfo.name
//            binding.tvSelectedPkmAttack.text = pokemonInfo.stats[1].base_stat.toString()
//        })

//        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonId}.png"
//        Picasso.get().load(url).into(binding.ivSelectedPkmImage)
        initListeners()
    }

    private fun initListeners() {
        binding.vBackground.setOnClickListener {  } // To block the background
        binding.ivSelectPkmCancel.setOnClickListener {
            requireActivity().findViewById<FragmentContainerView>(R.id.frSelectedPokemonContainer).visibility = View.GONE
            parentFragmentManager.popBackStack()
        }
        binding.btSelectPkm.setOnClickListener {
//            requireActivity().findViewById<FragmentContainerView>(R.id.frSelectedPokemonContainer).visibility = View.GONE
            val intent = Intent(requireActivity(), CombatActivity::class.java)
            startActivity(intent)

        }
    }

    companion object {
        const val POKEMON_ID = "pokemonId"
        //        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String) =
            SelectedPokemonFragment().apply {
                arguments = Bundle().apply {
                    putInt(POKEMON_ID, pokemonId!!)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
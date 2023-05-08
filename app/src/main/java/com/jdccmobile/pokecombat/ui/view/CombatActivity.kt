package com.jdccmobile.pokecombat.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jdccmobile.pokecombat.R
import com.jdccmobile.pokecombat.databinding.ActivityCombatBinding
import com.jdccmobile.pokecombat.domain.TurnResultModel
import com.jdccmobile.pokecombat.ui.viewModel.CombatViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.random
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.random.Random

@AndroidEntryPoint
class CombatActivity @Inject constructor() : AppCompatActivity() {

    private lateinit var binding: ActivityCombatBinding
    private val combatViewModel: CombatViewModel by viewModels()

    private var myPokemonHp = 0f
    private var rivalPokemonHp = 0f
    private var myPokemonName = ""
    private var rivalPokemonName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCombatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myPokemonId = intent.getIntExtra(MY_POKEMON_ID, 1)
        combatViewModel.initViewModel(myPokemonId)
        initUi()
        initListeners()

    }

    private fun initUi() {
        initMyPokemonInfo()
        initRivalPokemonInfo()

    }

    private fun initMyPokemonInfo() {
        combatViewModel.myPokemonInfo.observe(this) { pokemon ->
            myPokemonName = pokemon.name
            myPokemonHp = pokemon.stats[0].base_stat.toFloat()
            binding.tvMyPokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png"
            Picasso.get().load(url).into(binding.ivMyPokemon)

        }
    }

    private fun initRivalPokemonInfo() {
        combatViewModel.rivalPokemonInfo.observe(this) { pokemon ->
            rivalPokemonName = pokemon.name
            rivalPokemonHp = pokemon.stats[0].base_stat.toFloat()
            binding.tvRivalPokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png"
            Picasso.get().load(url).into(binding.ivRivalPokemon)

        }
    }

    private fun initListeners() {
        binding.ivAttack.setOnClickListener {
            // Moves -> 1 attack, 0 dodge
            val iaMove = Random.nextInt(2)
            // todo refactorizar
            val result = combatViewModel.getTurnResult(1, iaMove)
            if (result.iaIsDefeated) Toast.makeText(this, "VICTORIA", Toast.LENGTH_SHORT).show()
            if (result.userIsDefeated) Toast.makeText(this, "DERROTA", Toast.LENGTH_SHORT).show()
            updateHealthBar(result.userHP)
            updateDataCombat(result, 1, iaMove)
        }

        binding.ivDodge.setOnClickListener {
            val iaMove = Random.nextInt(2)
            val result = combatViewModel.getTurnResult(0, iaMove)
            if (result.iaIsDefeated) Toast.makeText(this, "VICTORIA", Toast.LENGTH_SHORT).show()
            if (result.userIsDefeated) Toast.makeText(this, "DERROTA", Toast.LENGTH_SHORT).show()
            updateDataCombat(result, 0, iaMove)
        }
    }

    private fun updateHealthBar(userHP: Float) {
        val healthRectangle = myPokemonHp / 5
        val actualHealthRectangle = ceil(userHP / healthRectangle).toInt()
        when(actualHealthRectangle){
            1 ->{
                binding.vMyHP2.visibility = View.INVISIBLE
                binding.vMyHP3.visibility = View.INVISIBLE
                binding.vMyHP4.visibility = View.INVISIBLE
                binding.vMyHP5.visibility = View.INVISIBLE
            }
            2 -> {
                binding.vMyHP2.visibility = View.INVISIBLE
                binding.vMyHP3.visibility = View.INVISIBLE
                binding.vMyHP4.visibility = View.INVISIBLE
            }
            3 -> {
                binding.vMyHP3.visibility = View.INVISIBLE
                binding.vMyHP4.visibility = View.INVISIBLE
            }
            4 -> {
                binding.vMyHP5.visibility = View.INVISIBLE
            }
            5-> {}
            else -> {
                binding.vMyHP1.visibility = View.INVISIBLE
                binding.vMyHP2.visibility = View.INVISIBLE
                binding.vMyHP3.visibility = View.INVISIBLE
                binding.vMyHP4.visibility = View.INVISIBLE
                binding.vMyHP5.visibility = View.INVISIBLE

            }
        }

    }

    private fun updateDataCombat(result: TurnResultModel, userMove: Int, iaMove: Int) {
        // todo refactorizar
        var userMoveDescription = ""
        if (userMove == 1) userMoveDescription = "atacó!"
        else userMoveDescription = "esquivó!"
        val userPokemonName = binding.tvMyPokemonName.text

        var iaMoveDescription = ""
        if (iaMove == 1) iaMoveDescription = "atacó!"
        else iaMoveDescription = "esquivó!"
        val iaPokemonName = binding.tvRivalPokemonName.text

        // todo añadir los hp quitados
        binding.tvMyMove.text = "$userPokemonName $userMoveDescription"
//        binding.tvMyPokemonName.text = result.userHP.toString()
//        binding.tvRivalPokemonName.text = result.iaHP.toString()
        binding.tvRivalMove.text = "$iaPokemonName $iaMoveDescription"
    }
}
package com.jdccmobile.pokecombat.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jdccmobile.pokecombat.R
import com.jdccmobile.pokecombat.databinding.ActivityCombatBinding
import com.jdccmobile.pokecombat.databinding.CombatInfoDialogBinding
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
        createCombatInfoDialog()
        initMyPokemonInfo()
        initRivalPokemonInfo()

    }

    private fun createCombatInfoDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.combat_info_dialog, null)
        dialogBuilder.setView(dialogView)
        val dialog = dialogBuilder.create()
        val binding = CombatInfoDialogBinding.bind(dialogView)
        binding.btOkInfo.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    private fun initMyPokemonInfo() {
        combatViewModel.myPokemonInfo.observe(this) { pokemon ->
            myPokemonName = pokemon.name
            myPokemonHp = pokemon.stats[0].base_stat.toFloat()
            binding.tvMyPokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            val url =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png"
            Picasso.get().load(url).into(binding.ivMyPokemon)

        }
    }

    private fun initRivalPokemonInfo() {
        combatViewModel.rivalPokemonInfo.observe(this) { pokemon ->
            rivalPokemonName = pokemon.name
            rivalPokemonHp = pokemon.stats[0].base_stat.toFloat()
            binding.tvRivalPokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            val url =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png"
            Picasso.get().load(url).into(binding.ivRivalPokemon)

        }
    }

    private fun initListeners() {
        binding.ivAttack.setOnClickListener {
            // Moves -> 1 attack, 0 dodge
            val iaMove = Random.nextInt(2)
            val result = combatViewModel.getTurnResult(1, iaMove)
            // todo abrir ventana con victoria y quitar toast y juntar en ambos casos
            if (result.iaIsDefeated) Toast.makeText(this, "VICTORIA", Toast.LENGTH_SHORT).show()
            if (result.userIsDefeated) {
                val intent = Intent(this, PokedexActivity::class.java)
                startActivity(intent)

            }
            updateDataCombat(result, 1, iaMove)
            updateHealthBar(result.userHP, result.iaHP)
        }

        binding.ivDodge.setOnClickListener {
            val iaMove = Random.nextInt(2)
            val result = combatViewModel.getTurnResult(0, iaMove)
            // todo abrir ventana con victoria y quitar toast
            if (result.iaIsDefeated) Toast.makeText(this, "VICTORIA", Toast.LENGTH_SHORT).show()
            if (result.userIsDefeated) {
                val intent = Intent(this, PokedexActivity::class.java)
                startActivity(intent)
                finish()
            }
            updateDataCombat(result, 0, iaMove)
            updateHealthBar(result.userHP, result.iaHP)
        }
    }

    private fun updateDataCombat(result: TurnResultModel, userMove: Int, iaMove: Int) {
        val userMoveDescription = if (userMove == 1) "atacó!" else "esquivó!"
        val userPokemonName = binding.tvMyPokemonName.text

        val iaMoveDescription = if (iaMove == 1) "atacó!" else "esquivó!"
        val iaPokemonName = binding.tvRivalPokemonName.text

        // todo añadir los hp quitados
        val userMoveText = "$userPokemonName $userMoveDescription"
        binding.tvMyMove.text = userMoveText

        val iaMoveText = "$iaPokemonName $iaMoveDescription"
        binding.tvRivalMove.text = iaMoveText
    }

    private fun updateHealthBar(userHP: Float, iaHp: Float) {
        updateUserHealthBar(userHP)
        updateRivalHealthBar(iaHp)
    }

    private fun updateUserHealthBar(userHP: Float) {
        val healthRectangle = myPokemonHp / 5

        when (ceil(userHP / healthRectangle).toInt()) {
            1 -> {
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

            5 -> {}
            else -> {
                binding.vMyHP1.visibility = View.INVISIBLE
                binding.vMyHP2.visibility = View.INVISIBLE
                binding.vMyHP3.visibility = View.INVISIBLE
                binding.vMyHP4.visibility = View.INVISIBLE
                binding.vMyHP5.visibility = View.INVISIBLE

            }
        }
    }

    private fun updateRivalHealthBar(rivalHP: Float) {
        val healthRectangle = rivalPokemonHp / 5

        when (ceil(rivalHP / healthRectangle).toInt()) {
            1 -> {
                binding.vRivalHP2.visibility = View.INVISIBLE
                binding.vRivalHP3.visibility = View.INVISIBLE
                binding.vRivalHP4.visibility = View.INVISIBLE
                binding.vRivalHP5.visibility = View.INVISIBLE
            }

            2 -> {
                binding.vRivalHP2.visibility = View.INVISIBLE
                binding.vRivalHP3.visibility = View.INVISIBLE
                binding.vRivalHP4.visibility = View.INVISIBLE
            }

            3 -> {
                binding.vRivalHP3.visibility = View.INVISIBLE
                binding.vRivalHP4.visibility = View.INVISIBLE
            }

            4 -> {
                binding.vRivalHP5.visibility = View.INVISIBLE
            }

            5 -> {}
            else -> {
                binding.vRivalHP1.visibility = View.INVISIBLE
                binding.vRivalHP2.visibility = View.INVISIBLE
                binding.vRivalHP3.visibility = View.INVISIBLE
                binding.vRivalHP4.visibility = View.INVISIBLE
                binding.vRivalHP5.visibility = View.INVISIBLE

            }
        }
    }

}
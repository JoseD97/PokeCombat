package com.jdccmobile.pokecombat.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jdccmobile.pokecombat.R
import com.jdccmobile.pokecombat.databinding.ActivityCombatBinding
import com.jdccmobile.pokecombat.databinding.DialogCombatEndBinding
import com.jdccmobile.pokecombat.databinding.DialogCombatInfoBinding
import com.jdccmobile.pokecombat.domain.TurnResultModel
import com.jdccmobile.pokecombat.ui.viewModel.CombatViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.random.Random

@AndroidEntryPoint
class CombatActivity @Inject constructor() : AppCompatActivity() {

    private lateinit var binding: ActivityCombatBinding
    private val combatViewModel: CombatViewModel by viewModels()

    private var myPokemonId = 0
    private var myPokemonHp = 0f
    private var rivalPokemonHp = 0f
    private var myPokemonName = ""
    private var rivalPokemonName = ""
    private var victoriesCountDataStore = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCombatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myPokemonId = intent.getIntExtra(MY_POKEMON_ID, 1)
        combatViewModel.initViewModel(myPokemonId)
        initUI()
        initListeners()
    }

    private fun initUI() {

        combatViewModel.getIsInfoCombatShowed()
        createCombatInfoDialog()
        initMyPokemonInfo()
        initRivalPokemonInfo()
        getMaxVictories()


    }

    private fun getMaxVictories() {
        combatViewModel.getVictoriesCountDataStore()
        combatViewModel.victoriesCountDataStore.observe(this){ victories ->
            if(victories != null) victoriesCountDataStore = victories
            else victoriesCountDataStore = 99
            Log.i("JDJD", "victoriesCountDataStore $victoriesCountDataStore")
        }
    }

    private fun createCombatInfoDialog() {
        var wasDialogShowed : Boolean?
        combatViewModel.isInfoCombatShowed.observe(this){ wasDialogShowed = it
            if(!wasDialogShowed!!){
                val dialogBuilder = AlertDialog.Builder(this)
                val inflater = this.layoutInflater
                val dialogView = inflater.inflate(R.layout.dialog_combat_info, null)
                dialogBuilder.setView(dialogView)
                val dialog = dialogBuilder.create()
                val binding = DialogCombatInfoBinding.bind(dialogView)
                binding.btOkInfo.setOnClickListener {
                    combatViewModel.putIsInfoCombatShowed()
                    dialog.dismiss()
                }
                dialog.show()
            }
        }
    }

    private fun initMyPokemonInfo() {
        combatViewModel.myPokemonInfo.observe(this) { pokemon ->
            myPokemonName = pokemon.name
            myPokemonHp = pokemon.stats[0].base_stat.toFloat()
            binding.tvMyPokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png"
            Picasso.get().load(url).into(binding.ivMyPokemon)
            binding.ivMyPokemon.animate().alpha(1f).duration = 1000

        }
    }

    private fun initRivalPokemonInfo() {
        combatViewModel.rivalPokemonInfo.observe(this) { pokemon ->
            rivalPokemonName = pokemon.name
            rivalPokemonHp = pokemon.stats[0].base_stat.toFloat()
            binding.tvRivalPokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png"
            Picasso.get().load(url).into(binding.ivRivalPokemon)
            binding.ivRivalPokemon.animate().alpha(1f).duration = 1000

            binding.pbLoadingRivalImage.visibility = View.GONE
            binding.tvMyMove.visibility = View.VISIBLE
            binding.tvRivalMove.visibility = View.VISIBLE
        }


    }

    private fun initListeners() {
        binding.ivAttack.setOnClickListener {
            // Moves -> 2 superattack, 1 attack, 0 dodge
            val iaMove = Random.nextInt(3)
            val result = combatViewModel.getTurnResult(1, iaMove)
            combatViewModel.updateAttacksLoaded(result.isUserAttackLoaded, result.isIaAttackLoaded)
            if (!result.isUserAttackLoaded) binding.ivSuperAttackCharged.visibility = View.GONE
            newTurn(result, 1, iaMove)
        }

        binding.ivDodge.setOnClickListener {
            val iaMove = Random.nextInt(3)
            val result = combatViewModel.getTurnResult(0, iaMove)
            combatViewModel.updateAttacksLoaded(result.isUserAttackLoaded, result.isIaAttackLoaded)
            newTurn(result, 0, iaMove)
        }

        binding.ivSuperAttack.setOnClickListener {
            val iaMove = Random.nextInt(3)
            val result = combatViewModel.getTurnResult(2, iaMove)
            combatViewModel.updateAttacksLoaded(result.isUserAttackLoaded, result.isIaAttackLoaded)
            if (result.isUserAttackLoaded) binding.ivSuperAttackCharged.visibility = View.VISIBLE
            newTurn(result, 2, iaMove)
        }
    }

    private fun newTurn(result: TurnResultModel, userMove: Int, iaMove: Int) {
        if (result.iaIsDefeated) {
            combatViewModel.updateVictoriesCount(1)
            binding.ivRivalPokemon.animate().alpha(0f).duration = 1000
            createEndDialog(false)
        } else if (result.userIsDefeated) {
            binding.ivMyPokemon.animate().alpha(0f).duration = 1000
            createEndDialog(true)
            combatViewModel.updateVictoriesCount(0)
        } else {
            updateDataCombat(result, userMove, iaMove)
            updateHealthBar(result.userHP, result.iaHP)
        }
    }

    private fun updateDataCombat(result: TurnResultModel, userMove: Int, iaMove: Int) {
        var userMoveDescription = ""
        if (userMove == 0) userMoveDescription = getString(R.string.dodged)
        if (userMove == 1) userMoveDescription = getString(R.string.attacked)
        if(userMove == 2) userMoveDescription = "CARGO"
        val userPokemonName = binding.tvMyPokemonName.text

        var iaMoveDescription =
            if (iaMove == 1) getString(R.string.attacked) else getString(R.string.dodged)
        if(iaMove == 2) iaMoveDescription = "CARGO"
        val iaPokemonName = binding.tvRivalPokemonName.text

        val userMoveText = "$userPokemonName $userMoveDescription ${getString(R.string.rival_lives_left)} " +
                 "${result.iaHP.toInt()} ${getString(R.string.hp)}"
        binding.tvMyMove.text = userMoveText
        val iaMoveText = "$iaPokemonName $iaMoveDescription ${getString(R.string.user_lives_left)} " +
                "${result.userHP.toInt()} ${getString(R.string.hp)}"
        binding.tvRivalMove.text = iaMoveText
    }


    private fun createEndDialog(userDefeated: Boolean) {
        val dialogEndBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_combat_end, null)
        dialogEndBuilder.setView(dialogView)
        val dialogEnd = dialogEndBuilder.create()
        dialogEnd.setCanceledOnTouchOutside(false)
        val bindingDialog = DialogCombatEndBinding.bind(dialogView)

        val victoriesCount = combatViewModel.getVictoriesCount()
        // Default design if user wins
        // Design if user loses:
        if (userDefeated) {
            val redColor = ContextCompat.getColor(this, R.color.red)
            bindingDialog.tvCombatEndTitle.text = getString(R.string.defeat)
            bindingDialog.tvCombatEndTitle.setTextColor(redColor)
            bindingDialog.tvCombatEndVictories.setTextColor(redColor)
            bindingDialog.btEndDialog.text = getString(R.string.defeat)
            bindingDialog.btEndDialog.setBackgroundColor(redColor)
        }
        bindingDialog.tvCombatEndVictories.text = victoriesCount.toString()

        bindingDialog.btEndDialog.setOnClickListener {
            if (userDefeated) {
                Log.i("JDJD", "victoriesCountDataStore $victoriesCountDataStore")
                Log.i("JDJD", "victoriesCount $victoriesCount")
                if(victoriesCount > victoriesCountDataStore) combatViewModel.putVictoriesCountDataStore(victoriesCount)
                val intent = Intent(this, PokedexActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                combatViewModel.initViewModel(myPokemonId)
                initMyPokemonInfo()
                initRivalPokemonInfo()
                resetHP()
                val moveText = getString(R.string.turn_start) + " " + victoriesCount
                binding.tvMyMove.text = moveText
                binding.tvRivalMove.text = ""
                dialogEnd.dismiss()
            }
        }
        dialogEnd.show()
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
                binding.vMyHP3.visibility = View.INVISIBLE
                binding.vMyHP4.visibility = View.INVISIBLE
                binding.vMyHP5.visibility = View.INVISIBLE
            }

            3 -> {
                binding.vMyHP4.visibility = View.INVISIBLE
                binding.vMyHP5.visibility = View.INVISIBLE
            }

            4 -> {
                binding.vMyHP5.visibility = View.INVISIBLE
            }

            5 -> {} // al rectangles are visible
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
                binding.vRivalHP3.visibility = View.INVISIBLE
                binding.vRivalHP4.visibility = View.INVISIBLE
                binding.vRivalHP5.visibility = View.INVISIBLE
            }

            3 -> {
                binding.vRivalHP4.visibility = View.INVISIBLE
                binding.vRivalHP5.visibility = View.INVISIBLE
            }

            4 -> {
                binding.vRivalHP5.visibility = View.INVISIBLE
            }

            5 -> {} // al rectangles are visible
            else -> {
                binding.vRivalHP1.visibility = View.INVISIBLE
                binding.vRivalHP2.visibility = View.INVISIBLE
                binding.vRivalHP3.visibility = View.INVISIBLE
                binding.vRivalHP4.visibility = View.INVISIBLE
                binding.vRivalHP5.visibility = View.INVISIBLE

            }
        }
    }


    private fun resetHP() {

        binding.vMyHP1.visibility = View.VISIBLE
        binding.vMyHP2.visibility = View.VISIBLE
        binding.vMyHP3.visibility = View.VISIBLE
        binding.vMyHP4.visibility = View.VISIBLE
        binding.vMyHP5.visibility = View.VISIBLE

        binding.vRivalHP1.visibility = View.VISIBLE
        binding.vRivalHP2.visibility = View.VISIBLE
        binding.vRivalHP3.visibility = View.VISIBLE
        binding.vRivalHP4.visibility = View.VISIBLE
        binding.vRivalHP5.visibility = View.VISIBLE
    }

}
package com.jdccmobile.pokecombat.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.jdccmobile.pokecombat.databinding.ActivityMainBinding
import com.jdccmobile.pokecombat.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(): AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initListeners()
    }

    private fun initUI() {
        mainViewModel.victoriesCount.observe(this){ victories ->
            Log.i("JDJD", "victoriesCount $victories")
            if(victories != null) binding.tvScore.text = victories.toString()
            else binding.tvScore.text = "0"
        }
    }

    private fun initListeners() {
        binding.btStart.setOnClickListener { navigateToPokedex() }
    }

    private fun navigateToPokedex() {
        val intent = Intent(this, PokedexActivity::class.java)
        startActivity(intent)
    }
}
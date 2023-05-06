package com.jdccmobile.pokecombat.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jdccmobile.pokecombat.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CombatActivity @Inject constructor() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combat)

    }
}
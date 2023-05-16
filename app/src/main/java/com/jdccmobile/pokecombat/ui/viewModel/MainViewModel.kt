package com.jdccmobile.pokecombat.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.pokecombat.data.preferences.PreferencesKeys
import com.jdccmobile.pokecombat.domain.GetVictoriesCountUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getVictoriesCountUC: GetVictoriesCountUC
) : ViewModel() {

    val victoriesCount = MutableLiveData<Int?>()

    init {
        getVictoriesCountDataStore()
    }

    fun getVictoriesCountDataStore(){
        viewModelScope.launch {
            val victories = getVictoriesCountUC(PreferencesKeys.VICTORIES)
            Log.i("JDJD", "victories VM $victories")
            victoriesCount.postValue(victories)
        }
    }
}
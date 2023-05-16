package com.jdccmobile.pokecombat.di

import android.content.Context
import com.jdccmobile.pokecombat.data.pokeApi.PokemonApiClient
import com.jdccmobile.pokecombat.data.preferences.PreferencesDataStore
import com.jdccmobile.pokecombat.data.preferences.PreferencesImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // Preferences
    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext app: Context
    ): PreferencesDataStore = PreferencesImp(app)

    // Retrofit
    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePokemonApiClient(retrofit: Retrofit) : PokemonApiClient {
        return retrofit.create(PokemonApiClient::class.java)
    }



}
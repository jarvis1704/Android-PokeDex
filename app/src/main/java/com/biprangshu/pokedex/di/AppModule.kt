package com.biprangshu.pokedex.di

import com.biprangshu.pokedex.remote.PokeApi
import com.biprangshu.pokedex.respository.PokemonRepository
import com.biprangshu.pokedex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun ProvidePokemonRepository(
        api: PokeApi,
    )= PokemonRepository(api)

    @Singleton
    @Provides
    fun ProvidePokeApi(): PokeApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }
}
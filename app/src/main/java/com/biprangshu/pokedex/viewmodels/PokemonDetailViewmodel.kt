package com.biprangshu.pokedex.viewmodels

import androidx.lifecycle.ViewModel
import com.biprangshu.pokedex.remote.responses.Pokemon
import com.biprangshu.pokedex.respository.PokemonRepository
import com.biprangshu.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewmodel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

    suspend fun GetPokemonInfo(pokemonName: String): Resource<Pokemon>{
        return repository.getPokemonInfo(pokemonName)
    }

}
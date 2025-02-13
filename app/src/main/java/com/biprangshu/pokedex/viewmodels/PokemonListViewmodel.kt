package com.biprangshu.pokedex.viewmodels

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.biprangshu.pokedex.respository.PokemonRepository
import com.biprangshu.pokedex.util.Constants.PAGE_SIZE
import com.biprangshu.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewmodel @Inject constructor(
    private val respository: PokemonRepository
): ViewModel(){

    private var currPage = 0

    var pokemonList= mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError= mutableStateOf("")
    var isLoading= mutableStateOf(false)
    var isEndReached= mutableStateOf(false)

    private var cachedPokemonList= listOf<PokedexListEntry>()
    private var isSearchStarting=true
    var isSearching= mutableStateOf(false)

    init {
        LoadPokemonPaginated()
    }

    fun SearchPokemon(query: String){
        val listToSearch= if(isSearchStarting){
            pokemonList.value
        }else{
            cachedPokemonList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()){
                pokemonList.value=cachedPokemonList
                isSearching.value=false
                isSearchStarting=true
                return@launch
            }

            if (isSearchStarting) {
                cachedPokemonList = pokemonList.value
                isSearchStarting = false
            }


            val results = cachedPokemonList.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }

            pokemonList.value = results
            isSearching.value = true
        }
    }

    fun LoadPokemonPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = respository.getPokemonList(PAGE_SIZE, currPage * PAGE_SIZE)
            when(result) {
                is Resource.Success -> {
                    isEndReached.value = currPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if(entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                    }
                    currPage++

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> {

                }
            }
        }
    }
    fun CalcDominantColor(drawable: Drawable, onFinish: (Color)-> Unit){
        val bitmap= (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap).generate(){
            pelette->
            pelette?.dominantSwatch?.rgb?.let {
                colorvalue->
                onFinish(Color(colorvalue))
            }
        }
    }




}

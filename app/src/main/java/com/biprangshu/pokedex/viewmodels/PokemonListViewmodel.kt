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

    init {
        LoadPokemonPaginated()
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

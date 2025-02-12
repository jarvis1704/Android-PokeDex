package com.biprangshu.pokedex.viewmodels

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import com.biprangshu.pokedex.respository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewmodel @Inject constructor(
    private val respository: PokemonRepository
): ViewModel(){
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

package com.biprangshu.pokedex.pokemondetail

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.biprangshu.pokedex.remote.responses.Pokemon
import com.biprangshu.pokedex.util.Resource
import com.biprangshu.pokedex.viewmodels.PokemonDetailViewmodel

@Composable
fun PokemonDetailScreen(modifier: Modifier = Modifier, dominantColor: Color, navController: NavController, pokemonName: String, topPadding: Dp =20.dp, pokemonImageSize: Dp=200.dp, viewmodel: PokemonDetailViewmodel= hiltViewModel()) {
    val pokemonInfo= produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value=viewmodel.GetPokemonInfo(pokemonName)
    }


}
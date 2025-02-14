package com.biprangshu.pokedex.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.biprangshu.pokedex.remote.responses.Pokemon
import com.biprangshu.pokedex.ui.theme.RussoOne
import com.biprangshu.pokedex.ui.theme.TypeFighting
import com.biprangshu.pokedex.util.Resource
import com.biprangshu.pokedex.viewmodels.PokemonDetailViewmodel

@Composable
fun PokemonDetailScreen(modifier: Modifier = Modifier, dominantColor: Color, navController: NavController, pokemonName: String, topPadding: Dp =20.dp, pokemonImageSize: Dp = 300.dp, viewmodel: PokemonDetailViewmodel= hiltViewModel()) {
    val pokemonInfo= produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value=viewmodel.GetPokemonInfo(pokemonName)
    }.value

    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(color = dominantColor)
            .navigationBarsPadding()
    ) {
        PokemonDetailHeader(navController = navController)
        PokemonDetailWrapper(
            pokemonInfo = pokemonInfo,
            modifier = Modifier.fillMaxSize().padding(
                top= topPadding + pokemonImageSize / 2f,
                start = 16.dp,
                bottom = 16.dp,
                end = 16.dp
            ).shadow(10.dp, RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.surface).padding(16.dp),
            loadingModifier = Modifier.padding(
                top = topPadding + pokemonImageSize / 2f,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ).align(Alignment.Center)
            )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            if(pokemonInfo is Resource.Success){
                pokemonInfo?.data?.sprites?.let {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(it.frontDefault).crossfade(true).build(),
                        contentDescription = pokemonName,
                        modifier= Modifier.size(pokemonImageSize).padding(top = topPadding),

                    )
                }
            }
        }
    }

}


@Composable
fun PokemonDetailHeader(modifier: Modifier = Modifier, navController: NavController) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Back Arrow", tint = Color.White, modifier = Modifier
            .size(36.dp)
            .clickable { navController.popBackStack() })
    }
}

@Composable
fun PokemonDetailWrapper(modifier: Modifier = Modifier, pokemonInfo: Resource<Pokemon>, loadingModifier: Modifier= Modifier) {
    when(pokemonInfo){
        is Resource.Error -> {
            Text(
                text = pokemonInfo.message!!,
                color = TypeFighting,
                modifier = modifier,
                fontFamily = RussoOne,
                fontSize = 16.sp, fontWeight = FontWeight.SemiBold
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(modifier = loadingModifier)
        }

        is Resource.Success -> {

        }
    }
}
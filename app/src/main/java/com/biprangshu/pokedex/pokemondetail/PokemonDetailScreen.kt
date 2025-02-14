package com.biprangshu.pokedex.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.biprangshu.pokedex.R
import com.biprangshu.pokedex.remote.responses.Pokemon
import com.biprangshu.pokedex.remote.responses.Type
import com.biprangshu.pokedex.ui.theme.RussoOne
import com.biprangshu.pokedex.ui.theme.TypeFighting
import com.biprangshu.pokedex.util.Resource
import com.biprangshu.pokedex.util.parseTypeToColor
import com.biprangshu.pokedex.viewmodels.PokemonDetailViewmodel
import java.util.Locale
import kotlin.math.round

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
            PokemonDetailSection(
                pokemonInfo = pokemonInfo.data!!,
                modifier = modifier
                    .offset(y = (-20).dp)
            )
        }
    }
}

@Composable
fun PokemonDetailSection(
    pokemonInfo: Pokemon,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "#${pokemonInfo.id} ${pokemonInfo.name.capitalize(Locale.ROOT)}",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        PokemonTypeSection(types = pokemonInfo.types)
        PokemonDetailDataSection(
            pokemonWeight = pokemonInfo.weight,
            pokemonHeight = pokemonInfo.height
        )

    }
}

@Composable
fun PokemonTypeSection(types: List<Type>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        for(type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(35.dp)
            ) {
                Text(
                    text = type.type.name.capitalize(Locale.ROOT),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonWeight: Int,
    pokemonHeight: Int,
    sectionHeight: Dp = 80.dp
) {
    val pokemonWeightInKg = remember {
        round(pokemonWeight * 100f) / 1000f
    }
    val pokemonHeightInMeters = remember {
        round(pokemonHeight * 100f) / 1000f
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PokemonDetailDataItem(
            dataValue = pokemonWeightInKg,
            dataUnit = "kg",
            dataIcon = painterResource(id = R.drawable.ic_weight),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier
            .size(1.dp, sectionHeight)
            .background(Color.LightGray))
        PokemonDetailDataItem(
            dataValue = pokemonHeightInMeters,
            dataUnit = "m",
            dataIcon = painterResource(id = R.drawable.ic_height),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float,
    dataUnit: String,
    dataIcon: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(painter = dataIcon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$dataValue$dataUnit",
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
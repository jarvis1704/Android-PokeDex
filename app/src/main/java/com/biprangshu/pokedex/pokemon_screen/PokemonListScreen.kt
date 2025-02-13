package com.biprangshu.pokedex.pokemon_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.biprangshu.pokedex.R
import com.biprangshu.pokedex.ui.theme.RubricMono
import com.biprangshu.pokedex.ui.theme.RussoOne
import com.biprangshu.pokedex.ui.theme.TypeGrass
import com.biprangshu.pokedex.viewmodels.PokedexListEntry
import com.biprangshu.pokedex.viewmodels.PokemonListViewmodel


@Composable
fun PokemonListScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: PokemonListViewmodel= hiltViewModel()) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Row(
                modifier= Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Image(painter = painterResource(R.drawable.pokedex_logo), contentDescription = "Pokedex Logo")
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                }
            }
            Spacer(Modifier.height(8.dp))
            SearchBar(onSearch = {viewModel.SearchPokemon(it)})
            Spacer(Modifier.height(16.dp))
            PokemonList(navController = navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier, onSearch: (String) -> Unit = {}) {
    var text by remember {
        mutableStateOf("")
    }
    var active by remember { mutableStateOf(false) }

    SearchBar(
        query = text,
        onQueryChange = {
            text = it
            onSearch(it)
        },
        onSearch = { active = false },
        active = active,
        onActiveChange = { active = it },
        modifier = Modifier.fillMaxWidth(),
        enabled = true,
        placeholder = { Text("Search for Pokemon") },
        leadingIcon = {
            if (active) {
                IconButton(onClick = { active = false }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            } else {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        },
        trailingIcon = {
            if(text.isNotEmpty()) {
                IconButton(onClick = { text = "" }) {  // Fixed onClick parameter
                    Icon(Icons.Filled.Close, contentDescription = "Clear")
                }
            }
        },
        shape = MaterialTheme.shapes.medium,
        colors = SearchBarDefaults.colors(),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        windowInsets = SearchBarDefaults.windowInsets,
    ) {
        Text("Bulbasaur")
    }
}

@Composable
fun PokemonList(modifier: Modifier = Modifier, navController: NavController, viewModel: PokemonListViewmodel= hiltViewModel()) {
    val pokemonList by remember {
        viewModel.pokemonList
    }

    val isEndReached by remember { viewModel.isEndReached }
    val isLoading by remember { viewModel.isLoading }
    val loadError by remember { viewModel.loadError }

    val isSearching by remember { viewModel.isSearching }

    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        val itemCount = if(pokemonList.size%2==0) pokemonList.size/2 else pokemonList.size/2+1

        items(itemCount){
            if(it>=itemCount-1 && !isEndReached && !isLoading && !isSearching){
                viewModel.LoadPokemonPaginated()
            }
            PokeDexRow(rowIndex = it, navController = navController, entries = pokemonList)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        if(isLoading){
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }else if(loadError.isNotEmpty()){
            RetrySection(error = loadError) {
                viewModel.LoadPokemonPaginated()
            }
        }
    }

}

@Composable
fun PokeDexEntry(modifier: Modifier = Modifier, entry: PokedexListEntry, navController: NavController, viewModel: PokemonListViewmodel= hiltViewModel()) {
    val defaultDominantColor= MaterialTheme.colorScheme.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(8.dp))
            .clip(
                RoundedCornerShape(8.dp)
            )
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate("pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}")
            },

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(entry.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = entry.pokemonName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    onSuccess = { state ->
                        val drawable = state.result.drawable
                        viewModel.CalcDominantColor(drawable) { dominantColor = it }
                        isLoading=false
                    },
                    onLoading = {isLoading=true}
                )

                if (isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 2.dp
                    )
                }

            }
            Spacer(Modifier.height(4.dp))
            Text(text = entry.pokemonName, fontFamily = RubricMono, fontWeight = FontWeight.Medium, fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun PokeDexRow(
    modifier: Modifier = Modifier,
    rowIndex: Int,
    entries: List<PokedexListEntry>,
    navController: NavController
) {
    val indices = remember(rowIndex) {
        val firstIndex = rowIndex * 2
        val secondIndex = firstIndex + 1
        firstIndex to secondIndex
    }


    val secondEntry = remember(indices.second, entries) {
        entries.getOrNull(indices.second)
    }

    Column(modifier = modifier) {
        Row {

            PokeDexEntry(
                entry = entries[indices.first],
                navController = navController,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(16.dp))

            if (secondEntry != null) {
                PokeDexEntry(
                    entry = secondEntry,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(Modifier.weight(1f))
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun RetrySection(modifier: Modifier = Modifier, error: String, onRetry: ()-> Unit) {
    Column {
        Text(text = error, fontSize = 16.sp, fontFamily = RussoOne)
        Spacer(Modifier.height(8.dp))
        Button(onClick = { onRetry() }, modifier = Modifier.align(Alignment.CenterHorizontally), colors = ButtonDefaults.buttonColors(
            TypeGrass
        )) {
            Text("Retry", fontSize = 16.sp, fontFamily = RussoOne, fontWeight = FontWeight.Bold, color = Color.DarkGray)
        }
    }
}
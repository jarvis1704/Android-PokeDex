package com.biprangshu.pokedex.pokemon_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.text.font.FontStyle
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
fun PokemonListScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: PokemonListViewmodel = hiltViewModel()) {
    val listState = rememberLazyListState()
    val scrolledUp by remember {
        derivedStateOf { listState.firstVisibleItemIndex <= 0 }
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            DynamicHeader(navController = navController, scrolledUp = scrolledUp, onSearch = { viewModel.SearchPokemon(it) })

            PokemonList(navController = navController, listState = listState)
        }
    }
}

@Composable
fun DynamicHeader(navController: NavController, scrolledUp: Boolean, onSearch: (String) -> Unit) {
    val headerHeightExpanded = 150.dp
    val headerHeightCollapsed = 56.dp // Standard TopAppBar height
    val headerHeight by animateDpAsState(
        targetValue = if (scrolledUp) headerHeightExpanded else headerHeightCollapsed,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .background(MaterialTheme.colorScheme.background) // Ensure header background is consistent
    ) {
        AnimatedVisibility(
            visible = scrolledUp,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ExpandedHeaderContent(navController = navController, onSearch = onSearch)
        }
        AnimatedVisibility(
            visible = !scrolledUp,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CollapsedHeaderContent()
        }
    }
}

@Composable
fun ExpandedHeaderContent(navController: NavController, onSearch: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.pokedex_logo),
                contentDescription = "Pokedex Logo",
                modifier = Modifier.height(40.dp) // Adjust logo height as needed
            )
            IconButton(onClick = { navController.navigate(route = "pokemon_settings_screen") }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            }
        }
        SearchBar(onSearch = onSearch)
    }
}

@Composable
fun CollapsedHeaderContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center // Align text to start for collapsed header
    ) {
        Text(
            text = "Pokedex",
            fontFamily = RubricMono,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
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
                IconButton(onClick = {
                    active = false
                    onSearch("")
                    text=""
                }
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            } else {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        text = ""
                        onSearch("")
                    }
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "Clear")
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = SearchBarDefaults.colors(),
        tonalElevation = 16.dp,
        shadowElevation = 16.dp,
        windowInsets = SearchBarDefaults.windowInsets,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "Popular Searches",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Popular Pokemon suggestions
            val popularPokemon = listOf(
                "Pikachu" to "Electric type Pokemon",
                "Charizard" to "Fire/Flying type Pokemon",
                "Mewtwo" to "Legendary Psychic Pokemon",
                "Bulbasaur" to "Grass/Poison type Pokemon",
                "Gyarados" to "Water/Flying type Pokemon"
            )

            popularPokemon.forEach { (name, description) ->
                ListItem(
                    headlineContent = { Text(name) },
                    supportingContent = { Text(description) },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier = Modifier.clickable {
                        text = name
                        onSearch(name)
                        active = false
                    }
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                "Search by Type",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Pokemon type suggestions
            val types = listOf("Fire", "Water", "Grass", "Electric", "Psychic")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(types) { type ->
                    SuggestionChip(
                        onClick = {
                            text = type
                            onSearch(type)
                            active = false
                        },
                        label = { Text(text = type) }
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PokemonListViewmodel = hiltViewModel(),
    listState: androidx.compose.foundation.lazy.LazyListState = rememberLazyListState()
) {
    val pokemonList by remember { viewModel.pokemonList }
    val isEndReached by remember { viewModel.isEndReached }
    val isLoading by remember { viewModel.isLoading }
    val loadError by remember { viewModel.loadError }
    val isSearching by remember { viewModel.isSearching }

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        state = listState,
        modifier = Modifier.fillMaxSize() // Ensure LazyColumn fills available space
    ) {
        val itemCount = pokemonList.size
        items(count = (itemCount + 1) / 2) { rowIndex ->
            if (rowIndex >= ((itemCount - 1) / 2) && !isEndReached && !isLoading && !isSearching) {
                viewModel.LoadPokemonPaginated()
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (rowIndex * 2 < itemCount) {
                        PokeDexEntry(
                            entry = pokemonList[rowIndex * 2],
                            navController = navController
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(modifier = Modifier.weight(1f)) {
                    if (rowIndex * 2 + 1 < itemCount) {
                        PokeDexEntry(
                            entry = pokemonList[rowIndex * 2 + 1],
                            navController = navController
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            if (isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else if (loadError.isNotEmpty()) {
                RetrySection(error = loadError) {
                    viewModel.LoadPokemonPaginated()
                }
            }
        }
    }
}

@Composable
fun PokeDexEntry(
    modifier: Modifier = Modifier,
    entry: PokedexListEntry,
    navController: NavController,
    viewModel: PokemonListViewmodel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(16.dp))
            .aspectRatio(0.7f)
            .fillMaxWidth()
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
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)  // Reduced padding for better space utilization
        ) {
            // Number of the Pokemon
            Text(
                text = "#${entry.number}",
                fontFamily = RubricMono,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Box(
                modifier = Modifier
                    .size(100.dp)  // Adjusted size from 120.dp to 100.dp
                    .align(Alignment.CenterHorizontally)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(entry.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = entry.pokemonName,
                    contentScale = ContentScale.Fit,  // Changed from Crop to Fit
                    modifier = Modifier.fillMaxSize(),
                    onSuccess = { state ->
                        val drawable = state.result.drawable
                        viewModel.CalcDominantColor(drawable) { dominantColor = it }
                        isLoading = false
                    },
                    onLoading = { isLoading = true }
                )

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 2.dp
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            // Pokemon Name
            Text(
                text = entry.pokemonName,
                fontFamily = RubricMono,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )
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
    Column {
        Row {
            // First Pokemon in the row
            if (rowIndex * 2 < entries.size) {
                PokeDexEntry(
                    entry = entries[rowIndex * 2],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Second Pokemon in the row
            if (rowIndex * 2 + 1 < entries.size) {
                PokeDexEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RetrySection(modifier: Modifier = Modifier, error: String, onRetry: () -> Unit) {
    Column {
        Text(text = error, fontSize = 16.sp, fontFamily = RussoOne)
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                TypeGrass
            )
        ) {
            Text(
                "Retry",
                fontSize = 16.sp,
                fontFamily = RussoOne,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        }
    }
}
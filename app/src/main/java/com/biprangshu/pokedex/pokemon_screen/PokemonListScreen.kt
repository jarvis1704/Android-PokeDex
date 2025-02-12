package com.biprangshu.pokedex.pokemon_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.biprangshu.pokedex.R

@Composable
fun PokemonListScreen(modifier: Modifier = Modifier, navController: NavController) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().statusBarsPadding()
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
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier, onSearch: (String)-> Unit= {}) {
    var text by remember {
        mutableStateOf("")
    }
    var active by remember { mutableStateOf(false) }

    androidx.compose.material3.SearchBar(
        query = text,
        onQueryChange = {
            text=it
            onSearch(it)
        },
        onSearch = { active=false },
        active = active,
        onActiveChange = {active=it},
        modifier = TODO(),
        enabled = TODO(),
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
            if(text.isNotEmpty()){
                IconButton({text = ""}) {
                    Icon(Icons.Filled.Close, contentDescription = "Clear")
                }
            }
        },
        shape = TODO(),
        colors = TODO(),
        tonalElevation = TODO(),
        shadowElevation = TODO(),
        windowInsets = TODO(),
        interactionSource = TODO()
    ) {

    }
}

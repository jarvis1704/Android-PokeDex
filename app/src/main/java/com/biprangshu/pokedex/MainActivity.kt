package com.biprangshu.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toLowerCase
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.biprangshu.pokedex.pokemon_screen.PokemonListScreen
import com.biprangshu.pokedex.pokemondetail.PokemonDetailScreen
import com.biprangshu.pokedex.ui.theme.PokeDexTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.biprangshu.pokedex.settings.SettingsScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkMode= isSystemInDarkTheme()
            var darkTheme by remember {
                mutableStateOf(darkMode)
            }
            PokeDexTheme(
                darkTheme= darkTheme
            ){
                val navController= rememberNavController()
                NavHost(navController = navController, startDestination = "pokemon_list_screen"){
                    composable("pokemon_list_screen"){
                        PokemonListScreen(navController = navController)
                    }

                    composable("pokemon_detail_screen/{dominantColor}/{pokemonName}", arguments = listOf(
                        navArgument("dominantColor"){
                            type= NavType.IntType
                        },
                        navArgument("pokemonName"){
                            type= NavType.StringType
                        }

                    )
                    ){
                        val dominantColor = remember {
                            val color= it.arguments?.getInt("dominantColor")
                            color?.let{
                                Color(it)
                            }?: Color.White
                        }
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }
                        PokemonDetailScreen(
                            dominantColor = dominantColor,
                            pokemonName = pokemonName?.lowercase(Locale.ROOT) ?: "",
                            navController = navController
                        )
                    }
                    composable(route = "pokemon_settings_screen"){
                        SettingsScreen(darkTheme, navController = navController) {
                            newValue->
                            darkTheme=newValue
                        }
                    }
                }
            }
        }
    }
}


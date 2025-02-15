package com.biprangshu.pokedex.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.biprangshu.pokedex.ui.theme.PokeDexTheme
import com.biprangshu.pokedex.ui.theme.RubricMono
import com.biprangshu.pokedex.ui.theme.RussoOne

@Composable
fun SettingsScreen(darkTheme: Boolean,navController: NavController, onThemeChanged: (Boolean) -> Unit) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp)
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Back Arrow", tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(32.dp).clickable { navController.popBackStack() })
                Spacer(Modifier.width(16.dp))
                Text("Settings", fontFamily = RubricMono, fontSize = 24.sp, color = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(Modifier.height(16.dp))
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text("Show in Dark Mode", fontFamily = RubricMono, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.onSurface)
                        Text("Toggle to run the app in dark or light mode", fontFamily = RussoOne, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp)
                    }
                    Spacer(Modifier.width(16.dp))
                    Switch(
                        checked = darkTheme,
                        onCheckedChange = onThemeChanged
                    )
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().clickable {

                        }
                    ) {
                        Text("About the App", fontFamily = RubricMono, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.onSurface)
                        Text("Get to know the Nitty Grity of the app", fontFamily = RussoOne, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize().padding(vertical = 16.dp).statusBarsPadding(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Made with ", fontFamily = RubricMono, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "icon", modifier = Modifier.size(18.dp))
                    Text(" by Biprangshu Das ", fontFamily = RubricMono, fontWeight = FontWeight.Normal, color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)

                }
                }
            }
        }
    }

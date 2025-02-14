package com.biprangshu.pokedex.util

import com.biprangshu.pokedex.remote.responses.Stat
import com.biprangshu.pokedex.remote.responses.Type
import com.biprangshu.pokedex.ui.theme.AtkColor
import com.biprangshu.pokedex.ui.theme.DefColor
import com.biprangshu.pokedex.ui.theme.HPColor
import com.biprangshu.pokedex.ui.theme.SpAtkColor
import com.biprangshu.pokedex.ui.theme.SpDefColor
import com.biprangshu.pokedex.ui.theme.SpdColor
import com.biprangshu.pokedex.ui.theme.TypeBug
import com.biprangshu.pokedex.ui.theme.TypeDark
import com.biprangshu.pokedex.ui.theme.TypeDragon
import com.biprangshu.pokedex.ui.theme.TypeElectric
import com.biprangshu.pokedex.ui.theme.TypeFairy
import com.biprangshu.pokedex.ui.theme.TypeFighting
import com.biprangshu.pokedex.ui.theme.TypeFire
import com.biprangshu.pokedex.ui.theme.TypeFlying
import com.biprangshu.pokedex.ui.theme.TypeGhost
import com.biprangshu.pokedex.ui.theme.TypeGrass
import com.biprangshu.pokedex.ui.theme.TypeGround
import com.biprangshu.pokedex.ui.theme.TypeIce
import com.biprangshu.pokedex.ui.theme.TypeNormal
import com.biprangshu.pokedex.ui.theme.TypePoison
import com.biprangshu.pokedex.ui.theme.TypePsychic
import com.biprangshu.pokedex.ui.theme.TypeRock
import com.biprangshu.pokedex.ui.theme.TypeSteel
import com.biprangshu.pokedex.ui.theme.TypeWater
import java.util.Locale

fun parseTypeToColor(type: Type): androidx.compose.ui.graphics.Color {
    return when(type.type.name.lowercase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> androidx.compose.ui.graphics.Color.Black
    }
}

fun parseStatToColor(stat: Stat): androidx.compose.ui.graphics.Color {
    return when(stat.stat.name.lowercase(Locale.getDefault())) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> androidx.compose.ui.graphics.Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when(stat.stat.name.lowercase(Locale.getDefault())) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}
package com.biprangshu.pokedex.remote.responses


import com.biprangshu.pokedex.remote.responses.DreamWorld
import com.biprangshu.pokedex.remote.responses.OfficialArtwork
import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld,
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork
)
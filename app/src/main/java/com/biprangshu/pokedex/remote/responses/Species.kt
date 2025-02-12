package com.biprangshu.pokedex.remote.responses


import com.google.gson.annotations.SerializedName

data class Species(
    val name: String,
    val url: String
)
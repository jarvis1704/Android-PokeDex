package com.biprangshu.pokedex.remote.responses


import com.google.gson.annotations.SerializedName

data class Version(
    val name: String,
    val url: String
)
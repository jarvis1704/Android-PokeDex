package com.biprangshu.pokedex.remote.responses


data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)
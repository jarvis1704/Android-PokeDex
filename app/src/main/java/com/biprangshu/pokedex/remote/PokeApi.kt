package com.biprangshu.pokedex.remote

import com.biprangshu.pokedex.remote.responses.Pokemon
import com.biprangshu.pokedex.remote.responses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun GetPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun GetPokemonInfo(
        @Path("name") name: String
    ): Pokemon
}
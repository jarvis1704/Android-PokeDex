package com.biprangshu.pokedex.remote.responses


import com.biprangshu.pokedex.remote.responses.GenerationI
import com.biprangshu.pokedex.remote.responses.GenerationIi
import com.biprangshu.pokedex.remote.responses.GenerationIii
import com.biprangshu.pokedex.remote.responses.GenerationIv
import com.biprangshu.pokedex.remote.responses.GenerationV
import com.biprangshu.pokedex.remote.responses.GenerationVi
import com.biprangshu.pokedex.remote.responses.GenerationVii
import com.biprangshu.pokedex.remote.responses.GenerationViii
import com.google.gson.annotations.SerializedName

data class Versions(
    @SerializedName("generation-i")
    val generationI: GenerationI,
    @SerializedName("generation-ii")
    val generationIi: GenerationIi,
    @SerializedName("generation-iii")
    val generationIii: GenerationIii,
    @SerializedName("generation-iv")
    val generationIv: GenerationIv,
    @SerializedName("generation-v")
    val generationV: GenerationV,
    @SerializedName("generation-vi")
    val generationVi: GenerationVi,
    @SerializedName("generation-vii")
    val generationVii: GenerationVii,
    @SerializedName("generation-viii")
    val generationViii: GenerationViii
)
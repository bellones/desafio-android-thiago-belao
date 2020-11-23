package com.teste.marvelkotlin.model

import java.util.*

data class Revista (

    val id : Int,
    val digitalId : Int,
    val title : String,
    val description : String,
    val thumbnail: Foto,
    val prices : List<Preco>

)

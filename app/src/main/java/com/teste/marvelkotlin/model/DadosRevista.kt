package com.teste.marvelkotlin.model

data class DadosRevista (

    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Revista>
)


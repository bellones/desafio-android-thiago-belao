package com.teste.marvelkotlin.model

data class Dados (
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Personagem>
)

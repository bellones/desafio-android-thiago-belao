package com.teste.marvelkotlin.model

data class Personagem (
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Foto
)
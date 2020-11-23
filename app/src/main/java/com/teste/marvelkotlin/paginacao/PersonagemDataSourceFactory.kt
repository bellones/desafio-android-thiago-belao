package com.teste.marvelkotlin.paginacao

import com.teste.marvelkotlin.interfaces.Api
import io.reactivex.disposables.CompositeDisposable
import androidx.paging.DataSource
import com.teste.marvelkotlin.model.Personagem

class PersonagemDataSourceFactory (
    private val compositeDisposable: CompositeDisposable,
    private val marvelApi: Api) : DataSource.Factory<Int, Personagem>() {

    override fun create(): DataSource<Int, Personagem> {
        return ParsonageDataSource(marvelApi, compositeDisposable)
    }
}
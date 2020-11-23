package com.teste.marvelkotlin.paginacao

import androidx.paging.DataSource
import com.teste.marvelkotlin.interfaces.RevistaApi
import com.teste.marvelkotlin.model.Revista
import io.reactivex.disposables.CompositeDisposable

class RevistaDataSourceFactory (
    private val compositeDisposable: CompositeDisposable,
    private val marvelApi: RevistaApi,
    private val id : Int

) : DataSource.Factory<Int, Revista>() {

    override fun create(): DataSource<Int, Revista> {
        return RevistaDataSource(marvelApi, compositeDisposable, id)
    }
}
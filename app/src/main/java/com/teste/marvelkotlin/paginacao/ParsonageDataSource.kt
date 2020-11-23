package com.teste.marvelkotlin.paginacao

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.teste.marvelkotlin.interfaces.Api
import com.teste.marvelkotlin.model.Personagem
import io.reactivex.disposables.CompositeDisposable


class ParsonageDataSource(
    private val marvelApi: Api,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Personagem>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Personagem>) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Personagem>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Personagem>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun createObservable(requestedPage: Int,
                                 adjacentPage: Int,
                                 requestedLoadSize: Int,
                                 initialCallback: LoadInitialCallback<Int, Personagem>?,
                                 callback: LoadCallback<Int, Personagem>?) {
        compositeDisposable.add(
            marvelApi.ListaPersonagens(requestedPage * requestedLoadSize)
                .subscribe(
                    { response ->
                        Log.e("", response.data.results.toString())
                        initialCallback?.onResult(response.data.results, null, adjacentPage)
                        callback?.onResult(response.data.results, adjacentPage)
                    },
                    { t ->
                        Log.d("NGVL", "Error loading page: $requestedPage", t)
                    }
                )
        )
    }
}
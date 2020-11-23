package com.teste.marvelkotlin.paginacao

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.teste.marvelkotlin.interfaces.RevistaApi
import com.teste.marvelkotlin.model.Preco
import com.teste.marvelkotlin.model.Revista
import io.reactivex.disposables.CompositeDisposable

class RevistaDataSource(
    private val marvelApi : RevistaApi,
    private val compositeDisposable: CompositeDisposable,
    private val id : Int


) : PageKeyedDataSource<Int, Revista>(){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Revista>) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, Revista>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Revista>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun createObservable(requestedPage: Int,
                                 adjacentPage: Int,
                                 requestedLoadSize: Int,
                                 initialCallback: LoadInitialCallback<Int, Revista>?,
                                 callback: LoadCallback<Int, Revista>?) {

        var listPrices =  ArrayList<Preco>()
        var parsed =  ArrayList<Revista>()
        compositeDisposable.add(
            marvelApi.ListaRevista(id)
                .subscribe(
                    { response ->

                        for (it in 0 until response.data.results?.size!!){
                            for (x in 0 until response.data.results[it].prices.size){
                                listPrices.add(response.data.results[it].prices[x])

                            }
                        }
                        val listagem = listPrices.maxBy { a -> a.price }
                        val listaTotal = response.data.results
                        val lista = listaTotal.maxBy { t -> t.prices.indexOf(listagem)}
                        if (lista != null) {
                            parsed.add(lista)
                        }
                        initialCallback?.onResult(parsed, null, adjacentPage)
                        callback?.onResult(parsed, adjacentPage)
                    },
                    { t ->
                        Log.d("NGVL", "Error loading page: $requestedPage", t)
                    }
                )
        )
    }
}
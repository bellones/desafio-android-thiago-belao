package com.teste.marvelkotlin.view.home


import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.teste.marvelkotlin.interfaces.Api
import com.teste.marvelkotlin.model.Personagem
import com.teste.marvelkotlin.paginacao.PersonagemDataSourceFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    var listaPersonagem: Observable<PagedList<Personagem>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 20

    private val sourceFactory: PersonagemDataSourceFactory

    init {
        sourceFactory = PersonagemDataSourceFactory(compositeDisposable, Api.getService())

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        listaPersonagem = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
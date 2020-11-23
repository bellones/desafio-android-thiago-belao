package com.teste.marvelkotlin.view.home.revista

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.bumptech.glide.Glide.init
import com.teste.marvelkotlin.R
import com.teste.marvelkotlin.extensoes.load
import com.teste.marvelkotlin.interfaces.RevistaApi
import com.teste.marvelkotlin.model.Revista
import com.teste.marvelkotlin.paginacao.RevistaDataSourceFactory
import com.teste.marvelkotlin.view.home.MainViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_revista.*
import kotlinx.android.synthetic.main.content_revista.*
import kotlin.Int as Int
import kotlin.collections.maxBy as maxBy

class RevistaActivity : AppCompatActivity() {



      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revista)
        setSupportActionBar(toolbar)
        val id = intent.getStringExtra("id")!!.toInt()
        var listaRevista: Observable<PagedList<Revista>>
        val compositeDisposable = CompositeDisposable()
        val pageSize = 20
        val sourceFactory: RevistaDataSourceFactory
        sourceFactory = RevistaDataSourceFactory(compositeDisposable, RevistaApi.getService(), id)

            val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(false)
                .build()

            listaRevista = RxPagedListBuilder(sourceFactory, config)
                .setFetchScheduler(Schedulers.io())
                .buildObservable()
                .cache()

            val disposable = listaRevista
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                        val revista  = list[0]
                        nome.setText(revista?.title)
                        descricao.setText(revista?.description)
                        foto.load("${revista?.thumbnail?.path}/standard_large.${revista?.thumbnail?.extension}")
                        preco.setText("$"+ revista?.prices?.maxBy { it -> it.price }?.price)

                    }, { e ->
                        Log.e("NGVL", "Error", e)
                    })


        }

      }













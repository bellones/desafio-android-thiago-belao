package com.teste.marvelkotlin.interfaces

import com.google.gson.GsonBuilder
import com.teste.marvelkotlin.extensoes.md5
import com.teste.marvelkotlin.model.PRIVATE_KEY
import com.teste.marvelkotlin.model.PUBLIC_KEY
import com.teste.marvelkotlin.model.RetornoRevista
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface RevistaApi {
    @GET("characters/{characterId}/comics")
   fun ListaRevista(@Path ("characterId") characterId : Int? = 0) : Observable<RetornoRevista>

    companion object {
        fun getService(): RevistaApi {


            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", PUBLIC_KEY)
                    .addQueryParameter("ts", ts)
                    .addQueryParameter("hash", "$ts$PRIVATE_KEY$PUBLIC_KEY".md5())
                    .build()

                chain.proceed(original.newBuilder().url(url).build())
            }

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com/v1/public/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
            return retrofit.create<RevistaApi>(RevistaApi::class.java)
        }
    }


}

package com.teste.marvelkotlin.view.home.detalhe

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.teste.marvelkotlin.R
import com.teste.marvelkotlin.extensoes.load
import com.teste.marvelkotlin.model.Revista
import com.teste.marvelkotlin.view.home.revista.RevistaActivity

import kotlinx.android.synthetic.main.activity_detalhes.*
import kotlinx.android.synthetic.main.content_detalhes.*

class Detalhes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)
        setSupportActionBar(toolbar)
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("nome")
        val desc = intent.getStringExtra("descricao")
        val ft = intent.getStringExtra("foto")
        val ext = intent.getStringExtra("ext")

        personagemId.setText(id)
        nome.setText(name)
        descricao.setText(desc)
        foto.load("${ft}/standard_large.${ext}")

        btRevista.setOnClickListener{
            val intent = Intent(this, RevistaActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

}

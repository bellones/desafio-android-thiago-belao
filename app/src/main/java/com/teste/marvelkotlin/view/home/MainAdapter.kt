package com.teste.marvelkotlin.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.teste.marvelkotlin.R
import com.teste.marvelkotlin.extensoes.load
import com.teste.marvelkotlin.model.Personagem
import kotlinx.android.synthetic.main.item_personagem.view.*

class MainAdapter (val onPersonagemClick: OnPersonagemClick) : PagedListAdapter<Personagem, MainAdapter.VH>(
    characterDiff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_personagem, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val personagem = getItem(position)
        if (personagem != null) {
            holder.initialize(personagem,onPersonagemClick)
        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgThumbnail = itemView.foto
        val txtName = itemView.nome
        val id = itemView.personagemId
        val descricao = itemView.descricao


        fun initialize(item: Personagem, action: OnPersonagemClick){

            imgThumbnail.load("${item?.thumbnail?.path}/standard_medium.${item?.thumbnail?.extension}")
            id.text = item.id.toString()
            txtName.text =  item.name
            descricao.text = item.description

            itemView.setOnClickListener{
                action.onIntemClick(item, adapterPosition)
            }
        }
    }

    interface OnPersonagemClick {

        fun onIntemClick(item : Personagem, position: Int){

        }
    }

    companion object {
        val characterDiff = object: DiffUtil.ItemCallback<Personagem>() {
            override fun areItemsTheSame(velho: Personagem, novo: Personagem): Boolean {
                return velho.id == novo.id

            }

            override fun areContentsTheSame(velho: Personagem, novo: Personagem): Boolean {
                return velho  == novo
            }

        }
    }


}
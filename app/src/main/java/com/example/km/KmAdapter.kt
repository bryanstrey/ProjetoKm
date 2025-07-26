package com.example.km

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KmAdapter(private val lista: List<KmRegistro>) :
    RecyclerView.Adapter<KmAdapter.KmViewHolder>() {

    class KmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtKm: TextView = itemView.findViewById(R.id.txtKm)
        val txtDataHora: TextView = itemView.findViewById(R.id.txtDataHora)
        val txtQuantidade: TextView = itemView.findViewById(R.id.txtQuantidade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_km_registro, parent, false)
        return KmViewHolder(view)
    }

    override fun onBindViewHolder(holder: KmViewHolder, position: Int) {
        val item = lista[position]
        holder.txtKm.text = "KM: ${item.km}"
        holder.txtDataHora.text = "Data: ${item.dataHora}"
        holder.txtQuantidade.text = "Qtd: ${item.quantidade}"
    }

    override fun getItemCount(): Int = lista.size
}

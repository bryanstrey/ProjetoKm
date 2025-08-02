package com.example.km

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KmAdapter(private var items: List<KmListItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_TOTAL_KM = 0
        private const val VIEW_TYPE_HEADER = 1
        private const val VIEW_TYPE_HEADER_NOME = 2
        private const val VIEW_TYPE_ITEM = 3
    }

    fun updateData(newItems: List<KmListItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is TotalKmItem -> VIEW_TYPE_TOTAL_KM
            is DayHeader -> VIEW_TYPE_HEADER
            is DayHeaderNome -> VIEW_TYPE_HEADER_NOME
            is KmItem -> VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TOTAL_KM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_total_km, parent, false)
                TotalKmViewHolder(view)
            }
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_day_header, parent, false)
                DayHeaderViewHolder(view)
            }
            VIEW_TYPE_HEADER_NOME -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_day_header_nome, parent, false)
                DayHeaderNomeViewHolder(view)
            }
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_km_registro, parent, false)
                KmViewHolder(view)
            }
            else -> throw IllegalArgumentException("Tipo de view desconhecido")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is TotalKmItem -> (holder as TotalKmViewHolder).bind(item)
            is DayHeader -> (holder as DayHeaderViewHolder).bind(item)
            is DayHeaderNome -> (holder as DayHeaderNomeViewHolder).bind(item)
            is KmItem -> (holder as KmViewHolder).bind(item)
        }
    }

    class TotalKmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTotalKm: TextView = itemView.findViewById(R.id.tvTotalKm)
        fun bind(item: TotalKmItem) {
            tvTotalKm.text = "Total KM: ${item.totalKm}"
        }
    }

    class DayHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtDia: TextView = itemView.findViewById(R.id.txtDia)
        fun bind(header: DayHeader) {
            txtDia.text = "Dia ${header.dia}"
        }
    }

    class DayHeaderNomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtNomeDia: TextView = itemView.findViewById(R.id.txtNomeDia)
        fun bind(header: DayHeaderNome) {
            txtNomeDia.text = header.nomeDia
        }
    }

    class KmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtKm: TextView = itemView.findViewById(R.id.txtKm)
        private val txtDataHora: TextView = itemView.findViewById(R.id.txtDataHora)
        private val txtLocal: TextView = itemView.findViewById(R.id.txtLocal)

        fun bind(item: KmItem) {
            val registro = item.registro
            txtKm.text = "KM: ${registro.km}"
            txtDataHora.text = "Data: ${registro.dataHora}"
            txtLocal.text = "De: ${registro.localSaida} → Para: ${registro.localChegada}"
        }
    }
}

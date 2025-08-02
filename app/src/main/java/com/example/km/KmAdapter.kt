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
        private const val VIEW_TYPE_ITEM = 2
    }

    fun updateData(newItems: List<KmListItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is TotalKmItem -> VIEW_TYPE_TOTAL_KM
            is DayHeader -> VIEW_TYPE_HEADER
            is KmItem -> VIEW_TYPE_ITEM
            // else não precisa porque é sealed interface e todos os casos cobertos
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
            is KmItem -> (holder as KmViewHolder).bind(item)
        }
    }

    // ViewHolder para o card total KM
    class TotalKmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTotalKm: TextView = itemView.findViewById(R.id.tvTotalKm)
        fun bind(item: TotalKmItem) {
            tvTotalKm.text = "Total KM: ${item.totalKm}"
        }
    }

    // ViewHolder para cabeçalho (dia)
    class DayHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtDia: TextView = itemView.findViewById(R.id.txtDia)
        fun bind(header: DayHeader) {
            txtDia.text = "Dia ${header.dia}"
        }
    }

    // ViewHolder para registro
    class KmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtKm: TextView = itemView.findViewById(R.id.txtKm)
        private val txtDataHora: TextView = itemView.findViewById(R.id.txtDataHora)
        private val txtQuantidade: TextView = itemView.findViewById(R.id.txtLocal)

        private val txtLocal: TextView = itemView.findViewById(R.id.txtLocal)

        fun bind(item: KmItem) {
            val registro = item.registro
            txtKm.text = "KM: ${registro.km}"
            txtDataHora.text = "Data: ${registro.dataHora}"
            txtLocal.text = "De: ${registro.localSaida} → Para: ${registro.localChegada}"
        }

    }
}

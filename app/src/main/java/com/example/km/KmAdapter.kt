package com.example.km

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KmAdapter(private var items: List<KmListItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    fun updateData(newItems: List<KmListItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DayHeader -> VIEW_TYPE_HEADER
            is KmItem -> VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_day_header, parent, false)
            DayHeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_km_registro, parent, false)
            KmViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is DayHeader -> (holder as DayHeaderViewHolder).bind(item)
            is KmItem -> (holder as KmViewHolder).bind(item)
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
        private val txtQuantidade: TextView = itemView.findViewById(R.id.txtQuantidade)

        fun bind(item: KmItem) {
            val registro = item.registro
            txtKm.text = "KM: ${registro.km}"
            txtDataHora.text = "Data: ${registro.dataHora}"
            txtQuantidade.text = "Qtd: ${registro.quantidade}"
        }
    }
}

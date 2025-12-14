package com.notero.superapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.notero.superapp.R
import com.notero.superapp.network.ApiService

class ListaOverviewAdapter(
    private val items: MutableList<ApiService.ListaDto>,
    private val onClick: (ApiService.ListaDto) -> Unit
) : RecyclerView.Adapter<ListaOverviewAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    fun update(newItems: List<ApiService.ListaDto>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val text1: TextView = itemView.findViewById(android.R.id.text1)
        private val text2: TextView = itemView.findViewById(android.R.id.text2)
        fun bind(dto: ApiService.ListaDto) {
            text1.text = dto.nombre
            text2.text = "Total: $${String.format("%.2f", dto.total)}"
            itemView.setOnClickListener { onClick(dto) }
        }
    }
}


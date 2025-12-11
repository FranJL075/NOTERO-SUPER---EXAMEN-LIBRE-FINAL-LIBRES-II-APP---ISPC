package com.notero.superapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notero.superapp.databinding.ItemListaBinding
import com.notero.superapp.model.Lista

class ListaAdapter(private val onClick: (Lista) -> Unit) : RecyclerView.Adapter<ListaAdapter.VH>() {
    private val items = mutableListOf<Lista>()

    inner class VH(private val binding: ItemListaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Lista) {
            binding.tvNombre.text = item.nombre
            binding.tvTotal.text = "${item.total}"
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemListaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<Lista>) {
        items.clear(); items.addAll(newItems); notifyDataSetChanged()
    }
}

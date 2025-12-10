package com.notero.superapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notero.superapp.databinding.ItemNegocioBinding
import com.notero.superapp.network.NegocioDto

class NegocioAdapter(private val onClick: (NegocioDto) -> Unit) : RecyclerView.Adapter<NegocioAdapter.NegocioVH>() {
    private val items = mutableListOf<NegocioDto>()

    inner class NegocioVH(private val binding: ItemNegocioBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NegocioDto) {
            binding.tvNombre.text = "${item.nombre} - ${item.descuento}%"
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NegocioVH {
        val binding = ItemNegocioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NegocioVH(binding)
    }

    override fun onBindViewHolder(holder: NegocioVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<NegocioDto>) {
        items.clear(); items.addAll(newItems); notifyDataSetChanged()
    }
}

package com.notero.superapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notero.superapp.databinding.ItemDetalleBinding
import com.notero.superapp.model.DetalleLista

class DetalleAdapter(
    private val items: MutableList<DetalleLista>,
    private val onToggleFav: (DetalleLista) -> Unit
) : RecyclerView.Adapter<DetalleAdapter.DetalleVH>() {

    inner class DetalleVH(private val binding: ItemDetalleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetalleLista) {
            binding.tvNombre.text = (if (item.producto.favorito) "★ " else "") + item.producto.nombre
            binding.tvCantidad.text = item.cantidad.toString()
            binding.tvPrecio.text = "${item.producto.precio}"
            binding.root.setOnLongClickListener { onToggleFav(item); true }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleVH {
        val binding = ItemDetalleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetalleVH(binding)
    }

    override fun onBindViewHolder(holder: DetalleVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<DetalleLista>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}

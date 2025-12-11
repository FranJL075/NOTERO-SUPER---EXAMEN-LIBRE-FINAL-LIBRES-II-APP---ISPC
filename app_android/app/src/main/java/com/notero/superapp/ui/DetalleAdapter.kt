package com.notero.superapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.notero.superapp.R
import com.notero.superapp.model.DetalleLista

class DetalleAdapter(
    private val items: MutableList<DetalleLista>,
    private val onDelete: (DetalleLista) -> Unit
) : RecyclerView.Adapter<DetalleAdapter.DetalleVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detalle, parent, false)
        return DetalleVH(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DetalleVH, position: Int) {
        holder.bind(items[position])
    }

    inner class DetalleVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        private val tvSubtotal: TextView = itemView.findViewById(R.id.tvSubtotal)
        private val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(detalle: DetalleLista) {
            tvNombre.text = detalle.producto.nombre
            tvSubtotal.text = "$${"%.2f".format(detalle.subtotal)}"
            tvCantidad.text = "x${detalle.cantidad}"
            btnDelete.setOnClickListener { onDelete(detalle) }
        }
    }

    fun update(newItems: List<DetalleLista>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}

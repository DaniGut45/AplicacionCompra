package com.example.aplicacioncompra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(
    private val productos: List<Producto>,
    private val onSelect: (Int) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    private val selectedItems = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto, position)
    }

    override fun getItemCount(): Int = productos.size

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.productName)
        private val cantidad: TextView = itemView.findViewById(R.id.productQuantity)
        private val seccion: TextView = itemView.findViewById(R.id.productSection)
        private val urgente: TextView = itemView.findViewById(R.id.productUrgent)
        private val checkBox: CheckBox = itemView.findViewById(R.id.productCheckBox)

        fun bind(producto: Producto, position: Int) {
            nombre.text = producto.nombre
            cantidad.text = producto.cantidad.toString()
            seccion.text = producto.seccion
            urgente.text = if (producto.urgente) "Urgente" else ""

            checkBox.isChecked = selectedItems.contains(position)
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItems.add(position)
                } else {
                    selectedItems.remove(position)
                }
                onSelect(position)
            }

            itemView.setOnClickListener {
                checkBox.toggle()
            }
        }
    }
}

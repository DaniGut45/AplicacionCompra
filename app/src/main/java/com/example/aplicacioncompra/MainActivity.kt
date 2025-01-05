package com.example.aplicacioncompra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private val productos = mutableListOf<Producto>()
    private val productosSeleccionados = mutableSetOf<Int>()

    private val ADD_PRODUCT_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ProductoAdapter(productos, ::onSelectProducto, ::onDeleteProducto)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivityForResult(intent, ADD_PRODUCT_REQUEST_CODE)
        }

        // Botón Borrar Todo
        findViewById<Button>(R.id.btnClearAll).setOnClickListener {
            productos.clear()
            productosSeleccionados.clear()
            adapter.notifyDataSetChanged()
        }

        // Botón Borrar Selección
        findViewById<Button>(R.id.btnClearSelection).setOnClickListener {
            // Borrar solo los productos seleccionados
            val productosBorrados = productosSeleccionados.map { productos[it] }
            productos.removeAll(productosBorrados)
            productosSeleccionados.clear()  // Limpiar selección
            adapter.notifyDataSetChanged()
        }

        // Botón Imprimir
        findViewById<Button>(R.id.btnPrint).setOnClickListener {
            val productList = productos.joinToString("\n") {
                "${it.nombre} - ${it.cantidad} - ${it.seccion}" + if (it.urgente) " - Urgente" else ""
            }

            // Crear un cuadro de diálogo con la lista de productos
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Lista de la compra")
            builder.setMessage(productList) // Mostrar la lista de productos en el mensaje
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss() // Cerrar el cuadro de diálogo al hacer clic en "OK"
            }

            builder.show() // Mostrar el cuadro de diálogo
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_PRODUCT_REQUEST_CODE && resultCode == RESULT_OK) {
            val newProduct = data?.getSerializableExtra("newProduct") as Producto
            productos.add(newProduct)
            adapter.notifyDataSetChanged()
        }
    }

    private fun onSelectProducto(position: Int) {
        // Marcar o desmarcar el producto seleccionado
        if (productosSeleccionados.contains(position)) {
            productosSeleccionados.remove(position)
        } else {
            productosSeleccionados.add(position)
        }
    }

    private fun onDeleteProducto(position: Int) {
        productos.removeAt(position)
        productosSeleccionados.remove(position) // Asegúrate de eliminar la selección si se borra
        adapter.notifyDataSetChanged()
    }
}

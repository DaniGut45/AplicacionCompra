package com.example.aplicacioncompra

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddProductActivity : AppCompatActivity() {

    private lateinit var etProductName: EditText
    private lateinit var etQuantity: EditText
    private lateinit var spinnerSection: Spinner
    private lateinit var cbUrgent: CheckBox
    private lateinit var btnSaveProduct: Button

    private val sections = arrayOf("Panadería", "Pescadería", "Frutería", "Carnicería", "Charcutería", "Conservas", "Perfumería", "General")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_producto)

        // Inicializar vistas
        etProductName = findViewById(R.id.etProductName)
        etQuantity = findViewById(R.id.etQuantity)
        spinnerSection = findViewById(R.id.spinnerSection)
        cbUrgent = findViewById(R.id.cbUrgent)
        btnSaveProduct = findViewById(R.id.btnSaveProduct)

        // Configurar el Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sections)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSection.adapter = adapter

        // Guardar producto
        btnSaveProduct.setOnClickListener {
            val productName = etProductName.text.toString()
            val quantityText = etQuantity.text.toString()
            val section = spinnerSection.selectedItem.toString()
            val isUrgent = cbUrgent.isChecked

            // Validaciones
            if (productName.isEmpty() || quantityText.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val quantity = quantityText.toIntOrNull()
            if (quantity == null || quantity <= 0) {
                Toast.makeText(this, "La cantidad debe ser un número mayor que 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear nuevo producto
            val newProduct = Producto(quantity, productName, section, isUrgent)

            // Retornar producto a MainActivity
            val resultIntent = Intent()
            resultIntent.putExtra("newProduct", newProduct)
            setResult(RESULT_OK, resultIntent)

            // Cerrar actividad
            finish()
        }
    }
}

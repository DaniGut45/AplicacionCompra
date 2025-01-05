package com.example.aplicacioncompra

import java.io.Serializable

data class Producto(
    val cantidad: Int,
    val nombre: String,
    val seccion: String,
    val urgente: Boolean
) : Serializable

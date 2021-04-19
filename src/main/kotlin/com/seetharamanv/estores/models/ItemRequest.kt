package com.seetharamanv.estores.models

data class ItemRequest(
    val name: String,
    val description: String,
    val brand: String,
    val category: String,
    val barcode: String,
    val price: Double
)
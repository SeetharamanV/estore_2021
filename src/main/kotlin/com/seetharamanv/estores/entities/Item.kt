package com.seetharamanv.estores.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "items")
data class Item(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long,
    val name: String,
    val description: String,
    val brand: String,
    val category: String,
    val barcode: String,
    val price: Double
)
package com.phonedev.boschclients.datamodel

import com.google.firebase.firestore.Exclude

data class ProductsModel(
    @get:Exclude var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var image: String? = null,
    var model: String? = null,
    var bateria: String? = null,
    var potencia: String? = null,
    var nombreVendedor: String? = null,
    var numeroTelefono: String? = null,
    var cantidad: Int = 0,
    var precio: Double = 0.0,
    var precioMayor: Double = 0.0) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductsModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}

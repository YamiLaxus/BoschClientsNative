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
    var cantidad: Int = 0,
    var precio: Double = 0.0,
    var precioMayor: Double = 0.0
) {

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

//data class ProductList(val results: List<ProductsModel> = listOf())

//room
//@Entity
//data class ProductsEntity(
//    @PrimaryKey
//    var id: String? = null,
//    @ColumnInfo(name = "name")
//    var name: String? = null,
//    @ColumnInfo(name = "description")
//    var description: String? = null,
//    @ColumnInfo(name = "image")
//    var image: String? = null,
//    @ColumnInfo(name = "model")
//    var model: String? = null,
//    @ColumnInfo(name = "bateria")
//    var bateria: String? = null,
//    @ColumnInfo(name = "potencia")
//    var potencia: String? = null,
//    @ColumnInfo(name = "cantidad")
//    var cantidad: Int = 0,
//    @ColumnInfo(name = "precio")
//    var precio: Double = 0.0,
//    @ColumnInfo(name = "precioMayor")
//    var precioMayor: Double = 0.0
//
//)
//
//fun List<ProductsEntity>.toProductList(): ProductList{
//    val resultList = mutableListOf<ProductsModel>()
//    this.forEach { ProductsEntity ->
//        resultList.add(ProductsEntity.toProduct())
//    }
//    return ProductList(resultList)
//}
//
//fun ProductsEntity.toProduct(): ProductsModel = ProductsModel(
//    this.id,
//    this.name,
//    this.description,
//    this.image,
//    this.model,
//    this.bateria,
//    this.potencia,
//    this.cantidad,
//    this.precio,
//    this.precioMayor
//)


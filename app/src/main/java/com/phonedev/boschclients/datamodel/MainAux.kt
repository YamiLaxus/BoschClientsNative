package com.phonedev.boschclients.datamodel


interface MainAux {
    fun getProductsCart(): MutableList<ProductsModel>
    fun getProductSelected(): ProductsModel?
}
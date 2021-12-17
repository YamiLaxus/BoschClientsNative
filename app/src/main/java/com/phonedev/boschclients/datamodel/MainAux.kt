package com.phonedev.boschclients.datamodel


interface MainAux {
    fun updateTotal()
    fun clearCart()

    fun getProductsCart(): MutableList<ProductsModel>
    fun getProductSelected(): ProductsModel?
    fun addProductToCart(product: ProductsModel)
}
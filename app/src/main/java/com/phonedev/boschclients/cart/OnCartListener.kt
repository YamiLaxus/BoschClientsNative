package com.phonedev.boschclients.cart

import com.phonedev.boschclients.datamodel.ProductsModel

interface OnCartListener {
    fun setQuantity(product: ProductsModel)
    fun showTotal(total: Double)
}
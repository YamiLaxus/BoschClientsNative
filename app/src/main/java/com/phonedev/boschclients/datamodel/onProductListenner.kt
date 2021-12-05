package com.phonedev.boschclients.products

import com.phonedev.boschclients.datamodel.ProductsModel

interface onProductListenner {
    fun onClick(product: ProductsModel)

}
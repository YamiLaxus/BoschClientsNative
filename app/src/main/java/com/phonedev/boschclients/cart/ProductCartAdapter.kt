package com.phonedev.boschclients.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.phonedev.boschclients.R
import com.phonedev.boschclients.databinding.ItemProductCartBinding
import com.phonedev.boschclients.datamodel.ProductsModel

class ProductCartAdapter(
    private var productList: MutableList<ProductsModel>,
    private val listener: OnCartListener
) : RecyclerView.Adapter<ProductCartAdapter.ViewHolder>() {

    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_product_cart, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]

        holder.setListener(product)

        holder.binding.tvName.text = product.name
        holder.binding.tvQuantity.text = product.cantidad.toString()

        Glide.with(context)
            .load(product.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .circleCrop()
            .into(holder.binding.imgProduct)

    }

    override fun getItemCount(): Int = productList.size

    fun add(product: ProductsModel) {
        if (!productList.contains(product)) {
            productList.add(product)
            notifyItemInserted(productList.size - 1)
        } else {
            update(product)
        }
    }

    fun update(product: ProductsModel) {
        val index = productList.indexOf(product)
        if (index != -1) {
            productList.set(index, product)
            notifyItemChanged(index)
        }
    }

    fun delete(product: ProductsModel) {
        val index = productList.indexOf(product)
        if (index != -1) {
            productList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemProductCartBinding.bind(view)

        fun setListener(product: ProductsModel) {
            binding.imbSum.setOnClickListener {
                listener.setQuantity(product)
            }
            binding.imbSub.setOnClickListener {
                listener.setQuantity(product)
            }
        }

    }
}
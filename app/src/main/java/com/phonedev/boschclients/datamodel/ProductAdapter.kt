package com.phonedev.boschclients.datamodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.phonedev.boschclients.MainActivity
import com.phonedev.boschclients.R
import com.phonedev.boschclients.databinding.ItemProductListBinding
import java.util.*
import kotlin.collections.ArrayList

class ProductAdapter(
    private val productList: MutableList<ProductsModel>,
    private val listener: MainActivity
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var context: Context

    private val mainList = productList
    private val searchList = ArrayList<ProductsModel>(productList)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_product_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]


        holder.setListener(product)

        holder.binding.tvName.text = product.name
        holder.binding.tvPrice.text = product.precio.toString()
        holder.binding.tvQuantity.text = product.cantidad.toString()

        Glide.with(context)
            .load(product.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .placeholder(R.drawable.ic_access_time)
//            .error(R.drawable.ic_error_outline)
            .centerCrop()
            .into(holder.binding.imageProduct)
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
        val binding = ItemProductListBinding.bind(view)

        fun setListener(product: ProductsModel) {
            binding.root.setOnClickListener {
                listener.onClick(product)
            }
        }
    }
}
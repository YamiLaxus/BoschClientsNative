package com.phonedev.boschclients.datamodel

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.phonedev.boschclients.MainActivity
import com.phonedev.boschclients.R
import com.phonedev.boschclients.databinding.ItemProductListBinding
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class ProductAdapter(
    private val productList: ArrayList<ProductsModel>,
    private val listener: MainActivity
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    val productListOrigina = productList

    private lateinit var context: Context


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
        holder.binding.tvDescription.text = product.description
        //holder.binding.tvQuantity.text = product.cantidad.toString()

        Glide.with(context)
            .load(product.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.loadingimage)
//            .error(R.drawable.ic_error_outline)
            .fitCenter()
            .into(holder.binding.imageProduct)
    }

    override fun getItemCount(): Int = productList.size


    @RequiresApi(Build.VERSION_CODES.N)
    fun filtrado(newText: String) {
        var longitud: Int = newText.length
        if (longitud != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                var coleccion: MutableList<ProductsModel>? = productList.stream()
                    .filter { it.name?.toLowerCase()!!.contains(newText.toLowerCase()) }
                    .collect(Collectors.toList())
                productList.clear()
                productList.addAll(coleccion!!)
            }
        } else {
            productList.clear()
            productList.addAll(productListOrigina)
        }
        notifyDataSetChanged()
    }


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
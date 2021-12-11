package com.phonedev.boschclients.cart

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.phonedev.boschclients.databinding.FragmentCartBinding
import com.phonedev.boschclients.datamodel.MainAux
import com.phonedev.boschclients.datamodel.ProductsModel

class CartFragment : BottomSheetDialogFragment(), OnCartListener {

    private var binding: FragmentCartBinding? = null

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    private lateinit var adapter: ProductCartAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentCartBinding.inflate(LayoutInflater.from(activity))
        binding?.let {
            val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
            bottomSheetDialog.setContentView(it.root)

            bottomSheetBehavior = BottomSheetBehavior.from(it.root.parent as View)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

            setupRecyclerView()
            setupBottoms()
            getProducts()

            return bottomSheetDialog
        }
        return super.onCreateDialog(savedInstanceState)

    }

    fun setupBottoms(){
        binding?.let {
            it.ibClose.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    fun setupRecyclerView(){
        binding?.let {
            adapter = ProductCartAdapter(mutableListOf(), this)

            it.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@CartFragment.adapter
            }

//            (1..5).forEach {
//                val product =
//                    ProductsModel(it.toString(), "Producto $it", "This prod$it", "", "", "", "", it, 2.0 * it, 1.0*it)
//                adapter.add(product)
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun getProducts(){
        (activity as? MainAux)?.getProductsCart()?.forEach {
            adapter.add(it)
        }
    }

    override fun setQuantity(product: ProductsModel) {
        TODO("Not yet implemented")
    }

    override fun showTotal(total: Double) {
        TODO("Not yet implemented")
    }
}
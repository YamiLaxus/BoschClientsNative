package com.phonedev.boschclients.cart

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.phonedev.boschclients.R
import com.phonedev.boschclients.databinding.FragmentCartBinding
import com.phonedev.boschclients.datamodel.MainAux
import com.phonedev.boschclients.datamodel.ProductsModel

class CartFragment :
    BottomSheetDialogFragment(), OnCartListener {

    private var binding: FragmentCartBinding? = null

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    private lateinit var adapter: ProductCartAdapter

    private var totalPrice = 0.0
    private var totalPriceMayor = 0.0
    var number: String = ""

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

    fun setupBottoms() {
        binding?.let {
            it.ibClose.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
            it.btnLuisVivas.setOnClickListener {
                number = "50256900208"

                if (totalPrice == 0.0){
                    Toast.makeText(
                        (activity as AppCompatActivity?)!!,
                        "Agrega algo a tu carrito :)",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    sendMessage()
                    requestOrder()
                }
            }
        }
    }

    private fun requestOrder() {
        dismiss()
        (activity as? MainAux)?.clearCart()
    }

    fun setupRecyclerView() {
        binding?.let {
            adapter = ProductCartAdapter(mutableListOf(), this)

            it.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@CartFragment.adapter
            }
        }
    }

    override fun onDestroy() {
        (activity as? MainAux)?.updateTotal()
        super.onDestroy()
        binding = null
    }

    fun getProducts() {
        (activity as? MainAux)?.getProductsCart()?.forEach {
            adapter.add(it)
        }
    }

    override fun setQuantity(product: ProductsModel) {
        adapter.update(product)
    }

    override fun showTotal(total: Double, totalMayor: Double) {
        totalPrice = total
        totalPriceMayor = totalMayor
        binding?.let {
            it.tvTotal.text = getString(R.string.product_full_cart, total)
            it.tvTotalMayorista.text = getString(R.string.product_full_mayor_cart, totalMayor)
        }
    }

    private fun sendMessage() {
        val user = FirebaseAuth.getInstance().currentUser?.displayName.toString()

        var pedido = ""
        pedido = "\n"
        pedido = pedido + "CLIENTE: $user"
        pedido = pedido + "\n"
        pedido = pedido + "_______________________________"

        Toast.makeText(
            (activity as AppCompatActivity?)!!,
            "Total en lista: ${adapter.itemCount}",
            Toast.LENGTH_SHORT
        ).show()

//        for (i in){
//            pedido += pedido +
//                    "\n" +
//                    "Producto: $rec[i].name" +
//                    "\n" +
//                    "Cantidad: $rec[i].nuevaCantidad" +
//                    "\n" +
//                    "_______________________________"
//        }

        pedido = pedido + "Total: Q.$totalPrice" +
        "\n"
        pedido += "Total Mayorista: Q.$totalPriceMayor"

        val url = "https://wa.me/$number?text=$pedido"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}

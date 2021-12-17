package com.phonedev.boschclients.pages

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.phonedev.boschclients.databinding.FragmentDetailsFragmentBinding
import com.phonedev.boschclients.databinding.ToolBarCustomBinding
import com.phonedev.boschclients.datamodel.MainAux
import com.phonedev.boschclients.datamodel.ProductsModel


class details_fragment : Fragment() {

    private var binding: FragmentDetailsFragmentBinding? = null
    private var product: ProductsModel? = null
    var number: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsFragmentBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Detalles del producto"
        binding?.let {
            return it.root
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.etNewQuantity?.setText("1")

        getProduct()
        clickToBuy()
    }

    fun getProduct() {
        product = (activity as? MainAux)?.getProductSelected()
        product?.let { product ->
            binding?.let {
                it.tvName.text = product.name
                it.tvDescription.text = product.description
                it.tvPotencia.text = product.potencia
                it.tvApplication.text = product.model
                it.tvTotalPrice.text = product.precio.toString()
                it.tvPrecioMayorista.text = product.precioMayor.toString()

                Glide.with(this)
                    .load(product.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.drawable.ic_access_time)
//                    .error(R.drawable.ic_broken_image)
                    .fitCenter()
                    .into(it.imageProduct)
            }
        }
    }

    override fun onDestroy() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Bosch"
        super.onDestroy()
    }

    private fun addToCart(product: ProductsModel) {
        (activity as MainAux)?.let {
            it.addProductToCart(product)
            activity?.onBackPressed()
        }
    }

    fun sendMessage() {

        var user = FirebaseAuth.getInstance().currentUser?.displayName.toString()
        var cantidad = binding?.etNewQuantity?.text.toString().toInt()
        var total: Double = product?.precio.toString().toDouble() * cantidad
        var totalMayorista: Double = product?.precioMayor.toString().toDouble() * cantidad
        var cantidadCero = 0

        var pedido = ""
        pedido = pedido + "\n"
        pedido = pedido + "CLIENTE: $user"
        pedido = pedido + "\n"
        pedido = pedido + "_______________________________"

        binding?.let {
            pedido = pedido +
                    "\n" +
                    "Producto: ${product?.name.toString()}" +
                    "\n" +
                    "Cantidad: ${cantidad.toString()}" +
                    "\n" +
                    "Precio: Q. ${product?.precio.toString()}" +
                    "\n" +
                    "Precio Mayorista: Q. ${product?.precioMayor.toString()}" +
                    "\n" +
                    "_______________________________" +
                    "\n" +
                    "TOTAL: Q. ${total.toString()}" +
                    "\n" +
                    "TOTAL Mayorista: Q. ${totalMayorista.toString()}";
        }

        val url = "https://wa.me/$number?text=$pedido"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun setNewQuantity(product: ProductsModel) {
        binding?.let {
            it.etNewQuantity.setText(product.nuevaCantidad.toString())
        }
    }

    fun clickToBuy() {
        binding?.let { binding ->
            binding.btnLuisVivas.setOnClickListener {
                if (binding.etNewQuantity.text.toString()
                        .isNotEmpty() && binding.etNewQuantity.text.toString().toInt() != 0
                ) {
                    number = "50256900208"
                    sendMessage()
                } else {
                    Toast.makeText(
                        (activity as AppCompatActivity?)!!,
                        "Ingresa la cantidad :)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.btnAroldoGomez.setOnClickListener {
                if (binding.etNewQuantity.text.toString()
                        .isNotEmpty() && binding.etNewQuantity.text.toString().toInt() != 0
                ) {
                    number = "50256900242"
                    sendMessage()
                } else {
                    Toast.makeText(
                        (activity as AppCompatActivity?)!!,
                        "Ingresa la cantidad :)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.btnYonnyMirando.setOnClickListener {
                if (binding.etNewQuantity.text.toString()
                        .isNotEmpty() && binding.etNewQuantity.text.toString().toInt() != 0
                ) {
                    number = "50242705179"
                    sendMessage()
                } else {
                    Toast.makeText(
                        (activity as AppCompatActivity?)!!,
                        "Ingresa la cantidad :)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.btnFranciscoRamirez.setOnClickListener {
                if (binding.etNewQuantity.text.toString()
                        .isNotEmpty() && binding.etNewQuantity.text.toString().toInt() != 0
                ) {
                    number = "50256900166"
                    sendMessage()
                } else {
                    Toast.makeText(
                        (activity as AppCompatActivity?)!!,
                        "Ingresa la cantidad :)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.btnMynorMateo.setOnClickListener {
                if (binding.etNewQuantity.text.toString()
                        .isNotEmpty() && binding.etNewQuantity.text.toString().toInt() != 0
                ) {
                    number = "50256946716"
                    sendMessage()
                } else {
                    Toast.makeText(
                        (activity as AppCompatActivity?)!!,
                        "Ingresa la cantidad :)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.btnAlexDias.setOnClickListener {
                if (binding.etNewQuantity.text.toString()
                        .isNotEmpty() && binding.etNewQuantity.text.toString().toInt() != 0
                ) {
                    number = "50256900212"
                    sendMessage()
                } else {
                    Toast.makeText(
                        (activity as AppCompatActivity?)!!,
                        "Ingresa la cantidad :)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.btnAddCart.setOnClickListener {
                if (binding.etNewQuantity.text.toString()
                        .isNotEmpty() && binding.etNewQuantity.text.toString().toInt() != 0
                ) {
                    product?.nuevaCantidad = binding.etNewQuantity.text.toString().toInt()
                    product?.let { it1 -> addToCart(it1) }
                } else {
                    Toast.makeText(
                        (activity as AppCompatActivity?)!!,
                        "Ingresa la cantidad :)",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }
}
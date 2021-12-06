package com.phonedev.boschclients

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.phonedev.boschclients.databinding.ActivityMainBinding
import com.phonedev.boschclients.datamodel.MainAux
import com.phonedev.boschclients.datamodel.ProductAdapter
import com.phonedev.boschclients.datamodel.ProductsModel
import com.phonedev.boschclients.pages.AboutActivity
import com.phonedev.boschclients.pages.details_fragment
import com.phonedev.boschclients.products.onProductListenner
import com.phonedev.pocketadmin.entities.Constants
import java.util.*
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.DefaultItemAnimator




class MainActivity : AppCompatActivity(), onProductListenner, MainAux, SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter

    //Frebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var firestoreListenner: ListenerRegistration

    private var productSelected: ProductsModel? = null


    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val response = IdpResponse.fromResultIntent(it.data)

            if (it.resultCode == RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    Toast.makeText(this, "Hola Bienvenido", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (response == null) {
                    Toast.makeText(this, "Hasta Pronto", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    response.error?.let {
                        if (it.errorCode == ErrorCodes.NO_NETWORK) {
                            Toast.makeText(this, "Sin Coneccion", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(
                                this,
                                "Codigo de error: ${it.errorCode}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.tool_bar_custom)

        binding.searchView.setOnQueryTextListener(this)

        binding.imbReload.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        configAuth()
        configFirestoreRealTime()
        configStackImages()
        configRecyclerView()

    }

    private fun configStackImages() {
        var imageList = ArrayList<SlideModel>()

        imageList.add(
            SlideModel(
                "https://firebasestorage.googleapis.com/v0/b/boschdb-c958e.appspot.com/o/stack_images%2Fgood.jpg?alt=media&token=294e548f-83d0-4133-8c6f-b39b6ff808d9",
                "We are Bosch"
            )
        )
        imageList.add(
            SlideModel(
                "https://firebasestorage.googleapis.com/v0/b/boschdb-c958e.appspot.com/o/stack_images%2Fimage_2.png?alt=media&token=3bf42da9-e96b-4b45-819f-e4a8718ca497",
                "Liviana, Compacta y Ergonómica."
            )
        )
        imageList.add(
            SlideModel(
                "https://firebasestorage.googleapis.com/v0/b/boschdb-c958e.appspot.com/o/stack_images%2Fimage_3.png?alt=media&token=ce5a5904-4a7d-4c12-9884-fd74aea13a59",
                ""
            )
        )
        imageList.add(
            SlideModel(
                "https://firebasestorage.googleapis.com/v0/b/boschdb-c958e.appspot.com/o/stack_images%2Fimage_4.png?alt=media&token=8cf97574-033b-46a4-bf14-b8a6f22efc25",
                ""
            )
        )
        imageList.add(
            SlideModel(
                "https://firebasestorage.googleapis.com/v0/b/boschdb-c958e.appspot.com/o/stack_images%2Fimage_5.png?alt=media&token=1278f6aa-2888-4f2d-b74b-41aef9aa8161",
                ""
            )
        )
        imageList.add(
            SlideModel(
                "https://firebasestorage.googleapis.com/v0/b/boschdb-c958e.appspot.com/o/stack_images%2Fbosch_logo.png?alt=media&token=5f9ccfe2-91b8-4cd5-9af8-f0c9f50af2d0",
                ""
            )
        )
        imageList.add(
            SlideModel(
                "https://firebasestorage.googleapis.com/v0/b/boschdb-c958e.appspot.com/o/stack_images%2Fscreen%20whatsapp.jpg?alt=media&token=e284d5e2-680f-41e3-8b9a-9de27f34d055",
                "Ordena por WhatsApp."
            )
        )

        binding.imageStack.setImageList(imageList, ScaleTypes.FIT)
    }


    private fun configAuth() {
        firebaseAuth = FirebaseAuth.getInstance()
        authStateListener = FirebaseAuth.AuthStateListener { auth ->
            if (auth.currentUser != null) {
                supportActionBar?.title = auth.currentUser?.displayName
                supportActionBar?.show()
                binding.llProgress.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            } else {
                val providers = arrayListOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )

                resultLauncher.launch(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build()
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        firebaseAuth.addAuthStateListener(authStateListener)
        configFirestoreRealTime()
    }

    override fun onPause() {
        super.onPause()
        firebaseAuth.removeAuthStateListener(authStateListener)
        firestoreListenner.remove()
    }

    private fun configRecyclerView() {
        adapter = ProductAdapter(ArrayList(), this)
//        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.setItemAnimator(DefaultItemAnimator())
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(
                this@MainActivity, 2,
                GridLayoutManager.VERTICAL, false
            )
            adapter = this@MainActivity.adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sign_out -> {
                AuthUI.getInstance().signOut(this)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_SHORT).show()
                    }
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            binding.recyclerView.visibility = View.GONE
                            binding.llProgress.visibility = View.VISIBLE
                        } else {
                            Toast.makeText(this, "Sesión no Cerrada", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            R.id.action_info -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    private fun configFirestoreRealTime() {
            val db = FirebaseFirestore.getInstance()
            val productRef = db.collection(Constants.COLL_PRODUCTS)

            firestoreListenner = productRef.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(this, "Error al consultar datos", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                for (snapshot in snapshot!!.documentChanges) {
                    val product = snapshot.document.toObject(ProductsModel::class.java)
                    product.id = snapshot.document.id
                    when (snapshot.type) {
                        DocumentChange.Type.ADDED -> adapter.add(product)
                        DocumentChange.Type.MODIFIED -> adapter.update(product)
                        DocumentChange.Type.REMOVED -> adapter.delete(product)
                    }
                }
            }
    }

    override fun onClick(product: ProductsModel) {
        productSelected = product

        val fragment = details_fragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.containerMain, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun getProductSelected(): ProductsModel? = productSelected


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            adapter.filtrado(newText)
        } else {
           configFirestoreRealTime()
        }
        return false
    }
}
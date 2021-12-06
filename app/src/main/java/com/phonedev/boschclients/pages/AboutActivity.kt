package com.phonedev.boschclients.pages

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.phonedev.boschclients.R
import com.phonedev.boschclients.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding
    var num = "41642429"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Acerca de..."

        clickButton()
    }

    fun clickButton(){
        binding.btnWhatsApp.setOnClickListener {
            goToWhatsApp()
        }
        binding.btnFacebook.setOnClickListener {
            goToFacebook()
        }
        binding.btnInstagram.setOnClickListener {
            goToInstagram()
        }
    }

    fun goToInstagram(){
        val url = "https://www.instagram.com/osvaldo_ez/"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    fun goToFacebook(){
        val url = "https://www.facebook.com/def.alt.00101"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    fun goToWhatsApp(){
        val url = "https://wa.me/502$num"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}
package com.phonedev.boschclients.pages

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phonedev.boschclients.databinding.ActivityNotFoundBinding

class NotFoundActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotFoundBinding
    private var num = "41642429"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotFoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        clickButton()
    }

    private fun clickButton() {
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

    private fun goToInstagram() {
        val url = "https://www.instagram.com/osvaldo_ez/"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun goToFacebook() {
        val url = "https://www.facebook.com/def.alt.00101"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun goToWhatsApp() {
        val url = "https://wa.me/502$num"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}
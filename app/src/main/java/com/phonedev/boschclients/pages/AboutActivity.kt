package com.phonedev.boschclients.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phonedev.boschclients.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar?.title = "Acerca de..."
    }
}
package com.phonedev.boschclients.pages

import android.content.AsyncQueryHandler
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.phonedev.boschclients.R

class SplashActivity : AppCompatActivity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish()//Evitar que regrese a esta activity
    }
}
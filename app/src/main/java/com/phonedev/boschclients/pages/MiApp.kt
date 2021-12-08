package com.phonedev.boschclients.pages

import android.app.Application
import android.os.SystemClock

class MiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SystemClock.sleep(400)
    }
}
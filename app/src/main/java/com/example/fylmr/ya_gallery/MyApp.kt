package com.example.fylmr.ya_gallery

import android.app.Application
import com.vk.sdk.VKSdk

class MyApp : Application() {
    override fun onCreate() {
        VKSdk.initialize(applicationContext)
        super.onCreate()
    }
}
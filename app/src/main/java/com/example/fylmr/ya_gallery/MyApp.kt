package com.example.fylmr.ya_gallery

import android.app.Application
import android.content.Intent
import android.util.Log
import com.example.fylmr.ya_gallery.activities.MainActivity
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk


class MyApp : Application() {
    val TAG = "MyApp"


    var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                Log.e(TAG, "VK Login error")
                toLoginActivity()
            }
        }
    }

    private fun toLoginActivity() {
        val goToLoginIntent = Intent(applicationContext, MainActivity::class.java)
        startActivity(goToLoginIntent)
    }

    override fun onCreate() {
        super.onCreate()
        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(applicationContext)
    }
}
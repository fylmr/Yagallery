package com.example.fylmr.ya_gallery

import android.app.Application
import android.util.Log
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk


class MyApp : Application() {
    val TAG = "MyApp"


    var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                Log.e(TAG, "VK Login error")
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(applicationContext)
    }
}
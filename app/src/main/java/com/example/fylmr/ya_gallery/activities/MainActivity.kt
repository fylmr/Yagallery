package com.example.fylmr.ya_gallery.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.fylmr.ya_gallery.R
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!VKSdk.isLoggedIn())
            VKSdk.login(this, VKScope.PHOTOS)
        else
            skipToNextActvity()


    }

    private fun skipToNextActvity() {
        val goToGalleryIntent = Intent(applicationContext, GalleryActivity::class.java)
        startActivity(goToGalleryIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data,
                        object : VKCallback<VKAccessToken> {
                            override fun onResult(res: VKAccessToken) {
                                skipToNextActvity()
                            }

                            override fun onError(error: VKError) {
                                showLoginError(error)
                            }
                        }
                )) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showLoginError(error: VKError) {
        Log.e(TAG, "VK Login error")
        Toast.makeText(applicationContext, error.errorMessage, Toast.LENGTH_SHORT).show()
    }
}

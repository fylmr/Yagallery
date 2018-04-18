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
            skipToNextActivity()


    }

    private fun skipToNextActivity() {
        val goToGalleryIntent = Intent(applicationContext, GalleryActivity::class.java)
        goToGalleryIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(goToGalleryIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data,
                        object : VKCallback<VKAccessToken> {
                            override fun onResult(res: VKAccessToken) {
                                skipToNextActivity()
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

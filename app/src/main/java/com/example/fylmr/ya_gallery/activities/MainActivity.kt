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
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!VKSdk.isLoggedIn()) {
            login_vk_btn.setOnClickListener {
                VKSdk.login(this, VKScope.PHOTOS)
            }
        } else {
            skipToNextActivity()
        }


    }

    private fun skipToNextActivity() {
        val goToGalleryIntent = Intent(applicationContext, GalleryActivity::class.java)
        goToGalleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
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
        Toast.makeText(applicationContext, resources.getString(R.string.vk_login_error), Toast.LENGTH_SHORT).show()
    }
}

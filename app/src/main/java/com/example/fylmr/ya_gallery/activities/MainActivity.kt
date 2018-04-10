package com.example.fylmr.ya_gallery.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.fylmr.ya_gallery.R
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        VKSdk.login(this, VKScope.PHOTOS)

//        skipToNextActvity()
    }

    private fun skipToNextActvity() {
        val goToGalleryIntent = Intent(applicationContext, GalleryActivity::class.java)
        startActivity(goToGalleryIntent)
    }
}

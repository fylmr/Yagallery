package com.example.fylmr.ya_gallery.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.fylmr.ya_gallery.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        skipToNextActvity()
    }

    private fun skipToNextActvity() {
        val goToGalleryIntent = Intent(applicationContext, GalleryActivity::class.java)
        startActivity(goToGalleryIntent)
    }
}

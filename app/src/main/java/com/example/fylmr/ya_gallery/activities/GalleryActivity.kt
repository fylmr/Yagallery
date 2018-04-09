package com.example.fylmr.ya_gallery.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.fylmr.ya_gallery.R
import com.example.fylmr.ya_gallery.presenters.GalleryPresenter

class GalleryActivity : MvpAppCompatActivity() {

    @InjectPresenter
    lateinit var galleryPresenter: GalleryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)


    }
}

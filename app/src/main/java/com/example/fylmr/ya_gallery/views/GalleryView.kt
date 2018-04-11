package com.example.fylmr.ya_gallery.views

import android.content.Intent
import com.arellomobile.mvp.MvpView
import com.example.fylmr.ya_gallery.entities.Picture

interface GalleryView: MvpView {
    fun populateGallery(pics: MutableList<Picture>)
    fun photoClicked(picture: Picture)

    fun openActivityForResult(intent: Intent)
}
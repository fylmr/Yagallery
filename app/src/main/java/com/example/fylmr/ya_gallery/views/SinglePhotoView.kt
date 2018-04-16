package com.example.fylmr.ya_gallery.views

import android.graphics.Bitmap
import com.arellomobile.mvp.MvpView

interface SinglePhotoView : MvpView {
    fun showPicture(url: String)
    fun showPicture(bmp: Bitmap)
    fun showFullPicture(url: String)
}
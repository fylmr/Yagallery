package com.example.fylmr.ya_gallery.views

import android.graphics.Bitmap
import com.arellomobile.mvp.MvpView

interface SinglePhotoView : MvpView {
    /**
     * Shows the picture.
     *
     * @param bmp [Bitmap] of the picture
     */
    fun showPicture(bmp: Bitmap)

    /**
     * Shows provided picture and also sets fullPictureSet flag to true.
     */
    fun showFullPicture(bmp: Bitmap)

    /**
     * Shows loader icon.
     */
    fun showLoading()

    /**
     * Hides loader icon.
     */
    fun hideLoading()
}
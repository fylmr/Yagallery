package com.example.fylmr.ya_gallery.views

import android.content.Intent
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.fylmr.ya_gallery.entities.Picture

interface GalleryView : MvpView {
    //    fun populateGallery(pics: MutableList<Picture>)
    fun addToGallery(pics: MutableList<Picture>)

    @StateStrategyType(SkipStrategy::class)
    fun photoClicked(picture: Picture)

    @StateStrategyType(SkipStrategy::class)
    fun openActivityForResult(intent: Intent, requestCode: Int? = null)
}
package com.example.fylmr.ya_gallery.presenters

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.fylmr.ya_gallery.models.VKPicsModel
import com.example.fylmr.ya_gallery.views.GalleryView

@InjectViewState
class GalleryPresenter : MvpPresenter<GalleryView>() {
    val TAG = "Gallery Presenter"

    var applicationContext: Context? = null

    val vkPicsModel = VKPicsModel()

    fun start(context: Context?) {
        /*
        When activity is created, it passes it's Context to presenter
        */

        Log.d(TAG, "start()")

        if (context != null) {
            this.applicationContext = context
        } else {
            Log.e(TAG, "Passed application context is null")
        }

        populateGallery()
    }

    private fun populateGallery() {
        vkPicsModel.getAllCurrentUserPictures({
            viewState.populateGallery(it)
        }, {
            Log.w(TAG, "Error populating gallery")
        })
    }

}

package com.example.fylmr.ya_gallery.presenters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.fylmr.ya_gallery.Constants
import com.example.fylmr.ya_gallery.activities.MainActivity
import com.example.fylmr.ya_gallery.activities.SinglePhotoActivity
import com.example.fylmr.ya_gallery.entities.Picture
import com.example.fylmr.ya_gallery.models.VKPicsModel
import com.example.fylmr.ya_gallery.views.GalleryView
import com.vk.sdk.VKSdk

/**
 * Photos gallery presenter.
 */
@InjectViewState
class GalleryPresenter : MvpPresenter<GalleryView>() {
    val TAG = "Gallery Presenter"

    var applicationContext: Context? = null

    val vkPicsModel = VKPicsModel()

    var pagesDownloaded = 0

    /**
     * When activity is created, it passes it's [Context] to presenter
     * and starts [populateGallery]
     */
    @Throws(NullPointerException::class)
    fun start(context: Context?) {


        Log.d(TAG, "start()")

        if (context != null) {
            this.applicationContext = context
        } else {
            throw NullPointerException("Passed application context is null")
        }

        populateGallery()
    }

    /**
     * Fill gallery with photos.
     */
    private fun populateGallery() {
        vkPicsModel.getFirstCurrentUserPictures({
            viewState.populateGallery(it)

            this.pagesDownloaded += 1
        }, {
            Toast.makeText(
                    applicationContext,
                    "Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
            ).show()

            Log.e(TAG, "Error populating gallery")
        })
    }

    /**
     * Opens fullsize photo in [SinglePhotoActivity].
     *
     * @param picture Picture to open in full size.
     */
    fun openPhoto(picture: Picture) {
        val goToFullScreenPic = Intent(applicationContext, SinglePhotoActivity::class.java)
        goToFullScreenPic.putExtra(Constants.ExtrasNames.PICTURE, picture)

        viewState.openActivityForResult(goToFullScreenPic)
    }

    /**
     * Logout from VK account and get back to [MainActivity].
     */
    fun logout() {
        VKSdk.logout()

        if (applicationContext != null) {
            val toMainActivityIntent = Intent(applicationContext, MainActivity::class.java)
            applicationContext!!.startActivity(toMainActivityIntent)
        }
    }

    /**
     *
     */
    fun galleryEndReached() {
        vkPicsModel.getCurrentUserPictures(null,
                Constants.VKMethods.DEFAULT_PHOTO_COUNT * this.pagesDownloaded,
                { downloadedPics ->
                    viewState.addToGallery(downloadedPics)
                    this.pagesDownloaded++
                },
                { errorString ->
                    Toast.makeText(
                            applicationContext,
                            "Please check your internet connection and try again.",
                            Toast.LENGTH_SHORT
                    ).show()

                    Log.e(TAG, "Error adding photos to gallery. Error + $errorString")
                }
        )
    }

}

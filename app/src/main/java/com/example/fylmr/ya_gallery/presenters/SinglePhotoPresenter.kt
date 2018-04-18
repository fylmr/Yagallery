package com.example.fylmr.ya_gallery.presenters

import android.app.Activity
import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.fylmr.ya_gallery.entities.Picture
import com.example.fylmr.ya_gallery.models.VKPicsModel
import com.example.fylmr.ya_gallery.views.SinglePhotoView

@InjectViewState
class SinglePhotoPresenter : MvpPresenter<SinglePhotoView>() {
    val TAG = "SinglePhotoPresenter"

    lateinit var context: Context

    lateinit var pic: Picture

    /**
     * Starts the [SinglePhotoPresenter]
     */
    fun start(context: Context?) {
        try {
            initContext(context)
        } catch (e: NullPointerException) {
            Log.e(TAG, "Passed context is null")
        } catch (e: ClassCastException) {
            Log.e(TAG, "Passed context is not Activity context")
        }
    }

    /**
     *   Get activity context from activity.
     *   @throws NullPointerException when context is null
     *   @throws ClassCastException when context is not Activity context (application context for example)
     **/
    @Throws(NullPointerException::class, ClassCastException::class)
    private fun initContext(context: Context?) {


        Log.d(TAG, "start()")

        if (context == null) {
            throw NullPointerException("Context is null")
        } else if (context !is Activity) {
            throw ClassCastException("Passed context is not of Activity class")
        } else {
            this.context = context
        }
    }

    fun handlePicFromIntent(pic: Picture?) {
        try {
            passPicToActivity(pic)
        } catch (e: NullPointerException) {
            Log.e(TAG, "Pic is empty")
        }
    }

    /**
     * Checks for common errors and passes picture to activity
     * @throws NullPointerException when picture is null
     */
    @Throws(NullPointerException::class)
    private fun passPicToActivity(pic: Picture?) {
        if (pic == null) {
            throw NullPointerException("Picture is null")
        }

        this.pic = pic

        if (pic.bmp != null) {
            Log.v(TAG, "Setting thorugh bmp")

            viewState.showPicture(pic.bmp!!)

        } else if (pic.url != null) {
            Log.v(TAG, "Setting through url")

            viewState.showPicture(pic.url!!)

        }

    }

    /**
     * Gets full size picture when the small one is finished loading
     */
    fun askFullPicture() {
        Log.v(TAG, "askFullPicture()")

        Log.d(TAG, pic.toString())

        VKPicsModel().getHighResPictureByID(
                pic.photo_id!!,
                pic.owner_id!!,
                "",
                { pic ->
                    viewState.showFullPicture(pic.url!!)

                    this.pic = pic
                },
                { error ->
                    Log.e(TAG, "getHighResPictureByID failed with error: $error")
                }
        )
    }


}
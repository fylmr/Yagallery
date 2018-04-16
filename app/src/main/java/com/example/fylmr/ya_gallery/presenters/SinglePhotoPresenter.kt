package com.example.fylmr.ya_gallery.presenters

import android.app.Activity
import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.fylmr.ya_gallery.entities.Picture
import com.example.fylmr.ya_gallery.views.SinglePhotoView

@InjectViewState
class SinglePhotoPresenter : MvpPresenter<SinglePhotoView>() {
    val TAG = "SinglePhotoPresenter"

    lateinit var context: Context

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

        if (pic.bmp != null) {
            viewState.showPicture(pic.bmp!!)
        } else if (pic.url != null) {
            //todo Добавить передачу ЮРЛ большой картинки
            viewState.showPicture(pic.url!!)
        }

    }


}
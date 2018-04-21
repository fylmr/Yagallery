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
     *   Set activity context to the presenter context field
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

    /**
     * A [passPicToActivity] wrapper method.
     */
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

        viewState.showLoading()
        if (pic.bmp == null) {
            pic.makeBmp(context, { bmp ->
                if (bmp != null) {
                    passPicToActivity(pic)

                } else {
                    Log.e(TAG, "passPicToActivity couldn't make Bitmap")
                    //todo
                }
            })
        } else {
            viewState.showPicture(pic.bmp!!)
            pic.saveToCache(context)
        }

        viewState.hideLoading()
        this.pic = pic

    }

    /**
     * Gets full size picture.
     */
    fun askFullPicture() {
        Log.v(TAG, "askFullPicture()")

        VKPicsModel().getHighResPictureByID(
                pic.photo_id!!,
                pic.owner_id!!,
                "",
                { pic ->
                    //todo Возможно, проблема где-то здесь, он обновляет не эту картинку, а скачанную


                    showFullPicture(pic)
                },
                { error ->
                    Log.e(TAG, "getHighResPictureByID failed with error: $error")
                }
        )
    }

    private fun showFullPicture(pic: Picture) {
        viewState.showLoading()

        this.pic = pic

        this.pic.makeBmp(context) { bmp ->

            if (bmp != null) {
                Log.v(TAG, "bmp is not null")

                viewState.showFullPicture(this.pic.bmp!!)
                viewState.hideLoading()

                pic.saveToCache(context)


            } else {
                Log.v(TAG, "bmp is null")
            }
        }
    }


}
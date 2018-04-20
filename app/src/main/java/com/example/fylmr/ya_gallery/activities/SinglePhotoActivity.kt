package com.example.fylmr.ya_gallery.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.fylmr.ya_gallery.Constants
import com.example.fylmr.ya_gallery.R
import com.example.fylmr.ya_gallery.entities.Picture
import com.example.fylmr.ya_gallery.presenters.SinglePhotoPresenter
import com.example.fylmr.ya_gallery.views.SinglePhotoView
import kotlinx.android.synthetic.main.activity_single_photo.*

class SinglePhotoActivity : MvpAppCompatActivity(), SinglePhotoView {
    val TAG = "SinglePhotoActivity"

    @InjectPresenter
    lateinit var singlePhotoPresenter: SinglePhotoPresenter

    var fullPictureSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_photo)

        singlePhotoPresenter.start(this)

        val pic = intent.getParcelableExtra<Picture>(Constants.ExtrasNames.PICTURE)
        singlePhotoPresenter.handlePicFromIntent(pic)
    }

    override fun showPicture(bmp: Bitmap) {
        Log.v(TAG, "showPicture(bmp)")

        single_photo_imgview.setImageBitmap(bmp)

        if (!fullPictureSet)
            askFullPicture()
    }


    override fun showFullPicture(bmp: Bitmap) {
        Log.v(TAG, "showFullPicture(bmp)")

        fullPictureSet = true

        showPicture(bmp)
    }

    fun askFullPicture() {
        singlePhotoPresenter.askFullPicture()
    }

    override fun showLoading() {
        Log.v(TAG, "showLoading()")

        single_photo_pb.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        Log.v(TAG, "hideLoading()")

        single_photo_pb.visibility = View.GONE
    }
}

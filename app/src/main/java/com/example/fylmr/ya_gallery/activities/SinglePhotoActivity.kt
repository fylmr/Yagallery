package com.example.fylmr.ya_gallery.activities

import android.graphics.Bitmap
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.fylmr.ya_gallery.Constants
import com.example.fylmr.ya_gallery.R
import com.example.fylmr.ya_gallery.entities.Picture
import com.example.fylmr.ya_gallery.presenters.SinglePhotoPresenter
import com.example.fylmr.ya_gallery.views.SinglePhotoView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_single_photo.*

class SinglePhotoActivity : MvpAppCompatActivity(), SinglePhotoView {

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

    override fun showPicture(url: String) {
        Picasso.with(this)
                .load(url)
                .into(single_photo_imgview)

        if (!fullPictureSet)
            askFullPicture()
    }

    override fun showFullPicture(url: String) {
        fullPictureSet = true

        showPicture(url)
    }

    fun askFullPicture() {
        singlePhotoPresenter.askFullPicture()
    }

    override fun showPicture(bmp: Bitmap) {
        single_photo_imgview.setImageBitmap(bmp)
    }
}

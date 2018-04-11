package com.example.fylmr.ya_gallery.activities

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.fylmr.ya_gallery.R
import com.example.fylmr.ya_gallery.presenters.SinglePhotoPresenter
import com.example.fylmr.ya_gallery.views.SinglePhotoView

class SinglePhotoActivity : MvpAppCompatActivity(), SinglePhotoView {

    @InjectPresenter
    lateinit var singlePhotoPresenter: SinglePhotoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_photo)
    }
}

package com.example.fylmr.ya_gallery.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.fylmr.ya_gallery.R
import com.example.fylmr.ya_gallery.adapters.GalleryAdapter
import com.example.fylmr.ya_gallery.entities.Picture
import com.example.fylmr.ya_gallery.presenters.GalleryPresenter
import com.example.fylmr.ya_gallery.views.GalleryView
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : MvpAppCompatActivity(), GalleryView {

    @InjectPresenter
    lateinit var galleryPresenter: GalleryPresenter

    //App preferences
    lateinit var sharedPref: SharedPreferences

    // Photos list and adapter for it
    private var pics = mutableListOf<Picture>()
    private lateinit var galleryAdapter: GalleryAdapter

    //Span count in gallery
    private var spans: Int = 3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        // Setting spans preferences
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        spans = sharedPref.getInt(getString(R.string.saved_gallery_spans_key), spans)

        // Initializing Pictures RecyclerView
        galleryAdapter = GalleryAdapter(applicationContext, pics)
        initializeRecyclerView()

        // Passing context to presenter
        galleryPresenter.start(applicationContext)
    }

    private fun initializeRecyclerView() {
        /*Initialize recycler view with selected spans and adapter initialized above*/

        gallery_rv.layoutManager = GridLayoutManager(applicationContext, spans)
        gallery_rv.adapter = galleryAdapter
    }

    override fun populateGallery(pics: MutableList<Picture>) {
        this.pics.clear()
        this.pics.addAll(pics)
    }
}

package com.example.fylmr.ya_gallery.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.fylmr.ya_gallery.Constants
import com.example.fylmr.ya_gallery.R
import com.example.fylmr.ya_gallery.adapters.GalleryAdapter
import com.example.fylmr.ya_gallery.entities.Picture
import com.example.fylmr.ya_gallery.presenters.GalleryPresenter
import com.example.fylmr.ya_gallery.views.GalleryView
import kotlinx.android.synthetic.main.activity_gallery.*


class GalleryActivity : MvpAppCompatActivity(), GalleryView {
    val TAG = "GalleryActivity"

    @InjectPresenter
    lateinit var galleryPresenter: GalleryPresenter

//    /**
//     * App preferences
//     */
//    lateinit var sharedPref: SharedPreferences

    /**
     *  Photos mutable list.
     */
    private var pics = mutableListOf<Picture>()

    /**
     * Adapter for [pics] mutable list.
     */
    private lateinit var galleryAdapter: GalleryAdapter

    //Span count in gallery
    private var spans: Int = 3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

//        // Setting spans preferences
//        sharedPref = getPreferences(Context.MODE_PRIVATE)
//        spans = sharedPref.getInt(getString(R.string.saved_gallery_spans_key), spans)

        // Initializing Pictures RecyclerView
        galleryAdapter = GalleryAdapter(this, pics)
        initializeRecyclerView()

        // Detect if recyclerview reaches it's end
        gallery_rv.addOnScrollListener(GalleryEndChecker())

        // Passing context to presenter
        galleryPresenter.start(applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.smpl_logout_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.logout_menu_item -> {
                galleryPresenter.logout()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Initialize recycler view with selected spans and adapter initialized above.
     */
    private fun initializeRecyclerView() {
        gallery_rv.layoutManager = GridLayoutManager(applicationContext, spans)
        gallery_rv.adapter = galleryAdapter
    }


    /**
     * Fills currect [pics] MutableList with given [pics].
     *
     * @param pics MutableList of [Picture]s.
     */
    override fun addToGallery(pics: MutableList<Picture>) {
        this.pics.addAll(pics)

        this.galleryAdapter.notifyDataSetChanged()
    }

    /**
     * Called when photo is clicked in [GalleryAdapter].
     *
     * @param picture Clicked picture
     */
    override fun photoClicked(picture: Picture) {
        galleryPresenter.openPhoto(picture)
    }

    /**
     * Opens activity with selected request code.
     *
     * @param intent Intent that should be opened for result
     * @param requestCode Integer showing request code
     */
    override fun openActivityForResult(intent: Intent, requestCode: Int?) {
        if (requestCode == null)
            startActivityForResult(intent, Constants.RequestCodes.OPEN_PHOTO_FULL_SCREEN)
        else
            startActivityForResult(intent, requestCode)

    }

    /**
     * Checks for gallery end reached and calls corresponding [galleryPresenter] method.
     */
    inner class GalleryEndChecker : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == RecyclerView.SCROLL_STATE_IDLE && !gallery_rv.canScrollVertically(1))
                galleryPresenter.addPhotosToGallery()
        }

    }
}

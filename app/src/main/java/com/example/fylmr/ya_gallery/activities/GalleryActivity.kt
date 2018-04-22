package com.example.fylmr.ya_gallery.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
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

    @InjectPresenter
    lateinit var galleryPresenter: GalleryPresenter

    /**
     * App preferences
     */
    lateinit var sharedPref: SharedPreferences

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

        // Setting spans preferences
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        spans = sharedPref.getInt(getString(R.string.saved_gallery_spans_key), spans)

        // Initializing Pictures RecyclerView
        galleryAdapter = GalleryAdapter(this, pics)
        initializeRecyclerView()

        // Passing context to presenter
        galleryPresenter.start(applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.smpl_logout_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.logout_menu_item -> {
                galleryPresenter.logout()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
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
     * Replaces currect [pics] MutableList with given [pics] parameter.
     *
     * @param pics MutableList of [Picture]s.
     */
    override fun populateGallery(pics: MutableList<Picture>) {
        this.pics.clear()
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
     * Opens activity with selected request code
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
}

package com.example.fylmr.ya_gallery.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fylmr.ya_gallery.R
import com.example.fylmr.ya_gallery.entities.Picture

class GalleryAdapter(var context: Context, var pics: MutableList<Picture>): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return pics.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_gallery_picture, parent))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(var v: View): RecyclerView.ViewHolder(v) {

    }
}
package com.example.fylmr.ya_gallery.entities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class Picture() : Parcelable {

    var url: String? = null
    // todo Добавить ЮРЛ большой картинки и юрл маленькой отдельно

    var photo_id: String? = null
    var owner_id: String? = null
    var album_id: String? = null

    var bmp: Bitmap? = null

    constructor(parcel: Parcel) : this() {
        url = parcel.readString()
        photo_id = parcel.readString()
        owner_id = parcel.readString()
        album_id = parcel.readString()
        bmp = parcel.readParcelable(Bitmap::class.java.classLoader)
    }

    override fun toString(): String {
        return "URL: $url \n " +
                "Photo ID: $photo_id \n" +
                "Owner ID: $owner_id \n" +
                "Album ID: $album_id \n"
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        if (dest != null) {
            dest.writeString(url)

            dest.writeString(photo_id)
            dest.writeString(owner_id)
            dest.writeString(album_id)

            dest.writeParcelable(bmp, 1)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    fun saveToCache(context: Context) {
        Log.v("Picture", "writeToCache()")

        if (bmp == null)
            makeBmp(context) {
                if (bmp != null)
                    saveImageToInternalStorage(context, bmp!!)
            }
        else
            saveImageToInternalStorage(context, bmp!!)
    }

    fun makeBmp(context: Context, onFinish: () -> Unit) {
        Log.v("Picture", "makeBmp()")

        Picasso.with(context)
                .load(url)
                .into(object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    }

                    override fun onBitmapFailed(errorDrawable: Drawable?) {
                        Log.w("Picture", "onBitmapFailed ${errorDrawable.toString()}")
                    }

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        bmp = bitmap
                        onFinish()
                    }
                })
    }

    private fun saveImageToInternalStorage(context: Context, image: Bitmap): Boolean {
        Log.v("Picture", "saveImageToIntStorage")

        try {
            val fos = context.openFileOutput("${owner_id}_${photo_id}", Context.MODE_PRIVATE)

            image.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()

            return true
        } catch (e: Exception) {
            Log.e("Picture", "saveImageToIntStorage error: ${e.message}")
            return false
        }

    }

    companion object CREATOR : Parcelable.Creator<Picture> {
        override fun createFromParcel(parcel: Parcel): Picture {
            return Picture(parcel)
        }

        override fun newArray(size: Int): Array<Picture?> {
            return arrayOfNulls(size)
        }
    }
}
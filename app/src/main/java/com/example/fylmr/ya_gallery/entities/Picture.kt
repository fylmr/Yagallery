package com.example.fylmr.ya_gallery.entities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/**
 * VK Picture class.
 * Contains url to it, it's photoId, ownerId, albumId [String]s.
 */
class Picture() : Parcelable {
    val TAG = "Picture"

    var url: String? = null

    var photo_id: String? = null
    var owner_id: String? = null

    @Deprecated("Unused")
    var album_id: String? = null

    var bmp: Bitmap? = null

    /**
     * Otherwise the target would be garbage collected.
     */
    var protectedFromGarbageCollectorTargets = HashSet<Target>()

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

    /**
     * Saves picture to storage.
     * @param context is used to save to internal storage
     */
    fun saveToCache(context: Context) {
        Log.v(TAG, "saveToCache()")

        if (bmp == null)
            makeBmp(context) {
                if (bmp != null)
                    saveImageToInternalStorage(context, bmp!!)
            }
        else
            saveImageToInternalStorage(context, bmp!!)
    }

    /**
     * Downloads picture from [url] field to [bmp] field.
     * Also sends the downloaded bmp with [onFinish].
     *
     * @param context
     * @param onFinish callback with nullable [bmp]
     */
    fun makeBmp(context: Context, onFinish: (bmp: Bitmap?) -> Unit) {
        Log.v(TAG, "makeBmp()")

        val target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                Log.v(TAG, "onPrepareLoad")
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
                Log.w(TAG, "onBitmapFailed ${errorDrawable.toString()}")
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                Log.v(TAG, "onBitmapLoaded")

                bmp = bitmap
                onFinish(bitmap)
            }
        }

        // Used to trick garbage collector
        protectedFromGarbageCollectorTargets.add(target)

        Picasso.with(context)
                .load(url)
                .into(target)
    }

    /**
     * Saves image to internal storage.
     * Filename format: ownerId_photoId.jpg
     * @param context [Context]
     * @param image [Bitmap]
     */
    private fun saveImageToInternalStorage(context: Context, image: Bitmap): Boolean {
        Log.v(TAG, "saveImageToIntStorage")

        try {
            val fos = context.openFileOutput("${owner_id}_${photo_id}.jpg", Context.MODE_PRIVATE)

            image.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()

            return true
        } catch (e: Exception) {
            Log.e(TAG, "saveImageToIntStorage error: ${e.message}")
            return false
        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(photo_id)
        parcel.writeString(owner_id)
        parcel.writeString(album_id)
        parcel.writeParcelable(bmp, flags)
    }

    override fun describeContents(): Int {
        return 0
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
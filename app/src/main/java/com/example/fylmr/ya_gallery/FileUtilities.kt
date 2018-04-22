package com.example.fylmr.ya_gallery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.FileInputStream


class FileUtilities {
    val TAG = "FileUtilities"

    /**
     * Get picture from internal storage.
     *
     * @param context [Context]
     * @param filename
     */
    fun getThumbnailFromInternalStorage(context: Context, filename: String): Bitmap? {

        var thumbnail: Bitmap? = null

        try {
            val filePath = context.getFileStreamPath(filename)
            val fi = FileInputStream(filePath)

            thumbnail = BitmapFactory.decodeStream(fi)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }


        return thumbnail
    }
}
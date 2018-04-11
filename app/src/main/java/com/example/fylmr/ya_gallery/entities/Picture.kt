package com.example.fylmr.ya_gallery.entities

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

class Picture() : Parcelable {

    var url: String? = null

    var photo_id: String? = null
    var owner_id: String? = null

    var bmp: Bitmap? = null

    constructor(parcel: Parcel) : this() {
        url = parcel.readString()
        photo_id = parcel.readString()
        owner_id = parcel.readString()
        bmp = parcel.readParcelable(Bitmap::class.java.classLoader)
    }


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        if (dest != null) {
            dest.writeString(url)

            dest.writeString(photo_id)
            dest.writeString(owner_id)

            dest.writeParcelable(bmp, 1)
        }
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
package com.example.fylmr.ya_gallery.models

import android.util.Log
import com.example.fylmr.ya_gallery.Constants
import com.example.fylmr.ya_gallery.entities.Picture
import com.vk.sdk.api.*


class VKPicsModel {
    val TAG = "VKPicsModel"

    /**
     * Get first 20 user pictures
     * @param onFinish Callback returning the userPhotos mutable list
     * @param onError Callback returning error description string
     */
    fun getFirstCurrentUserPictures(onFinish: (userPhotos: MutableList<Picture>) -> Unit, onError: (error: String) -> Unit) {
        Log.v(TAG, "getFirstCurrentUserPictures")

        getCurrentUserPictures(null, null,
                { userPhotos ->
                    onFinish(userPhotos)
                },
                { error ->
                    onError(error)
                })
    }

    /**
     *This method requests only one picture with selected params and calls
     * [onFinish] or [onError] after.
     *
     * The reason why these three params are passed instead of just one [Picture] instance
     * is that the instance sometimes has [Bitmap] inside itself, which transfer may cost
     * too much resources
     * @param photoId is photo ID
     * @param ownerId is photo's owner ID
     * @param albumId is photo's album ID
     */
    fun getHighResPictureByID(photoId: String, ownerId: String, albumId: String,
                              onFinish: (pic: Picture) -> Unit, onError: (error: String) -> Unit) {
        Log.v(TAG, "getHighResPictureByID")

        val picture = Picture()
        picture.photo_id = photoId
        picture.owner_id = ownerId

        val request = VKRequest(Constants.VKMethods.PHOTOS_GET_BY_ID,
                VKParameters.from(
                        VKApiConst.PHOTOS, "${ownerId}_${photoId}",
                        VKApiConst.EXTENDED, 0,
                        VKApiConst.PHOTO_SIZES, 0
                ))
        Log.v(TAG, "Request: ${request}")

        request.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                if (response != null) {
                    Log.v(TAG, "Response: ${response.responseString}")

                    val responsePhoto = responseToSingleHighResPhoto(picture, response)
                    onFinish(responsePhoto)
                }
            }

            override fun onError(error: VKError?) {
                Log.e(TAG, error.toString())

                onError(error.toString())
            }
        })
    }

    /**
     * Transforms [VKResponse] into [Picture] with the best available picture url.
     *
     * @param picture [Picture] instance with some previously set fields.
     * @param response [VKResponse] with the JSON inside.
     */
    private fun responseToSingleHighResPhoto(picture: Picture, response: VKResponse): Picture {
        Log.v(TAG, "responseToSingleHighResPhoto")

        val picture = picture

        val responseItem = response.json
                .getJSONArray("response")
                .getJSONObject(0)

        Log.v(TAG, "responseItem: ${responseItem.toString(4)}")

        // Looking for the largest available picture size
        var currentHighResURL = ""
        for (size in Constants.VKFields.PHOTO_SIZES.reversed()) {
            if (responseItem.has(size)) {
                currentHighResURL = responseItem.getString(size)
                break
            }
        }
        picture.url = currentHighResURL


        return picture

    }

    /**
     * Get logged in user pictures.
     *
     * @param count Amount of pictures to return. Default: 20
     * @param offset Skip this much pictures. Default: 0
     * @param onFinish Callback returning the userPhotos mutable list
     * @param onError Callback returning error description string
     */
    fun getCurrentUserPictures(count: Int?, offset: Int?,
                               onFinish: (userPhotos: MutableList<Picture>) -> Unit, onError: (error: String) -> Unit) {
        Log.v(TAG, "getCurrentUserPictures")


        val request = VKRequest(Constants.VKMethods.PHOTOS_GET_ALL,
                VKParameters.from(
                        VKApiConst.COUNT, count ?: 20,
                        VKApiConst.OFFSET, offset ?: 0))

        Log.v(TAG, "Request: ${request.toString()}")

        request.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                if (!(response == null)) {
                    Log.v(TAG, "Response: ${response.responseString}")

                    val userPhotos = responseToPhotosList(response)

                    onFinish(userPhotos)
                }

            }

            override fun attemptFailed(request: VKRequest?, attemptNumber: Int, totalAttempts: Int) {
                onError("Failed after $attemptNumber attempts")
            }

            override fun onError(error: VKError?) {
                onError(error.toString())
            }
        })

    }

    /**
     * Transforms [VKResponse] to mutable list of [Picture]s.
     *
     * @param response VKResponse from server
     */
    private fun responseToPhotosList(response: VKResponse): MutableList<Picture> {
        Log.v(TAG, "responseToPhotosList")

        val userPhotos = mutableListOf<Picture>()

        val responseItems = response.json
                .getJSONObject("response")
                .getJSONArray("items")
        Log.v(TAG, "responseItems: ${responseItems.toString(4)}")

        for (i in 0 until responseItems.length()) {
            val responseItem = responseItems.getJSONObject(i)
            val picture = Picture()

            picture.photo_id = responseItem.getLong(Constants.VKFields.ID).toString()
            picture.owner_id = responseItem.getLong(Constants.VKFields.OWNER_ID).toString()
            picture.album_id = responseItem.getLong(Constants.VKFields.ALBUM_ID).toString()
            picture.url = responseItem.getString(Constants.VKFields.PHOTO_604)

            userPhotos.add(picture)
        }

        Log.v(TAG, "Photos list size: ${userPhotos.size}")

        return userPhotos
    }


}
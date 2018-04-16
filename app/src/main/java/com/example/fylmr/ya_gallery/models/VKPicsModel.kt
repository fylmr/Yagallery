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

    fun getHighResPictureByID(photoId: String) {

    }

    /**
     * Get logged in user pictures
     * @param count Amount of pictures to return
     * @param offset Skip this much pictures
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
     * Transforms [VKResponse] to mutable list of [Picture]s
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
            picture.url = responseItem.getString(Constants.VKFields.PHOTO_604)

            userPhotos.add(picture)
        }

        Log.v(TAG, "Photos list size: ${userPhotos.size}")

        return userPhotos
    }


}
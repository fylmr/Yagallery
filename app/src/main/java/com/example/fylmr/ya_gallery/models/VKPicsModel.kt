package com.example.fylmr.ya_gallery.models

import com.example.fylmr.ya_gallery.Constants
import com.example.fylmr.ya_gallery.entities.Picture
import com.vk.sdk.api.*


class VKPicsModel {
    fun getAllCurrentUserPictures(onFinish: (userPhotos: MutableList<Picture>) -> Unit, onError: (error: String) -> Unit) {
        getCurrentUserPictures(null, null,
                { userPhotos ->
                    onFinish(userPhotos)
                },
                { error ->
                    onError(error)
                })
    }

    fun getCurrentUserPictures(count: Int?, offset: Int?,
                               onFinish: (userPhotos: MutableList<Picture>) -> Unit, onError: (error: String) -> Unit) {

        val request = VKRequest(Constants.VKMethods.PHOTOS_GET_ALL,
                VKParameters.from(
                        VKApiConst.COUNT, count ?: "",
                        VKApiConst.OFFSET, offset ?: ""))

        request.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                if (!(response == null)) {
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

    private fun responseToPhotosList(response: VKResponse): MutableList<Picture> {
        val userPhotos = mutableListOf<Picture>()

        val responseItems = response.json
                .getJSONObject("response")
                .getJSONArray("items")

        for (i in 0 until responseItems.length()) {
            val responseItem = responseItems.getJSONObject(i)
            val picture = Picture()

            picture.photo_id = responseItem.getLong(Constants.VKFields.ID).toString()
            picture.owner_id = responseItem.getLong(Constants.VKFields.OWNER_ID).toString()
            picture.url = responseItem.getString(Constants.VKFields.PHOTO_130)

            userPhotos.add(picture)
        }

        return userPhotos
    }
}
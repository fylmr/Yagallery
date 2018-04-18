package com.example.fylmr.ya_gallery

class Constants {

    class VKMethods {
        companion object {
            const val PHOTOS_GET_ALL = "photos.getAll"
            const val PHOTOS_GET_BY_ID = "photos.getById"
        }


    }

    class VKFields {
        companion object {
            const val ID = "id"
            const val OWNER_ID = "owner_id"
            const val ALBUM_ID = "album_id"

            const val PHOTO_75 = "photo_75"
            const val PHOTO_130 = "photo_130"
            const val PHOTO_604 = "photo_604"
            const val PHOTO_807 = "photo_807"
            const val PHOTO_1280 = "photo_1280"
            const val PHOTO_2560 = "photo_2560"
            val PHOTO_SIZES = listOf<String>(
                    PHOTO_75,
                    PHOTO_130,
                    PHOTO_604,
                    PHOTO_807,
                    PHOTO_1280,
                    PHOTO_2560
            )
        }
    }

    class RequestCodes {
        companion object {
            const val OPEN_PHOTO_FULL_SCREEN = 101
        }
    }

    class ExtrasNames {
        companion object {
            const val PICTURE = "picture"
        }
    }
}
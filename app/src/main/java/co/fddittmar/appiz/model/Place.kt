package co.fddittmar.appiz.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Fernnando on 14/04/2018.
 */
data class Place(
        @SerializedName("name") val name: String,
        @SerializedName("height") val height: String
)


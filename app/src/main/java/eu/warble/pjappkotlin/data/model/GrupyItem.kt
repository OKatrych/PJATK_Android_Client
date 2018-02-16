package eu.warble.pjappkotlin.data.model

import com.google.gson.annotations.SerializedName

data class GrupyItem(
        @SerializedName("Nazwa")
        val nazwa: String?
)
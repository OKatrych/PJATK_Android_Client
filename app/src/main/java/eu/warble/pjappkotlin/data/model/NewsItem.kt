package eu.warble.pjappkotlin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class NewsItem(
        @SerializedName("id")
        @Expose
        val id: Int?,

        @SerializedName("title")
        @Expose
        val title: String?,

        @SerializedName("url")
        @Expose
        val url: String?,

        @SerializedName("published_at")
        @Expose
        val date: String?,

        @SerializedName("content")
        @Expose
        val content: String?
)
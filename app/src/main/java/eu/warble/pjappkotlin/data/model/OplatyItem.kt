package eu.warble.pjappkotlin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class OplatyItem(
        @SerializedName("TerminPlatnosci")
        @Expose
        val terminPlatnosci: String?,

        @SerializedName("LiczbaRat")
        @Expose
        val liczbaRat: Int?,

        @SerializedName("Kwota")
        @Expose
        val kwota: Double?,

        @SerializedName("Nazwa")
        @Expose
        val nazwa: String?,

        @SerializedName("NrRaty")
        @Expose
        val nrRaty: Int?
)
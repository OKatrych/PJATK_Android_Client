package eu.warble.pjappkotlin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ZajeciaItem(
        @SerializedName("Budynek")
        @Expose
        val budynek: String?,

        @SerializedName("Kod")
        @Expose
        val kod: String?,

        @SerializedName("Nazwa_sali")
        @Expose
        val nazwaSali: String?,

        @SerializedName("TypZajec")
        @Expose
        val typZajec: String?,

        @SerializedName("Nazwa")
        @Expose
        val nazwa: String?,

        @SerializedName("Data_roz")
        @Expose
        val dataRoz: String?,

        @SerializedName("idRealizacja_zajec")
        @Expose
        val idRealizacjaZajec: Int?,

        @SerializedName("Data_zak")
        @Expose
        val dataZak: String?
)
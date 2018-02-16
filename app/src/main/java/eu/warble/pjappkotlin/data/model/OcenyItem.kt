package eu.warble.pjappkotlin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OcenyItem(
        @SerializedName("NazwaPrzedmiotuAng")
        @Expose
        val nazwaPrzedmiotuAng: String?,

        @SerializedName("Ocena")
        @Expose
        val ocena: String?,

        @SerializedName("Zaliczenie")
        @Expose
        val zaliczenie: String?,

        @SerializedName("NazwaPrzedmiotu")
        @Expose
        val nazwaPrzedmiotu: String?,

        @SerializedName("LiczbaGodzin")
        @Expose
        val liczbaGodzin: Int?,

        @SerializedName("ZaliczenieAng")
        @Expose
        val zaliczenieAng: String?,

        @SerializedName("Semestr")
        @Expose
        val semestr: String?,

        @SerializedName("Data")
        @Expose
        val data: String?,

        @SerializedName("KodPrzedmiotu")
        @Expose
        val kodPrzedmiotu: String?,

        @SerializedName("Prowadzacy")
        @Expose
        val prowadzacy: String?
)
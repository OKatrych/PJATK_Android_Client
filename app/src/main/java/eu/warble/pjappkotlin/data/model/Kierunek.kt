package eu.warble.pjappkotlin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Kierunek(
        @SerializedName("Studia")
        @Expose
        val studia: Any?,

        @SerializedName("NazwaWydzial")
        @Expose
        val nazwaWydzial: String?,

        @SerializedName("IdWydzial")
        @Expose
        val idWydzial: Int?,

        @SerializedName("Specjalizacja")
        @Expose
        val specjalizacja: Any?,

        @SerializedName("IdKierunek")
        @Expose
        val idKierunek: Int?,

        @SerializedName("NazwaKierunek")
        @Expose
        val nazwaKierunek: String?,

        @SerializedName("NazwaKierunekAng")
        @Expose
        val nazwaKierunekAng: String?,

        @SerializedName("NazwaWydzialAng")
        @Expose
        val nazwaWydzialAng: String?
)
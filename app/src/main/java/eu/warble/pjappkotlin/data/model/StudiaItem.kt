package eu.warble.pjappkotlin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StudiaItem(
        @SerializedName("RokStudiow")
        @Expose
        val rokStudiow: Int?,

        @SerializedName("Kierunek")
        @Expose
        val kierunek: Kierunek?,

        @SerializedName("Nazwa")
        @Expose
        val nazwa: String?,

        @SerializedName("SredniaSem")
        @Expose
        val sredniaSem: Double?,

        @SerializedName("Oceny")
        @Expose
        val oceny: List<OcenyItem>?,

        @SerializedName("Specjalizacja")
        @Expose
        val specjalizacja: String?,

        @SerializedName("SredniaStudia")
        @Expose
        val sredniaStudia: Double?,

        @SerializedName("IdWpisNaSemestr")
        @Expose
        val idWpisNaSemestr: Int?,

        @SerializedName("SemestrStudiow")
        @Expose
        val semestrStudiow: Int?,

        @SerializedName("SredniaRok")
        @Expose
        val sredniaRok: Double?
)
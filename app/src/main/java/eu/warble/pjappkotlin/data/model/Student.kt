package eu.warble.pjappkotlin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Student(
        @SerializedName("Status")
        @Expose
        val status: String?,

        @SerializedName("Imie")
        @Expose
        val imie: String?,

        @SerializedName("Kwota_wplat")
        @Expose
        val kwotaWplat: Double?,

        @SerializedName("Kwota_wyplat")
        @Expose
        val kwotaWyplat: Double?,

        @SerializedName("Zajecia")
        @Expose

        val zajecia: List<ZajeciaItem>?,

        @SerializedName("Login")
        @Expose

        val login: String?,

        @SerializedName("Grupy")
        @Expose

        val grupy: List<GrupyItem>?,

        @SerializedName("Konto_wyplat")
        @Expose

        val kontoWyplat: String?,

        @SerializedName("Kwota_naleznosci")
        @Expose

        val kwotaNaleznosci: Double?,

        @SerializedName("Oplaty")
        @Expose

        val oplaty: List<OplatyItem>?,

        @SerializedName("Studia")
        @Expose

        val studia: List<StudiaItem>?,

        @SerializedName("Konto_wplat")
        @Expose

        val kontoWplat: String?,

        @SerializedName("Saldo")
        @Expose

        val saldo: Double?,

        @SerializedName("Nazwisko")
        @Expose

        val nazwisko: String?,

        @SerializedName("Oceny")
        @Expose

        val oceny: List<OcenyItem>?,

        @SerializedName("Platnosci")
        @Expose

        val platnosci: List<PlatnosciItem>?,

        @SerializedName("Wyplaty")
        @Expose

        val wyplaty: List<Any>?
)
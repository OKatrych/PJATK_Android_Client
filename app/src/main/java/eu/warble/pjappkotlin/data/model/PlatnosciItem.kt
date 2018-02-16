package eu.warble.pjappkotlin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PlatnosciItem(
        @SerializedName("Wplacajacy")
        @Expose
        val wplacajacy: String?,

        @SerializedName("Kwota")
        @Expose
        val kwota: Double?,

        @SerializedName("TytulWplaty")
        @Expose
        val tytulWplaty: String?,

        @SerializedName("DataWplaty")
        @Expose
        val dataWplaty: String?
)
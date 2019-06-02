package eu.warble.pjappkotlin.utils

import eu.warble.pjappkotlin.data.model.Student
import android.text.Html
import android.os.Build
import android.text.Spanned

object Tools {
    fun checkApiResponseForErrors(student: Student): Boolean {
        return !(student.imie == null || student.nazwisko == null) &&
                !(student.studia == null || student.zajecia == null)
    }

    @Suppress("DEPRECATION")
    fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }
}
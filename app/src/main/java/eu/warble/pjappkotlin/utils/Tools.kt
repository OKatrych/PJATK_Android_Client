package eu.warble.pjappkotlin.utils

import eu.warble.pjappkotlin.data.model.Student

object Tools {
    fun checkApiResponseForErrors(student: Student): Boolean {
        return !(student.imie == null || student.nazwisko == null) &&
                !(student.studia == null || student.zajecia == null)
    }
}
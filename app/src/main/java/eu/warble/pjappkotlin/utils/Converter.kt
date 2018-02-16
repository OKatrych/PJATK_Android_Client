package eu.warble.pjappkotlin.utils

import com.google.gson.Gson
import eu.warble.pjappkotlin.data.model.Student
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object Converter {

    @Throws(IOException::class)
    fun responseToJsonString(body: ResponseBody): String {
        val builder = StringBuilder()
        BufferedReader(InputStreamReader(body.byteStream())).use { br ->
            var tmp: String? = br.readLine()
            while (tmp != null) {
                builder.append(tmp)
                tmp = br.readLine()
            }
        }
        return builder.toString()
    }

    fun jsonStringToStudentData(json: String?): Student? {
        if (json != null){
            return Gson().fromJson(json, Student::class.java)
        }
        return null
    }

    fun studentDataToJsonString(studentData: Student?): String? {
        return if (studentData != null) Gson().toJson(studentData) else null
    }
}
package eu.warble.pjappkotlin.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eu.warble.pjappkotlin.data.model.NewsItem
import eu.warble.pjappkotlin.data.model.Student
import eu.warble.pjappkotlin.data.model.ZajeciaItem
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object Converter {

    @Throws(IOException::class)
    fun responseToString(body: ResponseBody): String {
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

    fun jsonStringToScheduleList(json: String?): List<ZajeciaItem>? {
        if (json != null) {
            val listType = object : TypeToken<List<ZajeciaItem>>() {}.type
            return Gson().fromJson(json, listType)
        }
        return null
    }

    fun jsonStringToNewsList(json: String?): ArrayList<NewsItem>? {
        if (json != null) {
            val listType = object : TypeToken<ArrayList<NewsItem>>() {}.type
            return Gson().fromJson(json, listType)
        }
        return null
    }

    fun scheduleListToJsonString(data: List<ZajeciaItem>?): String? {
        return if (data != null) Gson().toJson(data) else null
    }

    fun jsonStringToStudentData(json: String?): Student? {
        if (json != null) {
            return Gson().fromJson(json, Student::class.java)
        }
        return null
    }

    fun studentDataToJsonString(studentData: Student?): String? {
        return if (studentData != null) Gson().toJson(studentData) else null
    }
}
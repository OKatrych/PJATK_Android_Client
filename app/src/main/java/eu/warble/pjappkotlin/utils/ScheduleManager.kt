package eu.warble.pjappkotlin.utils

import eu.warble.pjappkotlin.data.model.ZajeciaItem
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjusters


class ScheduleManager(private val scheduleData: List<ZajeciaItem>) {

    private lateinit var lessonsMap: Map<LocalDate, ArrayList<ZajeciaItem>>

    init {
        initLessonsSortedMap()
    }

    private fun initLessonsSortedMap() {
        lessonsMap = HashMap()
        for (zajecie in scheduleData) {
            val date = stringToLocalDate(zajecie.dataRoz)
            if (!lessonsMap.containsKey(date)) {
                val list = ArrayList<ZajeciaItem>(8)
                list.add(zajecie)
                (lessonsMap as HashMap<LocalDate, ArrayList<ZajeciaItem>>).put(date, list)
            } else {
                lessonsMap[date]?.add(zajecie)
            }
        }
    }

    fun getLessonsForDate(date: LocalDate): List<ZajeciaItem> {
        return lessonsMap[date] ?: return emptyList()
    }

    private fun stringToLocalDate(s: String?): LocalDate {
        return LocalDate.parse(s?.substring(0, 10))
    }

}
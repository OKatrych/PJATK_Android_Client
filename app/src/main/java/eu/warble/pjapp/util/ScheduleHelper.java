package eu.warble.pjapp.util;


import android.support.annotation.NonNull;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.TemporalAdjusters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.data.model.ZajeciaItem;

import static java.util.Collections.EMPTY_LIST;

public class ScheduleHelper {
    private List<ZajeciaItem> lessonsList;
    private Map<LocalDate, List<ZajeciaItem>> lessonsMap;
    private LocalDate firstDay;
    private LocalDate lastDay;

    public ScheduleHelper(Student studentData){
        firstDay = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        lastDay = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        lessonsList = studentData.getZajecia();
        initLessonsSortedMap();
    }

    public List<ZajeciaItem> getLessonsForDate(LocalDate date){
        List<ZajeciaItem> result = lessonsMap.get(date);
        if (result == null)
            return EMPTY_LIST;
        return result;
    }

    private void initLessonsSortedMap(){
        lessonsMap = new HashMap<>();
        for (ZajeciaItem zajecie : lessonsList) {
            LocalDate date = stringToLocalDate(zajecie.getDataRoz());
            if (!lessonsMap.containsKey(date)) {
                ArrayList<ZajeciaItem> list = new ArrayList<>(8);
                list.add(zajecie);
                lessonsMap.put(date, list);
            }else {
                lessonsMap.get(date).add(zajecie);
            }
        }
    }

    private LocalDate stringToLocalDate(@NonNull String s){
        return LocalDate.parse(s.substring(0, 10));
    }

    public LocalDate getFirstDayDate(){
        return LocalDate.parse(lessonsList.get(0).getDataRoz().substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public LocalDate getLastDayDate(){
        return LocalDate.parse(lessonsList.get(lessonsList.size()-1).getDataRoz().substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public boolean existsDay(LocalDate date){
        return !getLessonsForDate(date).isEmpty();
    }

    public LocalDate getFirstDayByDate(){
        //if monday
        if (LocalDate.now().getDayOfWeek().getValue() == 1)
            return LocalDate.now();
        return firstDay;
    }

    public LocalDate getLastDayByDate(){
        //if sunday
        if (LocalDate.now().getDayOfWeek().getValue() == 7)
            return LocalDate.now();
        return lastDay;
    }

    public LocalDate now(){
        return LocalDate.now();
    }
}

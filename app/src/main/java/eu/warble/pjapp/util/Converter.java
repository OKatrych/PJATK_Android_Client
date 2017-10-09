package eu.warble.pjapp.util;


import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import eu.warble.pjapp.data.model.Student;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Converter {

    public static String responseToJsonString(@NonNull ResponseBody body) throws IOException{
        StringBuilder builder = new StringBuilder();
        String tmp;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(body.byteStream()))) {
            while ((tmp = br.readLine()) != null)
                builder.append(tmp);
        }
        return builder.toString();
    }

    public static Student jsonStringToStudentData(String json){
        if (json != null)
            return new Gson().fromJson(json, Student.class);
        return null;
    }

    public static String studentDataToJsonString(Student studentData){
        if (studentData != null)
            return new Gson().toJson(studentData);
        return null;
    }

}

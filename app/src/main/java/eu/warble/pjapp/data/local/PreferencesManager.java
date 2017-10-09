package eu.warble.pjapp.data.local;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PreferencesManager {

    private static final String PJAPP_PREFS = "Pjapp-prefs";

    @Nullable
    public static String getLogin(@NonNull Context context){
        SharedPreferences preferences = context.getSharedPreferences(PJAPP_PREFS, Activity.MODE_PRIVATE);
        return preferences.getString("login", null);
    }

    @Nullable
    public static String getPassword(@NonNull Context context){
        SharedPreferences preferences = context.getSharedPreferences(PJAPP_PREFS, Activity.MODE_PRIVATE);
        return preferences.getString("password", null);
    }

    public static String getStudentData(@NonNull Context context){
        SharedPreferences preferences = context.getSharedPreferences(PJAPP_PREFS, Activity.MODE_PRIVATE);
        return preferences.getString("student_data", null);
    }

    public static void saveCredentials(@Nullable String login, @Nullable String password, @NonNull Context context){
        SharedPreferences preferences = context.getSharedPreferences(PJAPP_PREFS, Activity.MODE_PRIVATE);
        preferences.edit()
                .putString("login", login)
                .putString("password", password)
                .apply();
    }

    public static void saveStudentData(@NonNull Context context, String student_data){
        SharedPreferences preferences = context.getSharedPreferences(PJAPP_PREFS, Activity.MODE_PRIVATE);
        preferences.edit()
                .putString("student_data", student_data)
                .apply();
    }
}

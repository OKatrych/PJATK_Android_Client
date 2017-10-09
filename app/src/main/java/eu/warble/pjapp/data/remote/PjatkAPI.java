package eu.warble.pjapp.data.remote;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import eu.warble.pjapp.data.StudentDataSource;
import eu.warble.pjapp.data.model.Student;
import eu.warble.pjapp.util.AppExecutors;
import eu.warble.pjapp.util.Constants;
import eu.warble.pjapp.util.Converter;
import eu.warble.pjapp.util.CredentialsManager.Credentials;
import eu.warble.pjapp.util.NTLMAuthenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PjatkAPI implements StudentDataSource{

    private static PjatkAPI INSTANCE;
    private final OkHttpClient httpClient;
    private final AppExecutors appExecutors;
    private static final String STUDENT_PERSONAL_DATA_JSON;
    private static final String STUDENT_SCHEDULE;
    private static final String CHECK_CREDENTIALS;
    private static final int WRONG_CREDENTIALS = 401;
    private static final int OK = 200;

    static {
        STUDENT_PERSONAL_DATA_JSON = "https://ws.pjwstk.edu.pl/test/Service.svc/XmlService/GetStudentPersonalDataJson";
        STUDENT_SCHEDULE = "https://ws.pjwstk.edu.pl/test/Service.svc/XmlService/GetStudentSchedule";
        CHECK_CREDENTIALS = "https://ws.pjwstk.edu.pl/test/Service.svc/XmlService/tester";
    }

    // Prevent direct instantiation.
    private PjatkAPI(Credentials credentials, AppExecutors appExecutors){
        this.appExecutors = appExecutors;
        httpClient = new OkHttpClient.Builder()
                .authenticator(new NTLMAuthenticator(credentials.login, credentials.password))
                .connectTimeout(8, TimeUnit.SECONDS)
                .build();
    }

    public static PjatkAPI getInstance(Credentials credentials, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            INSTANCE = new PjatkAPI(credentials, appExecutors);
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

    public void getStudentData(LoadStudentDataCallback callback){
        if (httpClient == null){
            Log.e("PjatkApi", "httpClient is not initialized");
            callback.onDataNotAvailable(Constants.UNKNOWN_ERROR);
            return;
        }

        Request request = new Request.Builder().url(STUDENT_PERSONAL_DATA_JSON).build();
        appExecutors.networkIO().execute(() -> {
            try (Response response = httpClient.newCall(request).execute()){
                switch (response.code()) {
                    case WRONG_CREDENTIALS:
                        appExecutors.mainThread().execute(() -> callback.onDataNotAvailable(Constants.CREDENTIALS_ERROR));
                        break;
                    case OK:
                        ResponseBody body = response.body();
                        if (body == null)
                            appExecutors.mainThread().execute(() -> callback.onDataNotAvailable(Constants.CONNECTION_ERROR));
                        else {
                            Student studentData = Converter.jsonStringToStudentData(Converter.responseToJsonString(body));
                            appExecutors.mainThread().execute(() -> callback.onDataLoaded(studentData));
                        }
                        break;
                    default:
                        appExecutors.mainThread().execute(() -> callback.onDataNotAvailable(Constants.UNKNOWN_ERROR));
                        Log.e("PjatkApi", "Wrong response from PJATK server, response : " + response.toString());
                        break;
                }
            }catch (IOException ex){
                appExecutors.mainThread().execute(() -> callback.onDataNotAvailable(Constants.CONNECTION_ERROR));
            }
        });
    }
}

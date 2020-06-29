package com.example.attendanceapp2;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AttendanceApplication extends Application {

    public static final String APPLICATION_ID="7CDFD7E2-7409-5D9E-FF94-04A43D959200";
    public static final String API_KEY="921862C8-E53E-49B2-8234-552F3DC03E5B";
    public static final String SERVER_URL="https://api.backendless.com";

    public static BackendlessUser user;
    public static List<ClassOfStudent> classes;
    public static List<Student> classifiedStudents;
    public static List<Student> students;

    public static String date;

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(getApplicationContext(),APPLICATION_ID,API_KEY);
        Calendar c=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        date=dateFormat.format(c.getTime());
    }
}

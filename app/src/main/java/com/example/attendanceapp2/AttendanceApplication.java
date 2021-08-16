package com.example.attendanceapp2;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AttendanceApplication extends Application {

    public static final String APPLICATION_ID="2904AF0F-D97B-0913-FF51-5882C56A0600";
    public static final String API_KEY="E255D471-D2D5-4174-8EBE-45B0D8798F59";
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

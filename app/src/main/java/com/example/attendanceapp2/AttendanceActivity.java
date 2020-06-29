package com.example.attendanceapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {
    TextView  tvName,tvRollNo,tvPresents,tvAbsents;
    ListView lvListDay;
    List<Attendance> classifiedAttendance;
    AttendanceAdapter adapter;
    int presents=0;
    int absents=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        tvName=findViewById(R.id.tvName);
        tvRollNo=findViewById(R.id.tvRollNo);
        tvPresents=findViewById(R.id.tvPresents);
        tvAbsents=findViewById(R.id.tvAbsents);
        lvListDay=findViewById(R.id.lvListDay);

        tvName.setText(AttendanceApplication.user.getProperty("name").toString());
        tvRollNo.setText(AttendanceApplication.user.getProperty("rollNo").toString());

        classifiedAttendance=new ArrayList<>();
        DataQueryBuilder queryBuilder=DataQueryBuilder.create();
        queryBuilder.setPageSize(100);


        Backendless.Persistence.of(Attendance.class).find(queryBuilder, new AsyncCallback<List<Attendance>>() {
            @Override
            public void handleResponse(List<Attendance> response) {

                for (int i=0;i<response.size();i++)
                {
                    if(response.get(i).getClassName().equals(AttendanceApplication.user.getProperty("className").toString())
                            && response.get(i).getStudentName().equals(AttendanceApplication.user.getProperty("name").toString()))
                    {
                        classifiedAttendance.add(response.get(i));
                    }
                }
                for(int i=0;i<classifiedAttendance.size();i++)
                {
                    if(classifiedAttendance.get(i).getPresence().equals("Present"))
                    {
                        presents++;
                    }
                    else
                    {
                        absents++;
                    }
                }
                tvPresents.setText(presents+"");
                tvAbsents.setText(absents+"");
                adapter=new AttendanceAdapter(AttendanceActivity.this,classifiedAttendance);
                lvListDay.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(AttendanceActivity.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

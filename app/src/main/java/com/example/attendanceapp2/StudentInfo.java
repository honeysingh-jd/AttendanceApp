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


public class StudentInfo extends AppCompatActivity {


    TextView tvName,tvRollNo,tvPresents,tvAbsents;
    ListView lvListDay;
    List<Attendance> classifiedAttendance;
    AttendanceAdapter adapter;
    int presents,absents;

    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

         presents=0;
         absents=0;
         index=getIntent().getIntExtra("index",13);

         tvName=findViewById(R.id.tvName);
         tvRollNo=findViewById(R.id.tvRollNo);
         tvPresents=findViewById(R.id.tvPresents);
         tvAbsents=findViewById(R.id.tvAbsents);
         lvListDay=findViewById(R.id.lvListDay);

         tvName.setText(AttendanceApplication.classifiedStudents.get(index).getName());
         tvRollNo.setText(AttendanceApplication.classifiedStudents.get(index).getRollNo());
         tvPresents.setText(AttendanceApplication.classifiedStudents.get(index).getAttendance()+"");
         tvAbsents.setText(0+"");

         classifiedAttendance=new ArrayList<>();


        DataQueryBuilder queryBuilder=DataQueryBuilder.create();
        queryBuilder.setPageSize(100);
        queryBuilder.setSortBy("date");


        Backendless.Persistence.of(Attendance.class).find(queryBuilder, new AsyncCallback<List<Attendance>>() {
            @Override
            public void handleResponse(List<Attendance> response) {

                for (int i=0;i<response.size();i++)
                {
                    if(response.get(i).getClassName().equals(AttendanceApplication.classifiedStudents.get(index).getClassName())
                    && response.get(i).getStudentName().equals(AttendanceApplication.classifiedStudents.get(index).getName()))
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
                adapter=new AttendanceAdapter(StudentInfo.this,classifiedAttendance);
                lvListDay.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(StudentInfo.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });





    }
}

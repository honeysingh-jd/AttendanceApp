package com.example.attendanceapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity  {
    TextView tvClassName;
    String className;
    ListView lvListStudent;
    StudentAdapter adapter;

    List<Student> classifiedStudents;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        tvClassName=findViewById(R.id.tvClassName);

        lvListStudent=findViewById(R.id.lvListStudent);

        className=getIntent().getStringExtra("className");

        tvClassName.setText(className);

        String whereClause="userEmail='"+AttendanceApplication.user.getEmail()+"'";
        DataQueryBuilder queryBuilder=DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setGroupBy("created");
        queryBuilder.setPageSize(100);

        Backendless.Persistence.of(Student.class).find(queryBuilder, new AsyncCallback<List<Student>>() {
            @Override
            public void handleResponse(List<Student> response) {
                classifiedStudents=new ArrayList<>();
                for (int i=0;i<response.size();i++)
                {
                  if(response.get(i).getClassName().equals(className)) {

                      classifiedStudents.add(response.get(i));
                  }


                }

                adapter=new StudentAdapter(StudentList.this,classifiedStudents);
                lvListStudent.setAdapter(adapter);
                AttendanceApplication.classifiedStudents=classifiedStudents;
                AttendanceApplication.students=response;



            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(StudentList.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        lvListStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(StudentList.this,StudentInfo.class);
                intent.putExtra("index",position);
                startActivityForResult(intent,0);
            }
        });
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if(requestCode==0)
        {
            adapter.notifyDataSetChanged();
        }
    }
}

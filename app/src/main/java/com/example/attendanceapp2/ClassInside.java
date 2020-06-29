package com.example.attendanceapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class ClassInside extends AppCompatActivity {
    TextView tvClassName;
    Button btnList,btnAddStudent,btnAdd;
    EditText etName,etRollNo;
    boolean visibility=false;
    String className;
    Button btnTake;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_inside);

        tvClassName=findViewById(R.id.tvClassName);
        btnAddStudent=findViewById(R.id.btnAddStudent);
        btnAdd=findViewById(R.id.btnAdd);
        etName=findViewById(R.id.etName);
        etRollNo=findViewById(R.id.etRollNo);
        btnTake=findViewById(R.id.btnTake);

        className=getIntent().getStringExtra("className");
        tvClassName.setText(className);



        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibility=!visibility;
                if (visibility)
                {
                    btnAdd.setVisibility(View.VISIBLE);
                    etName.setVisibility(View.VISIBLE);
                    etRollNo.setVisibility(View.VISIBLE);
                }
                else
                {
                    btnAdd.setVisibility(View.GONE);
                    etName.setVisibility(View.GONE);
                    etRollNo.setVisibility(View.GONE);
                }

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().isEmpty() || etRollNo.getText().toString().isEmpty())
                {
                    Toast.makeText(ClassInside.this,"please enter all details",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    String name=etName.getText().toString().trim();
                    String rollNo=etRollNo.getText().toString().trim();
                    Student student=new Student();
                    student.setName(name);
                    student.setRollNo(rollNo);
                    student.setAttendance(0);
                    student.setPercentage(0.00);
                    student.setClassName(className);
                    student.setUserEmail(AttendanceApplication.user.getEmail());

                    Backendless.Persistence.save(student, new AsyncCallback<Student>() {
                        @Override
                        public void handleResponse(Student response) {
                            Toast.makeText(ClassInside.this,"New student added successfully!",Toast.LENGTH_SHORT).show();
                            etName.setText("");
                            etRollNo.setText("");

                            btnAdd.setVisibility(View.GONE);
                            etName.setVisibility(View.GONE);
                            etRollNo.setVisibility(View.GONE);
                            visibility=false;

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                            Toast.makeText(ClassInside.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(ClassInside.this,ScanningActivity.class);
              intent.putExtra("className",className);
              startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.listOfStudent)
        {
            Intent intent=new Intent(ClassInside.this,StudentList.class);
            intent.putExtra("className",className);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}

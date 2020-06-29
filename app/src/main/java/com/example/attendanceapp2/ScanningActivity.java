package com.example.attendanceapp2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.MetricAffectingSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class ScanningActivity extends AppCompatActivity {
    Button btnScan,btnSubmit;
    List<Student> presentStudents;
    String className;
    List<Student> list;
    List<Student> classifiedStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);
        list=new ArrayList<>();

        className=getIntent().getStringExtra("className");

        presentStudents=new ArrayList<>();

        btnScan=findViewById(R.id.btnScan);
        btnSubmit=findViewById(R.id.btnSubmit);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                    startActivityForResult(intent, 0);

                }
                catch (Exception e) {

                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);

                }

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog=new AlertDialog.Builder(ScanningActivity.this);
                dialog.setMessage("Warning! once you submit the attendance then all the students who are scanned yet will be marked as present" +
                        "and rest as absent" );
                dialog.setPositiveButton("final Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        classifiedStudents=new ArrayList<>();
                        for(int i=0;i<list.size();i++)
                        {
                            if(list.get(i).getClassName().equals(className))
                            {
                                classifiedStudents.add(list.get(i));
                            }
                        }
                        for(int i=0;i<presentStudents.size();i++)
                        {
                            for (int j = 0; j < classifiedStudents.size(); j++)
                            {
                                if (presentStudents.get(i) == classifiedStudents.get(j)) {
                                    classifiedStudents.remove(j);
                                }
                            }
                        }
                        for(int i=0;i<presentStudents.size();i++)
                        {
                            Attendance attendance=new Attendance();
                            attendance.setStudentName(presentStudents.get(i).getName());
                            attendance.setClassName(presentStudents.get(i).getClassName());
                            attendance.setRollNo(presentStudents.get(i).getRollNo());
                            attendance.setPresence("Present");
                            attendance.setDate(AttendanceApplication.date);

                            Backendless.Persistence.save(attendance, new AsyncCallback<Attendance>() {
                                @Override
                                public void handleResponse(Attendance response) {

                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(ScanningActivity.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        for(int i=0;i<classifiedStudents.size();i++)
                        {
                            Attendance attendance=new Attendance();
                            attendance.setStudentName(classifiedStudents.get(i).getName());
                            attendance.setClassName(classifiedStudents.get(i).getClassName());
                            attendance.setRollNo(classifiedStudents.get(i).getRollNo());
                            attendance.setPresence("Absent");
                            attendance.setDate(AttendanceApplication.date);

                            Backendless.Persistence.save(attendance, new AsyncCallback<Attendance>() {
                                @Override
                                public void handleResponse(Attendance response) {

                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(ScanningActivity.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        Toast.makeText(ScanningActivity.this,"Attendance saved successfully!",Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.setNegativeButton("back to scan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                 dialog.show();

            }
        });
        Backendless.Persistence.of(Student.class).find(new AsyncCallback<List<Student>>() {
            @Override
            public void handleResponse(List<Student> response) {
                list=response;
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(ScanningActivity.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                final String contents = data.getStringExtra("SCAN_RESULT").trim();
                if(!list.isEmpty())
                {
                    for (int i = 0; i < list.size(); i++)
                    {
                        if (list.get(i).getRollNo().equals(contents) && list.get(i).getClassName().equals(className)) {
                            presentStudents.add(list.get(i));
                        }
                    }
                }

            }
            if(resultCode == RESULT_CANCELED){

            }
        }

    }
}

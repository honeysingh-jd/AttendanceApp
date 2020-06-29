package com.example.attendanceapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.backendless.Backendless.Persistence;

public class HostActivity extends AppCompatActivity {
    Button btnCreateClass,btnAddClass;
    EditText etClassName;
    ListView lvListClass;
    ClassAdapter adapter;
    boolean show=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        btnCreateClass=findViewById(R.id.btnCreateClass);
        btnAddClass=findViewById(R.id.btnAddClass);
        etClassName=findViewById(R.id.etClassName);
        lvListClass=findViewById(R.id.lvListClass);

        btnCreateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show=!show;
                if(show)
                {
                    etClassName.setVisibility(View.VISIBLE);
                    btnAddClass.setVisibility(View.VISIBLE);
                }
                else {
                    etClassName.setVisibility(View.GONE);
                    btnAddClass.setVisibility(View.GONE);
                }
            }
        });

        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etClassName.getText().toString().isEmpty())
                {
                    Toast.makeText(HostActivity.this,"please enter the class name!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String className=etClassName.getText().toString().trim();

                    ClassOfStudent classOfStudent=new ClassOfStudent();
                    classOfStudent.setClassName(className);
                    classOfStudent.setUserEmail(AttendanceApplication.user.getEmail());

                    Backendless.Persistence.save(classOfStudent, new AsyncCallback<ClassOfStudent>() {
                        @Override
                        public void handleResponse(ClassOfStudent response) {
                            Toast.makeText(HostActivity.this,"New class saved successfully!",
                                    Toast.LENGTH_SHORT).show();

                            etClassName.setText("");
                            etClassName.setVisibility(View.GONE);
                            btnAddClass.setVisibility(View.GONE);
                            show=false;

                            String whereClause="userEmail='"+AttendanceApplication.user.getEmail()+"'";
                            DataQueryBuilder queryBuilder=DataQueryBuilder.create();
                            queryBuilder.setWhereClause(whereClause);
                            queryBuilder.setSortBy("created");
                            queryBuilder.setPageSize(100);


                            Backendless.Persistence.of(ClassOfStudent.class).find(queryBuilder,
                                    new AsyncCallback<List<ClassOfStudent>>() {
                                        @Override
                                        public void handleResponse(List<ClassOfStudent> response) {

                                            adapter=new ClassAdapter(HostActivity.this,response);
                                            lvListClass.setAdapter(adapter);
                                            AttendanceApplication.classes=response;
                                        }

                                        @Override
                                        public void handleFault(BackendlessFault fault) {
                                            Toast.makeText(HostActivity.this,"Error: "+fault.getMessage(),
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    });

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(HostActivity.this,"Error: "+fault.getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }
        });

        lvListClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(HostActivity.this,ClassInside.class);
                intent.putExtra("className",AttendanceApplication.classes.get(position).getClassName());
                startActivity(intent);
            }
        });

        String whereClause="userEmail='"+AttendanceApplication.user.getEmail()+"'";
        DataQueryBuilder queryBuilder=DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setSortBy("created");
        queryBuilder.setPageSize(100);


        Backendless.Persistence.of(ClassOfStudent.class).find(queryBuilder, new AsyncCallback<List<ClassOfStudent>>() {
            @Override
            public void handleResponse(List<ClassOfStudent> response) {
                adapter=new ClassAdapter(HostActivity.this,response);
                lvListClass.setAdapter(adapter);
                AttendanceApplication.classes=response;
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(HostActivity.this,"Error: "+fault.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.logout)
        {
            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void response) {
                    Toast.makeText(HostActivity.this,"Logged out successfully!",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    setResult(RESULT_OK,intent);
                    HostActivity.this.finish();
                }

                @Override
                public void handleFault(BackendlessFault fault)
                {
                    Toast.makeText(HostActivity.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

}

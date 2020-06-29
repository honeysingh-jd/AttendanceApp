package com.example.attendanceapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class RegisterInvitee extends AppCompatActivity {
    EditText etName,etRollNo,etMail,etPassword,etConfirm,etClassName;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_invitee);

        etName=(EditText)findViewById(R.id.etName);
        etRollNo=(EditText)findViewById(R.id.etRollNo);
        etMail=(EditText)findViewById(R.id.etMail);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etConfirm=(EditText)findViewById(R.id.etConfirm);
        btnRegister=(Button) findViewById(R.id.btnRegister);
        etClassName=findViewById(R.id.etClassName);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty() || etRollNo.getText().toString().isEmpty() ||
                        etMail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() ||
                        etConfirm.getText().toString().isEmpty() || etClassName.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterInvitee.this,"please enter all details",Toast.LENGTH_SHORT).show();
                }
                else if(!etPassword.getText().toString().equals(etConfirm.getText().toString()))
                {
                    Toast.makeText(RegisterInvitee.this,"password does not match",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String name=etName.getText().toString().trim();
                    String rollNo=etRollNo.getText().toString().trim();
                    String email=etMail.getText().toString().trim();
                    String password=etPassword.getText().toString().trim();
                    String className=etClassName.getText().toString().trim();

                    BackendlessUser user=new BackendlessUser();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setProperty("name",name);
                    user.setProperty("rollNo",rollNo);
                    user.setProperty("user_key","1");
                    user.setProperty("className",className);

                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(RegisterInvitee.this,"User successfully registerd!",Toast.LENGTH_SHORT).show();
                            RegisterInvitee.this.finish();

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                            Toast.makeText(RegisterInvitee.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}

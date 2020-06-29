package com.example.attendanceapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class RegisterHost extends AppCompatActivity {
    EditText etName,etMail,etPassword,etConfirm;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_host);

        etName=(EditText)findViewById(R.id.etName);
        etMail=(EditText)findViewById(R.id.etMail);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etConfirm=(EditText)findViewById(R.id.etConfirm);
        btnRegister=(Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty() || etMail.getText().toString().isEmpty() ||
                        etPassword.getText().toString().isEmpty() || etConfirm.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterHost.this,"please enter all fields!",Toast.LENGTH_SHORT).show();
                }
                else if (!etPassword.getText().toString().equals(etConfirm.getText().toString()))
                {
                    Toast.makeText(RegisterHost.this,"Password does not match",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String name=etName.getText().toString().trim();
                    String email=etMail.getText().toString().trim();
                    String password=etPassword.getText().toString().trim();

                    BackendlessUser user=new BackendlessUser();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setProperty("name",name);
                    user.setProperty("user_key","0");

                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(RegisterHost.this,"User successfully registered!",
                                    Toast.LENGTH_SHORT).show();
                            RegisterHost.this.finish();

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(RegisterHost.this,"Error: "+fault.getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}

package com.example.attendanceapp2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.backendless.persistence.local.UserIdStorageFactory;

public class Login extends AppCompatActivity {
    EditText etUsername,etPassword;
    Button btnLogin;
    TextView tvHost,tvInvitee;
    String user_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername= findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        tvHost=findViewById(R.id.tvHost);
        tvInvitee=findViewById(R.id.tvInvitee);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this,"please enter all fields!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String email=etUsername.getText().toString().trim();
                    String password=etPassword.getText().toString().trim();

                    Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            AttendanceApplication.user=response;
                            Toast.makeText(Login.this,"Logged in successfully!",Toast.LENGTH_SHORT).show();
                            user_key=response.getProperty("user_key").toString();
                            if(user_key.equals("0"))
                            {
                                startActivityForResult(new Intent(Login.this,HostActivity.class),0);
                            }
                            else
                            {
                                startActivityForResult(new Intent(Login.this,InviteeActivity.class),1);
                            }

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(Login.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    },true);
                }

            }
        });

        tvHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,RegisterHost.class));

            }
        });

        tvInvitee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,RegisterInvitee.class));

            }
        });

        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {
                if(response)
                {
                    String userObjectId= UserIdStorageFactory.instance().getStorage().get();
                    Backendless.Data.of(BackendlessUser.class).findById(userObjectId, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            AttendanceApplication.user=response;

                            user_key=response.getProperty("user_key").toString();
                            if(user_key.equals("0"))
                            {
                                startActivityForResult(new Intent(Login.this,HostActivity.class),0);
                            }
                            else
                            {
                                startActivityForResult(new Intent(Login.this,InviteeActivity.class),1);
                            }




                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(Login.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(Login.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0)
        {
            if(resultCode==RESULT_OK)
            {
                etUsername.setText("");
                etPassword.setText("");
            }
        }
        else
        {
            if(resultCode==RESULT_OK)
            {
                etUsername.setText("");
                etPassword.setText("");
            }
        }
    }
}

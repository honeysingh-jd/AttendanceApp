package com.example.attendanceapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


public class InviteeActivity extends AppCompatActivity {
    TextView tvName;
    ImageView ivIconCode;
    Button btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitee);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        tvName=findViewById(R.id.tvName);
        ivIconCode=findViewById(R.id.ivIconCode);
        btnList=findViewById(R.id.btnList);

        tvName.setText("hi, "+AttendanceApplication.user.getProperty("name").toString());

        ivIconCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(InviteeActivity.this,QRCodeGenerator.class));
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(InviteeActivity.this,AttendanceActivity.class));
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
                    Toast.makeText(InviteeActivity.this,"Logged out successfully!",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    setResult(RESULT_OK,intent);
                    InviteeActivity.this.finish();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(InviteeActivity.this,"Error: "+fault.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.attendanceapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodeGenerator extends AppCompatActivity {
    ImageView ivCode;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);

        ivCode=findViewById(R.id.ivCode);

        WindowManager manager=(WindowManager)getSystemService(WINDOW_SERVICE);
        Display display=manager.getDefaultDisplay();
        Point point =new Point();
        display.getSize(point);
        int width=point.x;
        int height=point.y;
        int smallerDimension=(width<height? width:height)*3/4;
        qrgEncoder=new QRGEncoder(AttendanceApplication.user.getProperty("rollNo").toString(),
                null, QRGContents.Type.TEXT,smallerDimension);
        try {
            bitmap= qrgEncoder.encodeAsBitmap();
            ivCode.setImageBitmap(bitmap);
        }
        catch (WriterException e)
        {
            Toast.makeText(QRCodeGenerator.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}

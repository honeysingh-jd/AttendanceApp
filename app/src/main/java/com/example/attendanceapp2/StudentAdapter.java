package com.example.attendanceapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    private List<Student> students;
    Context context;


    public StudentAdapter(Context context,List<Student> list) {

        super(context,R.layout.row_layout_student,list);
        this.students = list;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_layout_student,parent,false);

        TextView tvName=convertView.findViewById(R.id.tvName);
        TextView tvRollNo=convertView.findViewById(R.id.tvRollNo);

        tvName.setText(students.get(position).getName());
        tvRollNo.setText(students.get(position).getRollNo());

        return convertView;
    }
}

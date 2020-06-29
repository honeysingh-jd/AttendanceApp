package com.example.attendanceapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AttendanceAdapter extends ArrayAdapter<Attendance> {

    private List<Attendance> listOfAttendance;
    Context context;

    public AttendanceAdapter(Context context,List<Attendance> list)
    {
        super(context,R.layout.row_layout_attendance,list);
        this.context=context;
        this.listOfAttendance=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_layout_attendance,parent,false);

        TextView tvDate=convertView.findViewById(R.id.tvDate);
        TextView tvPresent=convertView.findViewById(R.id.tvPresent);

        tvDate.setText(listOfAttendance.get(position).getDate());
        tvPresent.setText(listOfAttendance.get(position).getPresence());

        return  convertView;
    }
}

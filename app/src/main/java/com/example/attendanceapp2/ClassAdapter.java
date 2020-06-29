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

public class ClassAdapter extends ArrayAdapter<ClassOfStudent> {
    private Context context;
    private List<ClassOfStudent> classes;

    public ClassAdapter(Context context,List<ClassOfStudent> list)
    {
        super(context,R.layout.row_layout_class,list);
        this.context=context;
        this.classes=list;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_layout_class,parent,false);

        TextView tvClass=convertView.findViewById(R.id.tvClass);

        tvClass.setText(classes.get(position).getClassName());

        return convertView;
    }
}

package com.example.attendanceapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {
    private Context context;
    private List<Person> persons;

    public PersonAdapter(Context context,List<Person> list)
    {
        super(context,R.layout.row_layout,list);
        this.context=context;
        this.persons=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_layout,parent,false);

        TextView tvName=convertView.findViewById(R.id.tvName);
        TextView tvSurname=convertView.findViewById(R.id.tvSurname);

        tvName.setText(persons.get(position).getName());
        tvSurname.setText(persons.get(position).getSurname());

        return convertView;
    }
}

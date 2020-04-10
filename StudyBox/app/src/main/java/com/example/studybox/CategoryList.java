package com.example.studybox;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryList extends ArrayAdapter {
    private Activity context;
    private String[] cc;
    private Integer[] ci;

    public CategoryList(Activity context, String[] cc,Integer[] ci) {
        super(context,R.layout.cclist, cc);
        this.context = context;
        this.cc = cc;
        this.ci = ci;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.cclist,null,true);
        TextView txtcc = rowView.findViewById(R.id.txtcc);
        ImageView txtcci = rowView.findViewById(R.id.txtcci);

        txtcc.setText(cc[position]);
        txtcci.setImageResource(ci[position]);

        return rowView;
    }
}

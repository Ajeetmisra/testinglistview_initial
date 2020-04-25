package com.example.testinglistview;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

public class DataAdapter extends ArrayAdapter<States> {


    public DataAdapter(Activity context, ArrayList<States> data)
        {
            super(context,0,data);
        }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View lisIemView = convertView;
        if (lisIemView == null)
        {
            lisIemView = LayoutInflater.from(getContext()).inflate(R.layout.layout,parent,false);
        }
        States curretnStateValue = getItem(position);
        TextView txt = (TextView) lisIemView.findViewById(R.id.cases);
        assert curretnStateValue != null;
        txt.setText(String.valueOf(curretnStateValue.getCases()));
        TextView txt2 =(TextView) lisIemView.findViewById(R.id.location);
        txt2.setText(curretnStateValue.getLoc());
        return lisIemView;
    }
}

package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.R;

import java.util.List;

import androidx.annotation.NonNull;

public class MySpinnerAdapter extends ArrayAdapter<String> {

    private Context context;

    public MySpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTypeface(MyApplication.getShpIranSansMoblie(getContext()));
        if (position == 0) {
            view.setTextColor(Color.GRAY);
        } else {
            view.setTextColor(Color.BLACK);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(MyApplication.getShpIranSansMoblie(getContext()));
        if (position == 0) {
            view.setTextColor(Color.GRAY);
        } else {
            view.setTextColor(Color.BLACK);
        }
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }
}
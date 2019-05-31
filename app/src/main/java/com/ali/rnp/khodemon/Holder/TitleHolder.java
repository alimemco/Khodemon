package com.ali.rnp.khodemon.Holder;

import android.view.View;

import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TitleHolder extends RecyclerView.ViewHolder {
        MyTextView titleTV;

        public TitleHolder( String title, @NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.recycler_view_title_detail_TV);

            titleTV.setText(title);
        }


    }
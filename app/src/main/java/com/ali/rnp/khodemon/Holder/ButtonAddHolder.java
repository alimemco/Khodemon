package com.ali.rnp.khodemon.Holder;

import android.view.View;


import com.ali.rnp.khodemon.Interface.OnButtonAddClick;
import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ButtonAddHolder extends RecyclerView.ViewHolder {
        MyButton addBtn;
        

        public ButtonAddHolder(String textBtn,@NonNull View itemView,int rcvModel,OnButtonAddClick onButtonAddClick) {
            super(itemView);
            addBtn = itemView.findViewById(R.id.recycler_view_add_button_btn);
            addBtn.setText(textBtn);
            addBtn.setOnClickListener(v -> {
                if (onButtonAddClick != null)
                    onButtonAddClick.OnAddClick(rcvModel);
            });

        }





    }

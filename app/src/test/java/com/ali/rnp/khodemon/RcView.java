package com.ali.rnp.khodemon;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public abstract class RcView {
    boolean isLarge = false;
    public abstract int getItemCount();
}

 abstract class  aliRc extends RcView{

     @Override
     public int getItemCount() {
         return isLarge ? 0 : 1;
     }
}



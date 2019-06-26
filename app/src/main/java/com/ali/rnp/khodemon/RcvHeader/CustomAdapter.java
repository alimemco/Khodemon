package com.ali.rnp.khodemon.RcvHeader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
 
    private static final int LAYOUT_HEADER= 0;
    private static final int LAYOUT_CHILD= 1;
 
   // private LayoutInflater inflater;
   // private Context context;
    private ArrayList<ListItem> listItemArrayList;
 
    public CustomAdapter(ArrayList<ListItem> listItemArrayList){
 
        //inflater = LayoutInflater.from(context);
       // this.context = context;
        this.listItemArrayList = listItemArrayList;
    }
 
    @Override
    public int getItemCount() {
        return listItemArrayList.size();
    }
 
    @Override
    public int getItemViewType(int position)
    {
        if(listItemArrayList.get(position).isHeader()) {
            return LAYOUT_HEADER;
        }else
            return LAYOUT_CHILD;
    }
 
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
 
        RecyclerView.ViewHolder holder;
        if(viewType==LAYOUT_HEADER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_header, parent, false);
            holder = new MyViewHolderHeader(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_child, parent, false);
             holder = new MyViewHolderChild(view);
        }
 
 
        return holder;
    }
 
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
 
        if(holder.getItemViewType()== LAYOUT_HEADER)
        {
            MyViewHolderHeader vaultItemHolder = (MyViewHolderHeader) holder;
            vaultItemHolder.tvHeader.setText(listItemArrayList.get(position).getName());
        }
        else {
 
            MyViewHolderChild vaultItemHolder = (MyViewHolderChild) holder;
            vaultItemHolder.tvChild.setText(listItemArrayList.get(position).getName());
 
        }
 
    }
 
 
    class MyViewHolderHeader extends RecyclerView.ViewHolder{
 
        MyTextView tvHeader;
 
         MyViewHolderHeader(View itemView) {
            super(itemView);
 
            tvHeader =  itemView.findViewById(R.id.tvHeader);
         }
 
    }
 
    class MyViewHolderChild extends RecyclerView.ViewHolder{
 
        MyTextView tvChild;
 
         MyViewHolderChild(View itemView) {
            super(itemView);
 
            tvChild =  itemView.findViewById(R.id.tvChild);
        }
 
    }
 
}
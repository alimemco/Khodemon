package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ali.rnp.khodemon.MyLibrary.MyButton;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectedDays extends RecyclerView.Adapter<SelectedDays.SelectedDaysHolder> {

    private Context context;
    private List<String> daysSelectedList;

    private static final String TAG = "SelectedDays";


    public SelectedDays(Context context){
        this.context = context;
        daysSelectedList = new ArrayList<>();

    }

    public void addItem (String item){
        daysSelectedList.add(0,item);
        notifyItemInserted(0);
    }

    public int removeItem(String item){
        int index = daysSelectedList.indexOf(item);
        if (index != -1){
            daysSelectedList.remove(index);
            notifyItemRemoved(index);
        }
        return index;
    }

    @NonNull
    @Override
    public SelectedDaysHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_selected_days,parent,false);
        return new SelectedDaysHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedDaysHolder holder, int position) {

        holder.buttonDay.setText(daysSelectedList.get(position));

    }

    @Override
    public int getItemCount() {
        return daysSelectedList.size();
    }

    public class SelectedDaysHolder extends RecyclerView.ViewHolder {
        private MyButton buttonDay;
        public SelectedDaysHolder(@NonNull View itemView) {
            super(itemView);
            buttonDay = itemView.findViewById(R.id.recyclerView_item_selected_days_button);
        }
    }
}

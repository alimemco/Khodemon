package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.IsSelected;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HoursAdapter  extends RecyclerView.Adapter<HoursAdapter.HoursViewHolder>{

    private Context context;
    private List<String> dayList;

    private String KEY_POSITION="KEY_POSITION";
    private String KEY_IS_OPEN="KEY_IS_OPEN";

    private String[] chooseOwnerModel = {"انتخاب کنید", "مالک", "فروشنده", "زیر مجموعه", "هیچکدام"};

    private List<IsSelected> isSelectedList;

    public HoursAdapter(Context context){
        this.context = context;
    }

    public void setData( List<String> dayList){
        this.dayList = dayList;
    }


    @NonNull
    @Override
    public HoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_hours,parent,false);

        isSelectedList = new ArrayList<>();

        for (int i = 0; i < dayList.size(); i++) {

            IsSelected isSelected = new IsSelected();

            isSelected.setOpened(false);

            isSelectedList.add(isSelected);

        }

        return new HoursViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoursViewHolder holder, int position) {

        holder.dayTextView.setText(dayList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isSelectedList.get(position).isOpened()){
                    holder.dayCheckBox.setChecked(true);
                    holder.hoursSpinnerFrom.setVisibility(View.VISIBLE);
                    isSelectedList.get(position).setOpened(true);
                }else {
                    holder.dayCheckBox.setChecked(false);
                    holder.hoursSpinnerFrom.setVisibility(View.GONE);
                    isSelectedList.get(position).setOpened(false);
                }



            }
        });

        holder.dayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    holder.hoursSpinnerFrom.setVisibility(View.VISIBLE);
                    holder.hoursSpinnerTo.setVisibility(View.VISIBLE);
                    isSelectedList.get(position).setOpened(true);
                } else {
                    holder.hoursSpinnerFrom.setVisibility(View.GONE);
                    holder.hoursSpinnerTo.setVisibility(View.GONE);
                    isSelectedList.get(position).setOpened(false);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public class HoursViewHolder extends RecyclerView.ViewHolder {
        private MyTextView dayTextView;
        private CheckBox dayCheckBox;
        private Spinner hoursSpinnerFrom;
        private Spinner hoursSpinnerTo;
        public HoursViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.recyclerView_item_hours_checkedTextView_day);
            dayCheckBox = itemView.findViewById(R.id.recyclerView_item_hours_checkBox_day);
            hoursSpinnerFrom = itemView.findViewById(R.id.recyclerView_item_hours_spinner_hours_from);
            hoursSpinnerTo = itemView.findViewById(R.id.recyclerView_item_hours_spinner_hours_to);

            hoursSpinnerFrom.setVisibility(View.GONE);
            hoursSpinnerTo.setVisibility(View.GONE);



            setupSpinner(hoursSpinnerFrom);
            setupSpinnerTo(hoursSpinnerTo);


        }
    }

    private void setupSpinnerTo(Spinner hoursSpinnerTo) {

        String[] hours = new String[]{
                "انتخاب کنید",
                "12:00",
                "12:30",
                "01:00",
                "01:30",
                "02:00",
                "02:30"
        };

        final List<String> plantsList = new ArrayList<>(Arrays.asList(hours));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                context,R.layout.item_spinner,plantsList){
            @Override
            public boolean isEnabled(int position){
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                MyTextView tv = (MyTextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        hoursSpinnerTo.setAdapter(spinnerArrayAdapter);

        hoursSpinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (context, "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void setupSpinner(Spinner hoursSpinnerFrom) {


        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, chooseOwnerModel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hoursSpinnerFrom.setAdapter(adapter);

        hoursSpinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chooseOwnerModel[0] = "One";
              //  selectedItem = items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}

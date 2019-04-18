package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.DataGenerator;
import com.ali.rnp.khodemon.DataModel.HourDays;
import com.ali.rnp.khodemon.DataModel.IsSelected;
import com.ali.rnp.khodemon.MyApplication;
import com.ali.rnp.khodemon.MyLibrary.MyTextView;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.HoursViewHolder> {

    private Context context;
    private List<String> dayList;
    private List<HourDays> hourDaysList;

    private static final String TAG = "HoursAdapter";

    private String KEY_POSITION = "KEY_POSITION";
    private String KEY_IS_OPEN = "KEY_IS_OPEN";


    private List<IsSelected> isSelectedList;

    public HoursAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> dayList) {
        this.dayList = dayList;
    }


    @NonNull
    @Override
    public HoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_hours, parent, false);

        isSelectedList = new ArrayList<>();
        hourDaysList = new ArrayList<>();

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

                if (!isSelectedList.get(position).isOpened()) {
                    holder.dayCheckBox.setChecked(true);
                    holder.hoursSpinnerFrom.setVisibility(View.VISIBLE);
                    holder.hour24CheckBox.setVisibility(View.VISIBLE);
                    isSelectedList.get(position).setOpened(true);
                    holder.closeTextView.setText(R.string.day_open);
                    holder.closeTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

                } else {
                    holder.dayCheckBox.setChecked(false);
                    holder.hoursSpinnerFrom.setVisibility(View.GONE);
                    holder.hour24CheckBox.setVisibility(View.GONE);
                    isSelectedList.get(position).setOpened(false);
                    holder.closeTextView.setText(R.string.day_close);
                    holder.closeTextView.setTextColor(ContextCompat.getColor(context, R.color.red300));
                }


            }
        });



        setupShiftSwitch(holder);
        setup24hourCheckBox(holder);
        setupDayCheckBox(holder,position);



    }


    private void setupDayCheckBox(HoursViewHolder holder, int position) {
        holder.dayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    holder.hoursSpinnerFrom.setVisibility(View.VISIBLE);
                    holder.hoursSpinnerTo.setVisibility(View.VISIBLE);
                    holder.shiftSwitch.setVisibility(View.VISIBLE);
                    holder.hour24CheckBox.setVisibility(View.VISIBLE);
                    holder.dashFirst.setVisibility(View.VISIBLE);

                    isSelectedList.get(position).setOpened(true);
                    holder.closeTextView.setText(R.string.day_open);
                    holder.closeTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

                } else {
                    holder.hoursSpinnerFrom.setVisibility(View.GONE);
                    holder.hoursSpinnerTo.setVisibility(View.GONE);
                    holder.shiftSwitch.setVisibility(View.GONE);
                    holder.dashFirst.setVisibility(View.GONE);
                    holder.hour24CheckBox.setVisibility(View.GONE);
                    holder.hour24CheckBox.setChecked(false);

                    isSelectedList.get(position).setOpened(false);
                    holder.closeTextView.setText(R.string.day_close);
                    holder.closeTextView.setTextColor(ContextCompat.getColor(context, R.color.red300));
                }
            }
        });
    }

    private void setup24hourCheckBox(HoursViewHolder holder) {
        holder.hour24CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    holder.hoursSpinnerTo.setVisibility(View.GONE);
                    holder.hoursSpinnerFrom.setVisibility(View.GONE);
                    holder.hoursSpinnerSecTo.setVisibility(View.GONE);
                    holder.hoursSpinnerSecFrom.setVisibility(View.GONE);
                    holder.shiftSwitch.setVisibility(View.GONE);
                    holder.dashFirst.setVisibility(View.GONE);
                    holder.dashSecond.setVisibility(View.GONE);

                    holder.closeTextView.setText(R.string.day_open);
                    holder.closeTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

                }else {
                    holder.hoursSpinnerTo.setVisibility(View.VISIBLE);
                    holder.hoursSpinnerFrom.setVisibility(View.VISIBLE);
                    holder.shiftSwitch.setVisibility(View.VISIBLE);
                    holder.dashFirst.setVisibility(View.VISIBLE);

                    holder.closeTextView.setText(R.string.day_close);
                    holder.closeTextView.setTextColor(ContextCompat.getColor(context, R.color.red300));
                    holder.shiftSwitch.setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }


    class HoursViewHolder extends RecyclerView.ViewHolder {
        private MyTextView dayTextView;
        private MyTextView closeTextView;
        private CheckBox dayCheckBox;
        private CheckBox hour24CheckBox;
        private Spinner hoursSpinnerFrom;
        private Spinner hoursSpinnerTo;
        private Switch shiftSwitch;
        private View dashSecond;
        private View dashFirst;
        private Spinner hoursSpinnerSecFrom;
        private Spinner hoursSpinnerSecTo;

        HoursViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.recyclerView_item_hours_checkedTextView_day);
            closeTextView = itemView.findViewById(R.id.recyclerView_item_hours_TextView_close);
            dayCheckBox = itemView.findViewById(R.id.recyclerView_item_hours_checkBox_day);
            hour24CheckBox = itemView.findViewById(R.id.recyclerView_item_hours_checkBox_24hour);
            hoursSpinnerFrom = itemView.findViewById(R.id.recyclerView_item_hours_spinner_hours_from);
            hoursSpinnerTo = itemView.findViewById(R.id.recyclerView_item_hours_spinner_hours_to);
            shiftSwitch = itemView.findViewById(R.id.recyclerView_item_hours_switch_shift);
            dashFirst = itemView.findViewById(R.id.recyclerView_item_hours_spinner_hours_dash);
            dashSecond = itemView.findViewById(R.id.recyclerView_item_hours_spinner_hours_second_dash);
            hoursSpinnerSecFrom = itemView.findViewById(R.id.recyclerView_item_hours_spinner_hours_second_from);
            hoursSpinnerSecTo = itemView.findViewById(R.id.recyclerView_item_hours_spinner_hours_second_to);

            hoursSpinnerFrom.setVisibility(View.GONE);
            hoursSpinnerTo.setVisibility(View.GONE);
            hoursSpinnerSecFrom.setVisibility(View.GONE);
            hoursSpinnerSecTo.setVisibility(View.GONE);
            dashSecond.setVisibility(View.GONE);
            dashFirst.setVisibility(View.GONE);
            shiftSwitch.setVisibility(View.GONE);
            hour24CheckBox.setVisibility(View.GONE);

            shiftSwitch.setSwitchTypeface(MyApplication.getShpIranSansMoblie(context));


            hour24CheckBox.setTypeface(MyApplication.getShpIranSansMoblie(context));
            shiftSwitch.setTypeface(MyApplication.getShpIranSansMoblie(context));

            setupSpinners(hoursSpinnerFrom, DataGenerator.hoursFrom());
            setupSpinners(hoursSpinnerTo, DataGenerator.hoursTo());

            setupSpinners(hoursSpinnerSecFrom, DataGenerator.hoursFrom());
            setupSpinners(hoursSpinnerSecTo, DataGenerator.hoursTo());


        }


    }


    private void setupSpinners(Spinner hoursSpinner, List<String> hours) {

        MySpinnerAdapter adapter = new MySpinnerAdapter(
                context, android.R.layout.simple_spinner_dropdown_item, hours);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter.setSpinner(hoursSpinner);
        adapter.setSpinnerView(hoursSpinner);

        hoursSpinner.setAdapter(adapter);


        hoursSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                if (position > 0) {

                    Toast.makeText(context, adapter.getItem(position)+" -> "+adapter.getSpinnerView().getId(), Toast.LENGTH_SHORT).show();
                   // Toast.makeText(context, "U Choose : " + adapter.getItem(position), Toast.LENGTH_SHORT).show();

                    switch (adapter.getSpinnerView().getId()){
                        case R.id.recyclerView_item_hours_spinner_hours_from:
                            Toast.makeText(context, "from One "+adapter.getItem(position), Toast.LENGTH_SHORT).show();
                            break;

                        case R.id.recyclerView_item_hours_spinner_hours_to:
                            Toast.makeText(context, "to One "+adapter.getItem(position), Toast.LENGTH_SHORT).show();
                            break;

                        case R.id.recyclerView_item_hours_spinner_hours_second_from:
                            Toast.makeText(context, "from two "+adapter.getItem(position), Toast.LENGTH_SHORT).show();
                            break;


                        case R.id.recyclerView_item_hours_spinner_hours_second_to:
                            Toast.makeText(context, "to two "+adapter.getItem(position), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


    }

    private void setupShiftSwitch(HoursViewHolder holder) {
        holder.shiftSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.hoursSpinnerSecFrom.setVisibility(View.VISIBLE);
                holder.hoursSpinnerSecTo.setVisibility(View.VISIBLE);
                holder.dashSecond.setVisibility(View.VISIBLE);
            } else {
                holder.hoursSpinnerSecFrom.setVisibility(View.GONE);
                holder.hoursSpinnerSecTo.setVisibility(View.GONE);
                holder.dashSecond.setVisibility(View.GONE);
            }
        });
    }


}

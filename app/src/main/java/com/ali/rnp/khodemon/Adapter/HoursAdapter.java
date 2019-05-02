package com.ali.rnp.khodemon.Adapter;

import android.content.Context;
import android.util.Log;
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
    private List<HourDays> hourDaysList;
    private HourDays hourDays;
    private MySpinnerAdapter adapter;
    private OnItemSelected onItemSelected;


    private static final String TAG = "HoursAdapter";


    public HoursAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HourDays> hourDaysList) {
        this.hourDaysList = new ArrayList<>();
        this.hourDaysList = hourDaysList;
        notifyDataSetChanged();
    }


    public void setOnItemSelected(OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    public List<HourDays> getData() {
        return this.hourDaysList;
    }

    @NonNull
    @Override
    public HoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_hours, parent, false);

        return new HoursViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoursViewHolder holder, int position) {


        holder.dayTextView.setText(hourDaysList.get(position).getDayName());

        holder.dayCheckBox.setOnCheckedChangeListener(null);
        setupDayCheckBox(holder, position);
        if (!holder.dayCheckBox.isChecked()) {
            holder.dayCheckBox.setChecked(hourDaysList.get(position).isOpen());
            Log.i(TAG, "onBindViewHolder: OK "+position);
        }else {
            Log.i(TAG, "onBindViewHolder: NO "+position);

        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!hourDaysList.get(position).isOpen()) {
                    holder.dayCheckBox.setChecked(true);

                } else {
                    holder.dayCheckBox.setChecked(false);
                }

            }
        });


        setupShiftSwitch(holder);
        setup24hourCheckBox(holder);


        setupClick(position, holder.hoursSpinnerFrom,
                holder.hoursSpinnerTo,
                holder.hoursSpinnerSecFrom,
                holder.hoursSpinnerSecTo);


    }

    private void setupClick(int positionRec, Spinner... spinners) {
        int index = 0 ;
        for (Spinner spin : spinners) {

            switch (spin.getId()){

                case R.id.recyclerView_item_hours_spinner_hours_from:
                    index = DataGenerator.hoursFrom().indexOf(hourDaysList.get(positionRec).getHourFromOne());
                    spin.setSelection(index == -1 ? 0 : index );

                    break;

                case R.id.recyclerView_item_hours_spinner_hours_to:
                    index = DataGenerator.hoursFrom().indexOf(hourDaysList.get(positionRec).getHourToOne());
                    spin.setSelection(index == -1 ? 0 : index );
                    break;


                case R.id.recyclerView_item_hours_spinner_hours_second_from:
                    index = DataGenerator.hoursFrom().indexOf(hourDaysList.get(positionRec).getHourFromSec());
                    spin.setSelection(index == -1 ? 0 : index );

                    break;

                case R.id.recyclerView_item_hours_spinner_hours_second_to:
                    index = DataGenerator.hoursFrom().indexOf(hourDaysList.get(positionRec).getHourToSec());
                    spin.setSelection(index == -1 ? 0 : index );

                    break;


            }




            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                    if (position > 0) {
                       // hourDays = new HourDays();


                        switch (spin.getId()) {
                            case R.id.recyclerView_item_hours_spinner_hours_from:

                                Toast.makeText(context, "FromOne -> positionRec:(" + positionRec + ") \n" +
                                        "selected:(" + adapter.getItem(position) + ")", Toast.LENGTH_LONG).show();

                                //hourDays.setHourFromOne(adapter.getItem(position));
                                hourDaysList.get(positionRec).setHourFromOne(adapter.getItem(position));
                                //setDaysSelected();

                                /*
                                DialogChooseOtherDay dialog = new DialogChooseOtherDay();
                                FragmentManager fragmentManager = ((HoursChooseActivity)context).getSupportFragmentManager();

                                dialog.setOnSelectCheckBoxDialog(new DialogChooseOtherDay.OnSelectCheckBoxDialog() {
                                    @Override
                                    public void OnSelected() {
                                   // setDaysSelected(true);


                                        hourDays.setOpen(true);
                                        hourDays.setDayName("یکشنبه تغییر یافت");


                                       // hourDaysList.get(1).setDayName("یکشنبه تغییر یافت");
                                        hourDaysList.get(1).setOpen(true);
                                        hourDaysList.get(1).setDayName("یکشنبه تغییر یافت");


                                    notifyDataSetChanged();
                                     }
                                });

                                dialog.show(fragmentManager,"test" );

*/
                                break;

                            case R.id.recyclerView_item_hours_spinner_hours_to:

                                Toast.makeText(context, "ToOne -> positionRec:(" + positionRec + ") \n" +
                                        "selected:(" + adapter.getItem(position) + ")", Toast.LENGTH_LONG).show();
                               // hourDays.setHourToOne(adapter.getItem(position));
                                hourDaysList.get(positionRec).setHourToOne(adapter.getItem(position));


                                //setDaysSelected(true);

                                break;

                            case R.id.recyclerView_item_hours_spinner_hours_second_from:

                                Toast.makeText(context, "FromSec -> positionRec:(" + positionRec + ") \n" +
                                        "selected:(" + adapter.getItem(position) + ")", Toast.LENGTH_LONG).show();
                               // hourDays.setHourFromSec(adapter.getItem(position));
                                hourDaysList.get(positionRec).setHourFromSec(adapter.getItem(position));

                                break;


                            case R.id.recyclerView_item_hours_spinner_hours_second_to:

                                Toast.makeText(context, "ToSec -> positionRec:(" + positionRec + ") \n" +
                                        "selected:(" + adapter.getItem(position) + ")", Toast.LENGTH_LONG).show();
                                //hourDays.setHourToSec(adapter.getItem(position));
                                hourDaysList.get(positionRec).setHourToSec(adapter.getItem(position));



                                break;
                        }


                       // hourDaysList.set(positionRec, hourDays);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });
        }
    }


    private void setupDayCheckBox(HoursViewHolder holder, int position) {


        holder.dayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    hourDays = new HourDays();

                    holder.hoursSpinnerFrom.setVisibility(View.VISIBLE);
                    holder.hoursSpinnerTo.setVisibility(View.VISIBLE);
                    holder.shiftSwitch.setVisibility(View.VISIBLE);
                    holder.hour24CheckBox.setVisibility(View.VISIBLE);
                    holder.dashFirst.setVisibility(View.VISIBLE);
                    hourDaysList.get(position).setOpen(true);
                    holder.closeTextView.setText(R.string.day_open);
                    holder.closeTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

                } else {
                    holder.hoursSpinnerFrom.setVisibility(View.GONE);
                    holder.hoursSpinnerTo.setVisibility(View.GONE);
                    holder.shiftSwitch.setVisibility(View.GONE);
                    holder.dashFirst.setVisibility(View.GONE);
                    holder.hour24CheckBox.setVisibility(View.GONE);
                    holder.hour24CheckBox.setChecked(false);
                    hourDaysList.get(position).setOpen(false);
                    holder.closeTextView.setText(R.string.day_close);
                    holder.closeTextView.setTextColor(ContextCompat.getColor(context, R.color.red300));
                }



                if (onItemSelected != null) {
                    onItemSelected.OnSelected(
                            hourDaysList.get(position).isOpen(),
                            hourDaysList.get(position).getDayName());


                }

            }
        });
    }

    private void setup24hourCheckBox(HoursViewHolder holder) {
        holder.hour24CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.hoursSpinnerTo.setVisibility(View.GONE);
                    holder.hoursSpinnerFrom.setVisibility(View.GONE);
                    holder.hoursSpinnerSecTo.setVisibility(View.GONE);
                    holder.hoursSpinnerSecFrom.setVisibility(View.GONE);
                    holder.shiftSwitch.setVisibility(View.GONE);
                    holder.dashFirst.setVisibility(View.GONE);
                    holder.dashSecond.setVisibility(View.GONE);

                    holder.closeTextView.setText(R.string.day_open);
                    holder.closeTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

                } else {
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
        return hourDaysList.size();
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

        adapter = new MySpinnerAdapter(
                context, android.R.layout.simple_spinner_dropdown_item, hours);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter.setSpinner(hoursSpinner);
        adapter.setSpinnerView(hoursSpinner);

        hoursSpinner.setAdapter(adapter);

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


    public void setDaysSelected() {
        if (hourDaysList != null) {


           // hourDaysList.get(1).setOpen(true);
            hourDaysList.get(1).setDayName("یکشنبه تغییر یافت");

        }
        notifyDataSetChanged();


    }

    public void showDataList() {
      /*
        for (int i = 0; i < hourDaysList.size(); i++) {
            Log.i(TAG,
                    "\n DAY: " + hourDaysList.get(i).getDayName() +
                    "\n FROM ONE: " + hourDaysList.get(i).getHourFromOne() +
                    "\n TO ONE: " + hourDaysList.get(i).getHourToOne() +
                    "\n FROM SEC: " + hourDaysList.get(i).getHourFromSec() +
                    "\n TO SEC: " + hourDaysList.get(i).getHourToSec());
        }
        */
      notifyDataSetChanged();
    }

    public interface OnItemSelected {
        void OnSelected(boolean isChecked, String day);
    }

}

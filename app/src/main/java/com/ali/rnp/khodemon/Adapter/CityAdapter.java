package com.ali.rnp.khodemon.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ali.rnp.khodemon.DataModel.City;
import com.ali.rnp.khodemon.ProvidersApp;
import com.ali.rnp.khodemon.Views.Activities.CityChooseActivity;
import com.ali.rnp.khodemon.Views.Activities.MainActivity;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLevelTwo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityAdapterHolder> {

    //private static final String TAG = "CityAdapter";
    private Context context;
    private List<City> cities;
    private boolean isFromFragmentAddTwo = false ;

    private static final String TAG = "CityAdapter";


    public CityAdapter(Context context) {
        this.context = context;
    }

    public void setupCityAdapter(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();

    }
    public void setIsFromFragmentAddTwo(boolean isFromFragmentAddTwo){
        this.isFromFragmentAddTwo = isFromFragmentAddTwo;

    }

    public void filterList(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CityAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View rootView = LayoutInflater.from(context).inflate(R.layout.item_rec_view_city_adapter, parent, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        rootView.setLayoutParams(lp);
        return new CityAdapterHolder(rootView);
    }


    @Override
    public void onBindViewHolder(@NonNull final CityAdapterHolder holder, final int position) {

        final City city = cities.get(position);
        holder.cityNameTxt.setText(city.getCityName());


        if ((position) + 1 == cities.size()) {
            holder.line.setVisibility(View.INVISIBLE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendCityData(city);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class CityAdapterHolder extends RecyclerView.ViewHolder {

        private TextView cityNameTxt;
        private View line;

        CityAdapterHolder(@NonNull View itemView) {
            super(itemView);
            cityNameTxt = itemView.findViewById(R.id.item_rec_view_adapter_city_name_txt);
            line = itemView.findViewById(R.id.item_rec_view_adapter_line);


        }
    }

    private void sendCityData(City city) {

        Intent intent = new Intent();
        intent.putExtra(ProvidersApp.KEY_CITY_NAME, city.getCityName());
        intent.putExtra(ProvidersApp.KEY_PROVINCE_NAME, city.getProvince());

        ((Activity) context).setResult(RESULT_OK,intent);
        ((Activity) context).finish();

    }



}

package com.ali.rnp.khodemon.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.City;
import com.ali.rnp.khodemon.MainActivity;
import com.ali.rnp.khodemon.R;
import com.ali.rnp.khodemon.Views.Activites.CityChoose;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityAdapterHolder> {

    private static final String TAG = "CityAdapter";
    private Context context;
    private List<City> cities;

    public CityAdapter (Context context){
        this.context = context;
    }

    public void setupCityAdapter(List<City> cities){
        this.cities = cities;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CityAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.item_rec_view_city_adapter,null,false);

        return new CityAdapterHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CityAdapterHolder holder, final int position) {

        City city = cities.get(position);
        holder.cityNameTxt.setText(city.getCity());


        if ((position)+1 == cities.size()){
            holder.line.setVisibility(View.INVISIBLE);
        }else {
            holder.line.setVisibility(View.VISIBLE);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendCityData(holder.cityNameTxt.getText().toString());
            }
        });




    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class CityAdapterHolder extends RecyclerView.ViewHolder {

        private TextView cityNameTxt;
        private View line;
    public CityAdapterHolder(@NonNull View itemView) {
        super(itemView);
        cityNameTxt = itemView.findViewById(R.id.item_rec_view_adapter_city_name_txt);
        line = itemView.findViewById(R.id.item_rec_view_adapter_line);

        Log.i(TAG, "CityAdapterHolder: "+cities.size());

    }
}

    private void sendCityData(String city) {

        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(CityChoose.INTENT_CITY_NAME,city);
        ((Activity)context).setResult(RESULT_OK,intent);
        ((Activity)context).finish();

    }


}

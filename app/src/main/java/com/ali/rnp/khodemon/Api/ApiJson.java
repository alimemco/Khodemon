package com.ali.rnp.khodemon.Api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiJson {

    private static String URL_API="http://amirabbaszareii.ir/php/phpslider.json";
    private List<String> titleList = new ArrayList<>();

    private Context context;
    private OnResultJsonReceived onResultJsonReceived;



    public ApiJson(Context context,OnResultJsonReceived onResultJsonReceived){
        this.context = context;
        this.onResultJsonReceived = onResultJsonReceived;


    }


    public void getTitleFromJson(){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL_API, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                parseDataFromJson(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResultJsonReceived.onItemResultReceived(null,error.toString());
            }
        });

        int retryTime = 10000;
        request.setRetryPolicy(new DefaultRetryPolicy(retryTime,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);

    }

    private void parseDataFromJson(JSONArray response) {

        for (int i = 0; i < response.length(); i++) {

            try {
                JSONObject jsonObject = response.getJSONObject(i);

                String title = jsonObject.getString("title");
                titleList.add(title);

            } catch (JSONException e) {
                e.printStackTrace();
                onResultJsonReceived.onItemResultReceived(null,e.toString());
            }

        }

        onResultJsonReceived.onItemResultReceived(titleList,null);

    }


    public interface OnResultJsonReceived {
        void onItemResultReceived(List<String> titleList ,String error);
    }
}

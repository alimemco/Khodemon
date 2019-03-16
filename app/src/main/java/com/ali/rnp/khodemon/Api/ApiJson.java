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

    public ApiJson(Context context){
        this.context = context;

    }


    public void getTitleFromJson(OnResultReceived onResultReceived){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL_API, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                parseDataFromJson(response,onResultReceived);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResultReceived.onItemResultReceived(null,error.toString());
            }
        });

        int retryTime = 10000;
        request.setRetryPolicy(new DefaultRetryPolicy(retryTime,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);

    }

    private void parseDataFromJson(JSONArray response, OnResultReceived onResultReceived) {

        for (int i = 0; i < response.length(); i++) {

            try {
                JSONObject jsonObject = response.getJSONObject(i);

                String title = jsonObject.getString("title");
                titleList.add(title);

            } catch (JSONException e) {
                e.printStackTrace();
                onResultReceived.onItemResultReceived(null,e.toString());
            }

        }

        onResultReceived.onItemResultReceived(titleList,null);

    }


    public interface OnResultReceived {
        void onItemResultReceived(List<String> titleList ,String error);
    }
}

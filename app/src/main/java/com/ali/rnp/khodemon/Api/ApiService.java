package com.ali.rnp.khodemon.Api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiService {

    private final static String API_REGISTER_USER="http://khodemon.ir/registerUser.php";
    private final static String API_LOGIN_USER="http://khodemon.ir/loginUser.php";

    public static final int STATUS_REGISTER_ERROR=616;
    public static final int STATUS_Login_ERROR=717;

    private int retryTime=10000;
    private Context context;

    public ApiService(Context context){
        this.context = context;
    }

    public void registerUser(JSONObject jsonObject, final OnRegisterCompleted onRegisterCompleted){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_REGISTER_USER, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                parseJsonRegisterUser(response,onRegisterCompleted);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRegisterCompleted.onRegisterStatusReceived(STATUS_REGISTER_ERROR);

            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);

    }

    public void loginUser(JSONObject jsonObject, final OnLoginCompleted onLoginCompleted){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_LOGIN_USER, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonLoginUser(response,onLoginCompleted);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onLoginCompleted.onLoginStatusReceived(STATUS_Login_ERROR);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    private void parseJsonRegisterUser(JSONObject response,OnRegisterCompleted onRegisterCompleted) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            String status = jsonObject.getString("result");
            onRegisterCompleted.onRegisterStatusReceived(Integer.parseInt(status));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJsonLoginUser(JSONObject response, OnLoginCompleted onLoginCompleted) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            String status = jsonObject.getString("result");
            onLoginCompleted.onLoginStatusReceived(Integer.parseInt(status));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public interface OnRegisterCompleted {
        void onRegisterStatusReceived(int status);
    }

    public interface OnLoginCompleted{
        void onLoginStatusReceived(int status);
    }
}
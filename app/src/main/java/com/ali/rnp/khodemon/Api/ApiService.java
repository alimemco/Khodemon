package com.ali.rnp.khodemon.Api;

import android.content.Context;
import android.util.Log;

import com.ali.rnp.khodemon.DataModel.HomeList;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiService {

    private static final String TAG = "ApiService";

    private final static String API_REGISTER_USER = "http://khodemon.ir/registerUser.php";
    private final static String API_LOGIN_USER = "http://khodemon.ir/loginUser.php";
    private final static String API_GET_HOME_ITEMS = "http://khodemon.ir/getHomeItems.php";
    private final static String API_GET_GROUP_ITEMS = "http://khodemon.ir/getGroupItems.php";
    private final static String API_GET_HOME_LIST_ITEMS = "http://khodemon.ir/getHomeItemsList.php";

    public static final int STATUS_REGISTER_ERROR = 616;
    public static final int STATUS_Login_ERROR = 717;


    public static final String LOCATION_GROUP_NAME = "LOCATION";
    public static final String PEOPLE_GROUP_NAME = "PEOPLE";

    public static final int LOCATION_GROUP_KEY = 1;
    public static final int PEOPLE_GROUP_KEY = 2;


    private int retryTime = 10000;
    private Context context;

    List<LocationPeople> locationPeopleSingle;

    public ApiService(Context context) {
        this.context = context;
    }

    public void registerUser(JSONObject jsonObject, final OnRegisterCompleted onRegisterCompleted) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_REGISTER_USER, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                parseJsonRegisterUser(response, onRegisterCompleted);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRegisterCompleted.onRegisterStatusReceived(STATUS_REGISTER_ERROR);

            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);

    }

    public void loginUser(JSONObject jsonObject, final OnLoginCompleted onLoginCompleted) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_LOGIN_USER, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonLoginUser(response, onLoginCompleted);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onLoginCompleted.onLoginStatusReceived(STATUS_Login_ERROR);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void getHomeItems(final OnHomeItemReceived onHomeItemReceived) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_HOME_ITEMS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonHomeItems(response, onHomeItemReceived);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onHomeItemReceived.onItemReceived(null, error);
                    }
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void getGroupItems(JSONObject jsonObjectGroup, final int groupKey, final OnGroupItemReceived onGroupItemReceived) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_GROUP_ITEMS, jsonObjectGroup, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonGroupItems(response, groupKey, onGroupItemReceived);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onGroupItemReceived.onItemGroupReceived(null, error);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public void getHomeListItems(final OnHomeListItemReceived onHomeListItemReceived) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_HOME_ITEMS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonHomeListItems(response, onHomeListItemReceived);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onHomeListItemReceived.onItemReceived(null, null, error);
                    }
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }


    private void parseJsonGroupItems(JSONObject response, int groupKey, OnGroupItemReceived onGroupItemReceived) {

        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArrayResult = jsonObject.getJSONArray("result");

            List<LocationPeople> locationPeopleList = new ArrayList<>();

            for (int i = 0; i < jsonArrayResult.length(); i++) {

                JSONObject jsonObjectLocPeo = jsonArrayResult.getJSONObject(i);

                LocationPeople locationPeople = new LocationPeople();

                if (groupKey == LOCATION_GROUP_KEY) {
                    locationPeople.setId(jsonObjectLocPeo.getInt("ID"));
                    locationPeople.setGroup(jsonObjectLocPeo.getString("group_name"));
                    locationPeople.setName(jsonObjectLocPeo.getString("name"));
                    locationPeople.setTag(jsonObjectLocPeo.getString("tagLocPeo"));
                    locationPeople.setOwnerSeller(jsonObjectLocPeo.getString("owner_seller"));
                    locationPeople.setAddress(jsonObjectLocPeo.getString("address"));
                    locationPeople.setOriginalPic(jsonObjectLocPeo.getString("original_pic"));
                } else if (groupKey == PEOPLE_GROUP_KEY) {
                    locationPeople.setId(jsonObjectLocPeo.getInt("ID"));
                    locationPeople.setGroup(jsonObjectLocPeo.getString("group_name"));
                    locationPeople.setName(jsonObjectLocPeo.getString("name"));
                    locationPeople.setTag(jsonObjectLocPeo.getString("tagLocPeo"));
                    locationPeople.setCity(jsonObjectLocPeo.getString("city"));
                    locationPeople.setWork_experience(jsonObjectLocPeo.getInt("work_experience"));
                    locationPeople.setExperts(jsonObjectLocPeo.getString("experts"));
                    locationPeople.setOriginalPic(jsonObjectLocPeo.getString("original_pic"));
                }


                locationPeopleList.add(locationPeople);
            }
            onGroupItemReceived.onItemGroupReceived(locationPeopleList, null);

        } catch (JSONException e) {
            Log.i(TAG, "parseJsonHomeItems: " + e);

        }

    }

    private void parseJsonHomeItems(JSONObject response, OnHomeItemReceived onHomeItemReceived) {

        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArrayResult = jsonObject.getJSONArray("result");

            List<LocationPeople> locationPeopleList = new ArrayList<>();

            for (int i = 0; i < jsonArrayResult.length(); i++) {

                JSONObject jsonObjectLocPeo = jsonArrayResult.getJSONObject(i);

                LocationPeople locationPeople = new LocationPeople();

                locationPeople.setId(jsonObjectLocPeo.getInt("ID"));
                locationPeople.setGroup(jsonObjectLocPeo.getString("group_name"));
                locationPeople.setName(jsonObjectLocPeo.getString("name"));
                locationPeople.setOriginalPic(jsonObjectLocPeo.getString("original_pic"));

                locationPeopleList.add(locationPeople);
            }
            onHomeItemReceived.onItemReceived(locationPeopleList, null);

        } catch (JSONException e) {
            Log.i(TAG, "parseJsonHomeItems: " + e);

        }
    }

    private void parseJsonHomeListItems(JSONObject response, OnHomeListItemReceived onHomeListItemReceived) {


        try {
            JSONObject jsonObject = new JSONObject(response.toString());

            JSONArray jsonArrayResult = jsonObject.getJSONArray("result");


            List<HomeList> locationPeopleList = new ArrayList<>();
            Log.i(TAG, "jsonArrayResult" + jsonArrayResult.length());

            for (int i = 0; i < jsonArrayResult.length(); i++) {

                JSONObject jsonObjectLocPeo = jsonArrayResult.getJSONObject(i);

                locationPeopleSingle = new ArrayList<>();

                for (int j = 0; j < jsonObjectLocPeo.length(); j++) {
                    Log.i(TAG, "jsonObjectLocPeo: " + jsonObjectLocPeo.length());


                    LocationPeople locationPeople = new LocationPeople();

                    locationPeople.setId(jsonObjectLocPeo.getInt("ID"));
                    locationPeople.setGroup(jsonObjectLocPeo.getString("group_name"));
                    locationPeople.setName(jsonObjectLocPeo.getString("name"));
                    locationPeople.setOriginalPic(jsonObjectLocPeo.getString("original_pic"));

                    locationPeopleSingle.add(locationPeople);

                }
                //   onHomeListItemReceived.onItemReceived(null,locationPeopleSingle,null);

                HomeList homeList = new HomeList();

                homeList.setId(i);
                homeList.setTitle("متن");
                homeList.setLocationPeopleList(locationPeopleSingle);


                locationPeopleList.add(homeList);
            }
            onHomeListItemReceived.onItemReceived(locationPeopleList, locationPeopleSingle, null);

        } catch (JSONException e) {
            Log.i(TAG, "parseJsonHomeItems: " + e);

        }


    }

    private void parseJsonRegisterUser(JSONObject response, OnRegisterCompleted onRegisterCompleted) {
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

    public interface OnLoginCompleted {
        void onLoginStatusReceived(int status);
    }

    public interface OnHomeItemReceived {
        void onItemReceived(List<LocationPeople> locationPeopleList, VolleyError error);
    }

    public interface OnHomeListItemReceived {
        void onItemReceived(List<HomeList> homeLists, List<LocationPeople> locationPeopleList, VolleyError error);
    }

    public interface OnGroupItemReceived {
        void onItemGroupReceived(List<LocationPeople> locationPeopleList, VolleyError error);
    }
}
package com.ali.rnp.khodemon.Api;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.ali.rnp.khodemon.DataModel.City;
import com.ali.rnp.khodemon.DataModel.ListLayout;
import com.ali.rnp.khodemon.DataModel.LocationPeople;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ApiService {

    private static final String TAG = "ApiService";

    private final static String API_REGISTER_USER = "http://khodemon.ir/registerUser.php";
    private final static String API_LOGIN_USER = "http://khodemon.ir/loginUser.php";
    private final static String API_GET_HOME_ITEMS = "http://khodemon.ir/getHomeItems.php";
    private final static String API_GET_GROUP_ITEMS = "http://khodemon.ir/getGroupItems.php";
    private final static String API_GET_HOME_LIST_ITEMS = "http://khodemon.ir/getHomeItemsList.php";
    private final static String API_GET_PROVINCE = "http://khodemon.ir/json/Province.json";

    private final static String API_UPLOAD_PHOTOS = "http://khodemon.ir/upload_images.php";

    private final static String API_TEST = "http://amirabbaszareii.ir/php/phpslider.json";




    public static final int STATUS_REGISTER_ERROR = 616;
    public static final int STATUS_Login_ERROR = 717;


    public static final String GROUP_NAME_LOCATION = "LOCATION";
    public static final String GROUP_NAME_PEOPLE = "PEOPLE";

    public static final int LOCATION_GROUP_KEY = 1;
    public static final int PEOPLE_GROUP_KEY = 2;

    private JSONObject jsonObjectPhoto;


    private int retryTime = 10000;
    private Context context;

    List<LocationPeople> locationPeoplePerItem;

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

    public void getHomeRecyclerListItems(final OnHomeListItemReceived onHomeListItemReceived) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, API_GET_HOME_LIST_ITEMS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonHomeRecyclerListItems(response, onHomeListItemReceived);
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

    public void getProvince(OnProvinceReceived onProvinceReceived) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, API_GET_PROVINCE, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseJsonProvince(response,onProvinceReceived);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                onProvinceReceived.onReceived(null,error);

            }
        });

                request.setRetryPolicy(new DefaultRetryPolicy(retryTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                // Volley.newRequestQueue(context).add(request);
        requestQueue.add(request);
    }


    public void uploadImage(Bitmap bitmap,final int currentPhoto,int allPhoto,OnUploadedPhoto onUploadedPhoto){

        try {
            jsonObjectPhoto = new JSONObject();
            String imgName = String.valueOf(Calendar.getInstance().getTimeInMillis());
            //jsonObject.put("id",currentPhoto);
            jsonObjectPhoto.put("name", imgName);
            jsonObjectPhoto.put("image", bitmapToString(bitmap));

        } catch (JSONException e) {
            Log.e("JSONObject Here", e.toString());
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_UPLOAD_PHOTOS, jsonObjectPhoto,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //  rQueue.getCache().clear();

                        // int cu = jsonObject.getInt("currentPhoto");


                        onUploadedPhoto.OnUploadPhoto(currentPhoto,null);



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.toString());
                onUploadedPhoto.OnUploadPhoto(-1,volleyError);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(120000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(jsonObjectRequest);

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

    private void parseJsonHomeRecyclerListItems(JSONObject response, OnHomeListItemReceived onHomeListItemReceived) {

        try {

            JSONObject jsonObject = new JSONObject(response.toString());

            JSONArray jsonArrayResult = jsonObject.getJSONArray("result");

            List<ListLayout> locationPeopleListLayout = new ArrayList<>();
            for (int i = 0; i < jsonArrayResult.length(); i++) {

                JSONObject jsonObjectGp = jsonArrayResult.getJSONObject(i);
                JSONArray jsonArrayData = jsonObjectGp.getJSONArray("data");


                locationPeoplePerItem = new ArrayList<>();
                for (int j = 0; j < jsonArrayData.length(); j++) {

                    JSONObject jsonObjectGroup = jsonArrayData.getJSONObject(j);


                    LocationPeople locationPeople = new LocationPeople();

                    locationPeople.setId(jsonObjectGroup.getInt("ID"));
                    locationPeople.setGroup(jsonObjectGroup.getString("group_name"));
                    locationPeople.setName(jsonObjectGroup.getString("name"));
                    locationPeople.setTag(jsonObjectGroup.getString("tag"));
                    locationPeople.setOriginalPic(jsonObjectGroup.getString("original_pic"));

                    locationPeoplePerItem.add(locationPeople);

                }




                ListLayout listLayout = new ListLayout();

                listLayout.setId(i);
                listLayout.setTitle(jsonObjectGp.getString("title"));
                listLayout.setGroup(jsonObjectGp.getString("group"));
                listLayout.setLocationPeopleList(locationPeoplePerItem);

                locationPeopleListLayout.add(listLayout);

            }


            onHomeListItemReceived.onItemReceived(locationPeopleListLayout, locationPeoplePerItem, null);

        } catch (JSONException e) {
            Log.i(TAG, "parseJsonHomeItems: " + e);

        }

    }

    private void parseJsonProvince(JSONArray response,OnProvinceReceived onProvinceReceived) {

        List<City> cities = new ArrayList<>();



        for (int i = 0; i < response.length(); i++) {

            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String provinceName = jsonObject.getString("name");
                JSONArray jsonArrayCities = jsonObject.getJSONArray("Cities");

                for (int j = 0; j <jsonArrayCities.length() ; j++) {
                    JSONObject jsonObjectCity = jsonArrayCities.getJSONObject(j);
                    String cityName = jsonObjectCity.getString("name");
                    City city = new City();
                    city.setCity(cityName);
                    cities.add(city);
                }

                onProvinceReceived.onReceived(cities,null);
            } catch (JSONException e) {
                e.printStackTrace();
            }

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


    private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

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
        void onItemReceived(List<ListLayout> listLayouts, List<LocationPeople> locationPeopleList, VolleyError error);
    }

    public interface OnGroupItemReceived {
        void onItemGroupReceived(List<LocationPeople> locationPeopleList, VolleyError error);
    }

    public interface OnProvinceReceived {
        void onReceived(List<City> cities, VolleyError error);
    }

    public interface OnUploadedPhoto{
        void OnUploadPhoto(int currentPhotoNum,VolleyError error);
    }


}